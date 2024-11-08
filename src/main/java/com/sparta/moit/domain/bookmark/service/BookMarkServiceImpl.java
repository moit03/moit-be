package com.sparta.moit.domain.bookmark.service;

import com.sparta.moit.domain.bookmark.dto.BookMarkResponseDto;
import com.sparta.moit.domain.bookmark.entity.BookMark;
import com.sparta.moit.domain.bookmark.repository.BookMarkRepository;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {
    private final MeetingRepository meetingRepository;
    private final BookMarkRepository bookMarkRepository;

    @Transactional
    public void addMeetingBookmark(BookMarkResponseDto bookmarkResponseDto, Member member) {
        // TODO: member 인증관련 코드는 UserDetails 에서 처리 해야할 것 같다고 생각
        if (member == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "userDetails Member not found");
        }
        if (isBookmarked(bookmarkResponseDto, member)) {
            throw new CustomException(ErrorCode.ALREADY_BOOKMARKED, "meetingId: ," + bookmarkResponseDto.getMeetingId() + " memberId:"+ member.getId());
        }
        Meeting meeting = meetingRepository.findById(bookmarkResponseDto.getMeetingId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND, "meetingId : " + bookmarkResponseDto.getMeetingId()));

        BookMark bookmark = BookMark.builder()
                .meeting(meeting)
                .member(member)
                .build();

        bookMarkRepository.save(bookmark);
    }

    @Override
    @Transactional
    public void removeMeetingBookmark(BookMarkResponseDto bookmarkResponseDto, Member member) {
        if (member == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "userDetails Member not found");
        }
        if (!isBookmarked(bookmarkResponseDto, member)) {
            throw new CustomException(ErrorCode.NOT_BOOKMARKED, "meetingId: "+ bookmarkResponseDto.getMeetingId()+ "memberId: "+member.getId());
        }
        BookMark bookmark = bookMarkRepository.findByMeetingIdAndMemberId(bookmarkResponseDto.getMeetingId(), member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND,  "meetingId: "+ bookmarkResponseDto.getMeetingId()+ "memberId: "+member.getId()));

        bookMarkRepository.delete(bookmark);
    }

    @Override
    public boolean isBookmarked(BookMarkResponseDto bookmarkResponseDto, Member member) {
        if (member == null) {
            return false;
        }
        return bookMarkRepository.findByMeetingIdAndMemberId(bookmarkResponseDto.getMeetingId(), member.getId()).isPresent();
    }
}
