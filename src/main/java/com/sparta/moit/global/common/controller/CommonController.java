package com.sparta.moit.global.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @GetMapping("/")
    public ResponseEntity<?> healthCheck(){
        return ResponseEntity.ok().body("Connection OK");
    }
}
