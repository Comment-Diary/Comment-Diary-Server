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
import org.springframework.web.bind.annotation.RequestBody;

import static com.commentdiary.common.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CommonException(DUPLICATED_EMAIL);
        }

        if (signUpRequest.isSamePassword()) {
            memberRepository.save(signUpRequest.toEntity());
        }
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {

        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
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
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if (changePasswordRequest.isSamePassword()) {
            getCurrentMemberId().changePassword((new BCryptPasswordEncoder().encode(changePasswordRequest.getPassword())));
        }
    }

    @Transactional
    public void delete() {
        memberRepository.deleteById(getCurrentMemberId().getId());
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getCurrentMemberId() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }

}
