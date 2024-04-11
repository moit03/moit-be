package com.sparta.moit.global.common.controller;

import com.sparta.moit.global.common.dto.RefreshTokenRequest;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Tag(name = "Auth Controller", description = "리프레시 토큰을 통한 엑세스 토큰 갱신")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RefreshTokenService refreshTokenService;

    public AuthController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    /*엑세스 토큰 갱신 API 호출*/
    @PostMapping("/refresh")
    @Operation(summary = "엑세스 토큰 갱신", description = "리프레시 토큰 검증 및 새 엑세스 토큰 발급")
    @ApiResponse(responseCode = "200", description = "엑세스 토큰 발급 성공")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        /*리프레시 토큰이 없으면 badRequest 반환*/
        String refreshToken = request.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("토큰이 입력되지 않았습니다.");
        }

        /*리프레시 토큰 검증*/
        boolean isValidRefreshToken = refreshTokenService.validateRefreshToken(refreshToken);
        if (!isValidRefreshToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증이 필요합니다.");
        }

        /*리프레시 토큰으로 새로운 엑세스 토큰 발급*/
        return refreshTokenService.refreshAccessToken(refreshToken)
                .map(accessToken -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(AUTHORIZATION, accessToken);
                    return ResponseEntity.ok().body(ResponseDto.success("액세스 토큰이 발급되었습니다.", accessToken));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail("엑세스 토큰 발급에 실패했습니다.", null)));
    }
}
