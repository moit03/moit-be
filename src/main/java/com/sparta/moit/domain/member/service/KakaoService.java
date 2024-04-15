package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.dto.MemberResponseDto;

public interface KakaoService {
    MemberResponseDto kakaoLogin(String code) throws JsonProcessingException;
    void logout(String refreshTokenString);

    String login();
}