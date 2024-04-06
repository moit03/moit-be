package com.sparta.moit.domain.chat.controller;

import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.service.ChatService;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{meetingId}/chats")
    /*
    * 채팅방 입장 전 채팅 목록 불러오기
    * */
    public ResponseEntity<?> getChatList(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChatResponseDto responseDto = chatService.getChatList(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("채팅 불러오기 완료", responseDto));
    }
}
