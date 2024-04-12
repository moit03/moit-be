package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingDetailResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MeetingService {

//    List<GetMeetingResponseDto> getMeetingList(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);

    Long createMeeting(CreateMeetingRequestDto requestDto, Member member);

    Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId);

    void deleteMeeting(Member member, Long meetingId);

    Slice<GetMeetingResponseDto> getMeetingList(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId);

    List<GetMeetingResponseDto> getMeetingListJpql(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId);

    GetMeetingDetailResponseDto getMeetingDetail(Long meetingId);

    GetMeetingDetailResponseDto getMeetingDetail(Long meetingId, Member member);

    List<GetMeetingResponseDto> getMeetingListByAddress(String firstRegion, String secondRegion, int page) throws JsonProcessingException;

    Slice<GetMeetingResponseDto> getMeetingListBySearch(String keyword, int page);

    Long enterMeeting(Member member, Long meetingId);

    void leaveMeeting(Member member, Long meetingId);
}
