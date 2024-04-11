package com.sparta.moit.domain.member.controller.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.global.common.dto.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "로그인", description = "로그인 API")
public interface MemberControllerDocs {
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API")
    ResponseEntity<?> kakaoLogin(@RequestParam String code) throws JsonProcessingException;

    @Operation(summary = "네이버 로그인", description = "네이버 로그인 API")
    ResponseEntity<?> naverLogin(@RequestParam String code,@RequestParam String state) throws JsonProcessingException;

    @Operation(summary = "로그아웃", description = "로그아웃 시 JWT 토큰을 만료처리 합니다.")
    ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request);
}


