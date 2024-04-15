package com.sparta.moit.global.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

//@Entity
@Getter
//@Table(name = "refreshtoken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh", timeToLive = 14 * 24 * 60 * 60 * 1000L)
public class RedisRefreshToken {
    @Id
    private String uid;

    @Indexed
    @Schema(description = "refresh token")
    private String token;

    @Schema(description = "토큰 발급 사용자 email 정보")
    private String email;

    @Schema(description = "토큰 만료 시간")
    private Date expiryDate;

    @Builder
    public RedisRefreshToken(String token, String email, Date expiryDate) {
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }


    public void setToken(String refreshTokenString) {
    }

    public void setEmail(String email) {
    }

    public void setDate(Date date) {
    }
}
