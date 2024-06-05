package com.sparta.moit.domain.chat.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SendChatRequestDto {
    @Size(max = 10000, message = "채팅 내용은 최대 10,000자까지 입력 가능합니다.")
    private String content;
}
