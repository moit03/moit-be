package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.dto.MemberResponseDto;

public interface NaverService {
    MemberResponseDto naverLogin(String code, String state) throws JsonProcessingException;
//    void logout(String refreshTokenString);
    String refreshToken(String refreshToken);
}