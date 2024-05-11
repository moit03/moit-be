package com.sparta.moit.global.common.service;

import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.global.common.entity.RedisRefreshToken;
import com.sparta.moit.global.error.CustomValidationException;
import com.sparta.moit.global.jwt.JwtUtil;
import com.sparta.moit.global.common.repository.RedisRefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j(topic = "리프레시 토큰 관리")
@Service
@Transactional
public class RefreshTokenService {

    /*리프레시 토큰 관리 로직
     * 토큰 생성, 저장, 검증, 삭제, 액세스 토큰 갱신 기능 수행 */
    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    public RefreshTokenService(RedisService redisService, JwtUtil jwtUtil, RedisRefreshTokenRepository redisRefreshTokenRepository) {
        this.redisService = redisService;
        this.jwtUtil = jwtUtil;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
    }

    /*리프레시 토큰 생성 및 저장*/
    public String createAndSaveRefreshToken(String email, String refreshTokenString) {
        RedisRefreshToken refreshToken = RedisRefreshToken.builder()
                .token(refreshTokenString)
                .email(email)
                .build();

        redisService.saveRefreshToken(refreshToken);
        return refreshToken.getToken();
    }

    /*리프레시 토큰 검증*/
    public boolean validateRefreshToken(String token) {
        log.info("validateRefreshToken 검증하는 리프레시 토큰: ", token);
        return redisRefreshTokenRepository.existsByToken(token);
    }

    /*리프레시 토큰으로 새 액세스 토큰 발급*/
    public Optional<String> refreshAccessToken(String refreshToken) {
        return redisRefreshTokenRepository.findByToken(refreshToken)
                .map(RedisRefreshToken::getEmail) /* 토큰에서 이메일 추출 */
                .map(email -> jwtUtil.createToken(email, UserRoleEnum.USER)); /*새 액세스 토큰 생성*/
    }

    /*리프레시 토큰 삭제*/
    public void deleteRefreshToken(String refreshTokenString) {
        /*DB에 해당 토큰이 있는 지 확인*/
        Optional<RedisRefreshToken> refreshTokenOptional = redisRefreshTokenRepository.findByToken(refreshTokenString);

        if (refreshTokenOptional.isPresent()) {
            redisRefreshTokenRepository.delete(refreshTokenOptional.get());
        } else {
            /*DB에 해당 토큰이 존재하지 않는 경우, 예외를 던집니다.*/
            throw new CustomValidationException("토큰이 유효하지 않거나 이미 삭제되었습니다.", Map.of("refreshToken", "토큰이 존재하지 않습니다."));
        }
    }
}