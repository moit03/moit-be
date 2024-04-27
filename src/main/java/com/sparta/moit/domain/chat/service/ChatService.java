package com.sparta.moit.domain.chat.service;

import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.dto.SendChatRequestDto;
import com.sparta.moit.domain.chat.dto.SendChatResponseDto;
import com.sparta.moit.domain.member.entity.Member;

import java.time.LocalDateTime;

public interface ChatService {
    ChatResponseDto getChatList(Long meetingId, int page, LocalDateTime userEnterTime, Member member);

    SendChatResponseDto sendChat(Long meetingId, String email, SendChatRequestDto chatRequest);
}
