package com.sparta.moit.domain.bookmark.service;

import com.sparta.moit.domain.bookmark.dto.BookMarkDto;
import com.sparta.moit.domain.member.entity.Member;

public interface BookMarkService {
    void addMeetingBookmark(BookMarkDto bookmarkDto, Member member);
    void removeMeetingBookmark(BookMarkDto bookmarkDto, Member member);
    boolean isBookmarked(BookMarkDto bookmarkDto, Member member);

}
