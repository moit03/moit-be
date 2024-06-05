package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.dto.GetMyPageDto;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface MeetingRepositoryCustom {
    Slice<Meeting> getMeetingSlice(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, Pageable pageable);

    Slice<Meeting> findByKeyword(String keyword, Pageable pageable);

    List<GetMyPageDto> getMyPage(Long memberId, MeetingStatusEnum status);

    List<Meeting> findMeetingsByMember(Long memberId);

    List<Meeting> findAllIncompleteMeetingsForHour();

    List<Meeting> getPopularMeetings();

    List<Meeting> findHeldMeetingsByCreatorId(Long memberId);
}
