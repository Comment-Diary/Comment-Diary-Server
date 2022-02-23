package com.commentdiary.src.member.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.*;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.RefreshToken;
import com.commentdiary.src.member.dto.*;
import com.commentdiary.src.member.repository.MemberRepository;
import com.commentdiary.src.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commentdiary.common.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CommonException(DUPLICATED_EMAIL);
        }

        if (signUpRequest.isSamePassword()) {
            memberRepository.save(signUpRequest.toEntity());
        }
    }

    public TokenResponse login(LoginRequest loginRequest) {

        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CommonException(MEMBER_NOT_FOUND));
        member.checkPassword(passwordEncoder, loginRequest.getPassword());

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .id(authentication.getName())
                .value(tokenResponse.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenResponse;
    }

    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequest) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequest.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequest.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequest.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenResponse.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenResponse;
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if (changePasswordRequest.isSamePassword()) {
            getCurrentMemberId().changePassword((new BCryptPasswordEncoder().encode(changePasswordRequest.getPassword())));
        }
    }

    public void delete() {
        memberRepository.deleteById(getCurrentMemberId().getId());
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getCurrentMemberId() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(MEMBER_NOT_FOUND));
    }
}
