package com.sparta.moit.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @GetMapping("/")
    public ResponseEntity<?> healthyCheck(){
        return ResponseEntity.ok().body("Connection OK");
    }
}
