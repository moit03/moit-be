package com.sparta.moit.domain.bookmark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class BookMarkRequestDto {
    private Long meetingId;
    public BookMarkRequestDto(Long meetingId) {
        this.meetingId = meetingId;
    }
}
