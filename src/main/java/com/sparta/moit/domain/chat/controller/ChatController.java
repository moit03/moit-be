package com.sparta.moit.domain.chat.controller;

import com.sparta.moit.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
}
