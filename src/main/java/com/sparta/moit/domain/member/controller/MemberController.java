package com.sparta.moit.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.dto.MemberResponseDto;
import com.sparta.moit.domain.member.service.KakaoService;
import com.sparta.moit.domain.member.service.MemberService;
import com.sparta.moit.domain.member.service.NaverService;
import com.sparta.moit.global.common.dto.RefreshTokenRequest;
import com.sparta.moit.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @GetMapping("/signin/kakao")
    @Operation(summary = "카카오 소셜 로그인", description = "카카오로 소셜 로그인을 합니다.")
    @ApiResponse(responseCode = "200", description = "카카오 로그인 완료")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        MemberResponseDto responseDto = kakaoService.kakaoLogin(code);
        return ResponseEntity.ok().body(ResponseDto.success("카카오 로그인 완료",responseDto));
    }

    @GetMapping("/signin/naver")
    @Operation(summary = "네이버 소셜 로그인", description = "네이버로 소셜 로그인을 합니다.")
    @ApiResponse(responseCode = "200", description = "네이버 로그인 완료")
    public ResponseEntity<?> naverLogin(@RequestParam String code, @RequestParam String state ) throws JsonProcessingException{
        MemberResponseDto responseDto = naverService.naverLogin(code,state);
        return ResponseEntity.ok().body(ResponseDto.success("네이버 로그인 완료", responseDto));
    }

    /*로그아웃 기능 호출*/
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 시 JWT 토큰을 만료처리 합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 완료")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        /*리프레시 토큰이 없으면 badRequest 반환*/
        String refreshTokenString = request.getRefreshToken();
        if (refreshTokenString == null || refreshTokenString.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        /*로그아웃 API 호출*/
        //naverService.logout(refreshTokenString);
        kakaoService.logout(refreshTokenString);
        /*로그아웃 메시지 반환*/
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 되었습니다.");
    }
}
