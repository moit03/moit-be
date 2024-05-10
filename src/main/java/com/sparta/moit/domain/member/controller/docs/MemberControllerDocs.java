package com.sparta.moit.domain.member.controller.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "로그인", description = "로그인 API")
public interface MemberControllerDocs {
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API")
    ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException;

    @Operation(summary = "네이버 로그인", description = "네이버 로그인 API")
    ResponseEntity<?> naverLogin(@RequestParam String code,@RequestParam String state, HttpServletResponse response) throws JsonProcessingException;

    @Operation(summary = "회원탈퇴", description = "회원 탈퇴 API")
    ResponseEntity<ResponseDto<String>> signOut(@AuthenticationPrincipal UserDetailsImpl userDetails);

}


