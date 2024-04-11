package com.sparta.moit.domain.mypage.controller;

import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface MypageControllerDocs{
    @Operation(summary = "마이페이지 정보 조회", description = "마이페이지 정보 조회 API")
    ResponseEntity<?> getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "마이페이지 참여 모임 리스트", description = "마이페이지 참여 모임 리스트 API")
    ResponseEntity<?> getMypageMeetingList(@AuthenticationPrincipal UserDetailsImpl userDetails);


}
