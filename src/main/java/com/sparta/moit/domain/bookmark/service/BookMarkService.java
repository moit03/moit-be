package com.sparta.moit.domain.bookmark.service;

import com.sparta.moit.domain.bookmark.dto.BookMarkResponseDto;
import com.sparta.moit.domain.member.entity.Member;

public interface BookMarkService {
    void addMeetingBookmark(BookMarkResponseDto bookmarkResponseDto, Member member);
    void removeMeetingBookmark(BookMarkResponseDto bookmarkResponseDto, Member member);
    boolean isBookmarked(BookMarkResponseDto bookmarkResponseDto, Member member);

}
