package com.sparta.moit.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.service.KakaoService;
import com.sparta.moit.domain.member.service.MemberService;
import com.sparta.moit.domain.member.service.NaverService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @GetMapping("/signin/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/signin/naver")
    public ResponseEntity<?> naverLogin(@RequestParam String code, String state) throws JsonProcessingException{
        String token = naverService.naverLogin(code,state);
        return ResponseEntity.ok().body(token);
    }
}
