package com.sparta.moit.global.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "CommonController")
@RestController
@CrossOrigin(origins = "*")
public class CommonController {
    @GetMapping("/")
    public ResponseEntity<?> healthCheck(){
        log.info("Connection OK");
        return ResponseEntity.ok().body("Connection OK");
    }

    @GetMapping("/my-endpoint")
    public String myEndpoint() {
        log.debug("CORS 요청 확인");
        return "Hello";
    }
}
