package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.dto.MemberResponseDto;
import com.sparta.moit.domain.member.entity.Member;

public interface KakaoService {
    MemberResponseDto kakaoLogin(String code) throws JsonProcessingException;
//    void logout(String refreshTokenString);

    String login();

    String signOut(Member member) throws JsonProcessingException;
    
}