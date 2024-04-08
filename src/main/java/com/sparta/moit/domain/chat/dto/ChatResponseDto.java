package com.sparta.moit.domain.chat.dto;

import com.sparta.moit.domain.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatResponseDto {

    private final Long meetingId;

    private final List<SingleChatResponseDto> chats;

    @Builder
    public ChatResponseDto(Long meetingId, List<SingleChatResponseDto> chats) {
        this.meetingId = meetingId;
        this.chats = chats;
    }

    public static ChatResponseDto fromEntity(List<Chat> chatEntityList) {
        List<SingleChatResponseDto> chats = chatEntityList.stream()
                .map(SingleChatResponseDto::fromEntity)
                .toList();
        return ChatResponseDto.builder()
                .meetingId(chatEntityList.get(0).getMeeting().getId())
                .chats(chats).build();
    }
}
