package com.commentdiary.src.member.service;

import com.commentdiary.jwt.JwtUtil;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.dto.JwtResponse;
import com.commentdiary.src.member.dto.LoginRequest;
import com.commentdiary.src.member.dto.SignUpRequest;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public JwtResponse signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            // TODO: 이메일 중복 처리
        }
        Member member = signUpRequest.toEntity();
        memberRepository.save(member);
        System.out.println("============");
        System.out.println(signUpRequest.getEmail());
        System.out.println(signUpRequest.getPassword());
        System.out.println("============");
        String jwt = jwtUtil.createJwt(signUpRequest.getEmail());
        return new JwtResponse(jwt);

    }

//    public JwtResponse login(LoginRequest loginRequest) {
//        String jwt = jwtUtil.createJwt(loginRequest.getEamil());
//        return new JwtResponse(jwt);
//    }
}
