package com.sparta.moit.global.common.dto;

import lombok.Getter;

@Getter
public class RefreshTokenResponse {
    private String refreshToken;

    public RefreshTokenResponse(String refreshToken) {
    }
}
