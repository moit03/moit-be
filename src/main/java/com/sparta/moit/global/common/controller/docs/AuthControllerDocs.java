package com.sparta.moit.global.common.controller.docs;

import com.sparta.moit.global.common.dto.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth Controller", description = "리프레시 토큰을 통한 엑세스 토큰 갱신")
public interface AuthControllerDocs {
    @Operation(summary = "엑세스 토큰 갱신", description = "리프레시 토큰 검증 및 새 엑세스 토큰 발급")
    ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest request);

    @Operation(summary = "로그아웃", description = "로그아웃 시 RefreshToken을 만료처리 합니다.")
    ResponseEntity<String> deleteRefreshToken(@RequestBody RefreshTokenRequest request);
}
