package com.sparta.moit.global.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.global.common.dto.AddressResponseDto;
import com.sparta.moit.global.util.AddressUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "공통", description = "공통 API")
@Slf4j(topic = "CommonController")
@RestController
public class CommonController {

    private final AddressUtil addressUtil;

    public CommonController(AddressUtil addressUtil) {
        this.addressUtil = addressUtil;
    }

    @Operation(summary = "연결 체크", description = "연결 체크 API")
    @GetMapping("/")
    public ResponseEntity<?> healthCheck(){
        log.info("Connection OK");
        return ResponseEntity.ok().body("Connection OK");
    }

    @GetMapping("/address")
    public ResponseEntity<?> xyTest() throws JsonProcessingException {
        AddressResponseDto addressResponseDtos = addressUtil.searchAddress("서울특별시", "강남구");
        return ResponseEntity.ok().body(addressResponseDtos);
    }
}
