package com.sparta.moit.domain.chat.dto;

import com.sparta.moit.domain.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class SingleChatResponseDto {
    private final Long chatId;
    private final SenderResponseDto sender;
    private final String content;
    private final LocalDateTime createdAt;

    @Builder
    public SingleChatResponseDto(Long chatId, SenderResponseDto sender, String content, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static SingleChatResponseDto fromEntity(Chat chat) {
        return SingleChatResponseDto.builder()
                .chatId(chat.getId())
                .sender(SenderResponseDto.fromEntity(chat.getMember()))
                .content(chat.getContent())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
