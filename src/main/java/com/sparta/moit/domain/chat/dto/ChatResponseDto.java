package com.sparta.moit.domain.chat.dto;

import com.sparta.moit.domain.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChatResponseDto {

    private final Long meetingId;

    private final Slice<SingleChatResponseDto> chats;

    @Builder
    public ChatResponseDto(Long meetingId, Slice<SingleChatResponseDto> chats) {
        this.meetingId = meetingId;
        this.chats = chats;
    }

    public static ChatResponseDto fromEntity(Slice<Chat> chatEntitySlice, Long meetingId) {
        List<SingleChatResponseDto> chatDtos = chatEntitySlice.getContent().stream()
                .map(SingleChatResponseDto::fromEntity)
                .collect(Collectors.toList());

        Slice<SingleChatResponseDto> chatDtoSlice = new SliceImpl<>(chatDtos, chatEntitySlice.getPageable(), chatEntitySlice.hasNext());

        return ChatResponseDto.builder()
                .meetingId(meetingId)
                .chats(chatDtoSlice).build();
    }
}
