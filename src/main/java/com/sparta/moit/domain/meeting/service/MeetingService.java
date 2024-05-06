package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.*;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface MeetingService {

    Long createMeeting(CreateMeetingRequestDto requestDto, Member member);
    Long createMeetingArray(CreateMeetingRequestDto requestDto, Member member);

    Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId);
    Long updateMeetingArray(UpdateMeetingRequestDto requestDto, Member member, Long meetingId);

    void deleteMeeting(Member member, Long meetingId);

    Slice<GetMeetingResponseDto> getMeetingListPostgreJson(int page ,Double locationLat ,Double locationLng ,String skillIdsStr ,String careerIdsStr);

    Slice<GetMeetingArrayResponseDto> getMeetingListPostgreArray(int page, Double locationLat, Double locationLng, String skillIdsStr, String careerIdsStr);

    Slice<GetMeetingResponseDto> getMeetingList(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId);

    List<GetMeetingResponseDto> getMeetingListNativeQuery(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId);

    GetMeetingDetailResponseDto getMeetingDetail(Long meetingId, Optional<Member> member);

    List<GetMeetingResponseDto> getMeetingListByAddress(String firstRegion, String secondRegion, int page) throws JsonProcessingException;

    Slice<GetMeetingResponseDto> getMeetingListBySearch(String keyword, int page);

    Long enterMeeting(Member member, Long meetingId);

    void leaveMeeting(Member member, Long meetingId);

    List<GetPopularResponseDto> getPopularMeeting();
}
