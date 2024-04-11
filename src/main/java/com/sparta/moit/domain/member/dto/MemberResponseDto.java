package com.sparta.moit.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    @NotBlank
    private String username;
    private String accessToken;
    private String refreshToken;
    @Builder
    public MemberResponseDto(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
