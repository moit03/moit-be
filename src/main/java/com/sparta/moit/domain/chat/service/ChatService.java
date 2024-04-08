package com.sparta.moit.domain.chat.service;

import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.dto.SendChatRequestDto;
import com.sparta.moit.domain.chat.dto.SendChatResponseDto;
import com.sparta.moit.domain.member.entity.Member;

public interface ChatService {
    ChatResponseDto getChatList(Member member, Long meetingId);

    SendChatResponseDto sendChat(Long meetingId, SendChatRequestDto chatRequest);
}
