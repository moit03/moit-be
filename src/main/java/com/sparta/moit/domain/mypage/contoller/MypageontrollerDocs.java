package com.sparta.moit.domain.mypage.contoller;

import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

public interface MypageontrollerDocs {
    @Operation(summary = "마이페이지 정보 조회", description = "마이페이지 정보 조회 API")
    ResponseEntity<?> getMypage(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails);
}
