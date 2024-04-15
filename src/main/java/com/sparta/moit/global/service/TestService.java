package com.sparta.moit.global.service;

import com.sparta.moit.domain.member.dto.MemberResponseDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.common.entity.RedisRefreshToken;
import com.sparta.moit.global.common.entity.RefreshToken;
import com.sparta.moit.global.jwt.JwtUtil;
import com.sparta.moit.global.repository.RedisRefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final MemberRepository memberRepository;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public TestService(MemberRepository memberRepository, RedisRefreshTokenRepository redisRefreshTokenRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    // 사용자의 토큰을 생성하는 메서드
    public String generateUserToken(String email) {
        // 이메일을 기반으로 사용자를 검색
        Member user = memberRepository.findByEmail(email).orElse(null);
        // 사용자의 비밀번호가 일치하면 토큰 생성
        return jwtUtil.createTokenForUser(user);
    }

    // 사용자의 리프레시 토큰을 생성하는 메서드
    public String generateRefreshToken(String email) {
        // 이메일을 기반으로 사용자를 검색
        Member user = memberRepository.findByEmail(email).orElse(null);
        String token = jwtUtil.createRefreshToken(user.getEmail(), user.getRole());
        RedisRefreshToken refreshToken = RedisRefreshToken.builder()
                .token(token)
                .email(email)
                .build();

        redisRefreshTokenRepository.save(refreshToken);
        if (user != null) {
            // 리프레시 토큰 생성
            return token;
        } else {
            // 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우 예외 처리
            throw new RuntimeException("리프레시 토큰 생성 실패");
        }
    }

    public MemberResponseDto login() {
        String token = generateUserToken("brandy0108@daum.net");
        String refreshToken = generateRefreshToken("brandy0108@daum.net");

        MemberResponseDto responseDto = MemberResponseDto.builder()
                .username("brandy0108@daum.net")
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
        return responseDto;
    }
}