package com.sparta.moit.domain.chat.controller;

import com.sparta.moit.domain.chat.dto.SendChatRequestDto;
import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.service.ChatService;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "채팅 Controller")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /* 채팅방 입장 전 채팅 목록 불러오기 */
    @GetMapping("/{meetingId}/chats")
    public ResponseEntity<?> getChatList(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("채팅방 입장 meeting id " + meetingId.toString());
        log.info("로그인 유저 이메일 " + userDetails.getUser().getEmail());
        ChatResponseDto responseDto = chatService.getChatList(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("채팅 불러오기 완료", responseDto));
    }
    /* 채팅 보내기, 구독하기 */
    @Transactional
    @MessageMapping("/api/meetings/{meetingId}/chat/{memberId}")
    public void sendChat(@DestinationVariable Long meetingId
//            , @AuthenticationPrincipal UserDetailsImpl userDetails
            , @RequestBody SendChatRequestDto sendChatRequestDto) {
        log.info("온 메시지 " + sendChatRequestDto.getContent());
        log.info("미팅 id " + meetingId.toString());
//        log.info("로그인 유저 이메일 " + userDetails.getUser().getEmail());
//        Member member = userDetails.getUser();
        Member member = new Member("이예진", "$2a$10$KFUTpJ/3P2.N4iwVaIi/xuiP3squLvUMMzzF14I8sUoc4Z0BFbNhK", "dldulwls@naver.com", UserRoleEnum.USER);
        messagingTemplate.convertAndSend("/topic/rooms/" + meetingId + "/chat", chatService.sendChat(meetingId, member, sendChatRequestDto));
    }

}
