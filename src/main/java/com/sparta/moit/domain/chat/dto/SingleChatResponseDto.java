package com.sparta.moit.domain.chat.dto;

import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SingleChatResponseDto {
    @Schema(description = "채팅 id", example = "1")
    private final Long chatId;
    @Schema(description = "발신자 정보", example = "{'memberId': '1', '': ''}")
    private final SenderResponseDto sender;
    private final String content;
    private final LocalDateTime createdAt;

    @Getter
    static class SenderResponseDto {
        private final Long memberId;
        private final String memberName;

        @Builder
        private SenderResponseDto(Long memberId, String memberName) {
            this.memberId = memberId;
            this.memberName = memberName;
        }

        private static SenderResponseDto fromEntity(Member member) {
            return SenderResponseDto.builder()
                    .memberId(member.getId())
                    .memberName(member.getUsername())
                    .build();
        }
    }

    @Builder
    public SingleChatResponseDto(Long chatId, SenderResponseDto sender, String content, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static SingleChatResponseDto fromEntity(Chat chat) {
        SenderResponseDto sender = SenderResponseDto.fromEntity(chat.getMember());
        return SingleChatResponseDto.builder()
                        .chatId(chat.getId())
                        .sender(sender)
                        .content(chat.getContent())
                        .createdAt(chat.getCreatedAt())
                        .build();
    }
}
