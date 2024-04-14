package com.sparta.moit.global.service;

import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestService(MemberRepository memberRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자의 토큰을 생성하는 메서드
    public String generateUserToken(String email, String password) {
        // 이메일을 기반으로 사용자를 검색
        Member user = memberRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 사용자의 비밀번호가 일치하면 토큰 생성
            return jwtUtil.createTokenForUser(user);
        } else {
            // 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우 예외 처리
            throw new RuntimeException("로그인 실패");
        }
    }

    // 사용자의 리프레시 토큰을 생성하는 메서드
    public String generateRefreshToken(String email, String password) {
        // 이메일을 기반으로 사용자를 검색
        Member user = memberRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 사용자의 비밀번호가 일치하면 리프레시 토큰 생성
            return jwtUtil.createRefreshTokenForUser(user);
        } else {
            // 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우 예외 처리
            throw new RuntimeException("리프레시 토큰 생성 실패");
        }
    }
}