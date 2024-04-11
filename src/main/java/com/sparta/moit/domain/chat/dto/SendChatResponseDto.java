package com.sparta.moit.domain.chat.dto;

import com.sparta.moit.domain.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SendChatResponseDto {
    private final Long chatId;
    private final SenderResponseDto sender;
    private final String content;
    private final LocalDateTime createdAt;
    @Builder
    public SendChatResponseDto(Long chatId, SenderResponseDto sender, String content, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static SendChatResponseDto fromEntity(Chat chat) {
        SenderResponseDto sender = SenderResponseDto.fromEntity(chat.getMember());
        return SendChatResponseDto.builder()
                .chatId(chat.getId())
                .sender(sender)
                .content(chat.getContent())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
