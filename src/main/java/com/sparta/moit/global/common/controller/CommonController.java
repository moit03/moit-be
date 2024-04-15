package com.sparta.moit.global.common.controller;

import com.sparta.moit.domain.member.dto.LoginRequestDto;
import com.sparta.moit.domain.member.dto.MemberResponseDto;
import com.sparta.moit.domain.member.service.MemberService;
import com.sparta.moit.global.common.dto.RefreshTokenResponse;
//import com.sparta.moit.global.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공통", description = "공통 API")
@Slf4j(topic = "CommonController")
@RestController
public class CommonController {

//    private final TestService testService;
//
//    @Autowired
//    public CommonController(TestService testService) {
//        this.testService = testService;
//    }

    @Operation(summary = "연결 체크", description = "연결 체크 API")
    @GetMapping("/")
    public ResponseEntity<?> healthCheck() {
        log.info("Connection OK");
        return ResponseEntity.ok().body("Connection OK");
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            // 사용자의 토큰 생성
            String token = testService.generateUserToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            if (token != null && !token.isEmpty()) {
                // 생성된 토큰을 응답 헤더에 추가
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Authorization", token);

                return ResponseEntity.ok().headers(responseHeaders).body("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Failed to generate token");
            }
        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("로그인 실패");
        }
    }*/
//    @GetMapping("/login")
//    public ResponseEntity<?> login(){
//        MemberResponseDto responseDto = testService.login();
//        return ResponseEntity.ok().body(responseDto);
//    }

}