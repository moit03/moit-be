package com.sparta.moit.domain.bookmark.repository;

import com.sparta.moit.domain.bookmark.entity.BookMark;
import com.sparta.moit.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByMeetingIdAndMemberId(Long meetingId, Long memberId);
    @Query("SELECT b.meeting FROM BookMark b WHERE b.member.id = :memberId and b.meeting.status != 'DELETE' " +
            "ORDER BY CASE WHEN b.meeting.status = 'OPEN' OR b.meeting.status = 'FULL' THEN b.meeting.meetingDate END ASC, " +
            "CASE WHEN b.meeting.status = 'COMPLETE' THEN b.meeting.meetingDate END DESC")
    List<Meeting> findBookmarkedMeetingsByMemberId(Long memberId);
    boolean existsByMemberIdAndMeetingId(Long memberId, Long meetingId);
    List<BookMark> findByMemberId(Long memberId);
}
