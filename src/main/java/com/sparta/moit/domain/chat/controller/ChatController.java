package com.sparta.moit.domain.chat.controller;

import com.sparta.moit.domain.chat.controller.docs.ChatControllerDocs;
import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.dto.SendChatRequestDto;
import com.sparta.moit.domain.chat.service.ChatService;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j(topic = "채팅 Controller")
@RestController
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /* 채팅방 입장 전 채팅 목록 불러오기 */
    @GetMapping("/api/meetings/{meetingId}/chats/{userEnterTime}")
    public ResponseEntity<ResponseDto<ChatResponseDto>> getChatList(@PathVariable Long meetingId
            , @RequestParam(defaultValue = "1") int page
            , @PathVariable("userEnterTime")
              @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime userEnterTime
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChatResponseDto responseDto = chatService.getChatList(meetingId, page, userEnterTime, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("채팅 불러오기 완료", responseDto));
    }

    /* 채팅 보내기, 구독하기 */
    @MessageMapping("/api/meetings/{meetingId}/chat")
    public void sendChat(@DestinationVariable Long meetingId
            , Principal principal
            , @RequestBody SendChatRequestDto sendChatRequestDto) {
        String email = principal.getName();
        messagingTemplate.convertAndSend("/topic/rooms/" + meetingId + "/chat", chatService.sendChat(meetingId, email, sendChatRequestDto));
    }
}
