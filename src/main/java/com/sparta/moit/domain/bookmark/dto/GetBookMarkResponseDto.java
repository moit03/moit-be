package com.sparta.moit.domain.bookmark.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetBookMarkResponseDto {
    private final List<Long> bookmarkedMeetingIds;
}
