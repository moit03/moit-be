package com.sparta.moit.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}