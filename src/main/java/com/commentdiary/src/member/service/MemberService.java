package com.commentdiary.src.member.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.*;
import com.commentdiary.src.delivery.service.DeliveryService;
import com.commentdiary.src.member.repository.MemberRepository;
import com.commentdiary.src.member.repository.RefreshTokenRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.RefreshToken;
import com.commentdiary.src.member.dto.*;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeliveryService deliveryService;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CommonException(DUPLICATED_EMAIL);
        }
        if (signUpRequest.isSamePassword()) {
            memberRepository.save(signUpRequest.toEntity());
        }
        Member member = memberRepository.findByEmail(signUpRequest.getEmail()).orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
        deliveryService.deliveryDiary(member);
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
        member.checkPassword(passwordEncoder, loginRequest.getPassword());

        if (member.getStatus().getDescription().equals("비활성")) {
            throw new CommonException(BLOCKED_USER);
        }

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
                .deviceToken(loginRequest.getDeviceToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenResponse;
    }

    @Transactional
    public MyPageResponse myPage() {
        Member member = getCurrentMemberId();
        return MyPageResponse.of(member);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if (changePasswordRequest.isSamePassword()) {
            getCurrentMemberId().changePassword((new BCryptPasswordEncoder().encode(changePasswordRequest.getPassword())));
        }
    }

    @Transactional
    public void logout(String accessToken) {
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        refreshTokenRepository.findById(authentication.getName()).orElseThrow(() -> new CommonException(ALREADY_LOGOUT));
        refreshTokenRepository.deleteById(authentication.getName());
    }

    @Transactional
    public void delete() {
        memberRepository.deleteById(getCurrentMemberId().getId());
    }

    @Transactional
    public TokenResponse reissue(String accessToken, String refreshToken) {
        // 1. Refresh Token 검증
        String validate = tokenProvider.validateRefreshToken(refreshToken);
        System.out.println("reissue : " + refreshToken);

        if (validate == "Expired") {
            throw new CommonException(EXPIRED_REFRESH_TOKEN);
        }
        else if (validate == "Exception") {
            throw new CommonException(INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // 3. 저장소에서 Member ID를 기반으로 Refresh Token 값 가져옴
        RefreshToken getRefreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new CommonException(ALREADY_LOGOUT));

        // 4. Refresh Token 일치하는지 검사
        if (!getRefreshToken.getValue().equals(refreshToken)) {
            throw new CommonException(NOT_MATCHED_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = getRefreshToken.updateValue(tokenResponse.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenResponse;
    }
    @Transactional
    public PushDto push() {
        getCurrentMemberId().changePushStatus();
        return PushDto.of(getCurrentMemberId());
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getCurrentMemberId() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }
}
