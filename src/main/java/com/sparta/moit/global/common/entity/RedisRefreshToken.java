package com.sparta.moit.global.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh", timeToLive =  60 ) // 14 * 24 * 60 * 60 *
public class RedisRefreshToken {

    @Id
    @Schema(description = "refresh token")
    private String token;

    @Schema(description = "토큰 발급 사용자 email 정보")
    private String email;

    @Builder
    public RedisRefreshToken(String token, String email) {
        this.token = token;
        this.email = email;
    }


    public void setToken(String refreshTokenString) {
    }

    public void setEmail(String email) {
    }

    public void setDate(Date date) {
    }
}