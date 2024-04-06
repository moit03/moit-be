package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingDetailResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.member.entity.Member;

import java.util.List;

public interface MeetingService {

//    List<GetMeetingResponseDto> getMeetingList(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);

    Long createMeeting(CreateMeetingRequestDto requestDto, Member member);

    Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId);

    List<GetMeetingResponseDto> getFilteredMeetingList(int page, Double locationLat, Double locationLng, List<Short> skillId, List<Short> careerId);

    Long enterMeeting(Member member, Long meetingId);

    List<GetMeetingResponseDto> getMeetingListByAddress(String firstRegion, String secondRegion, int page) throws JsonProcessingException;

    GetMeetingDetailResponseDto getMeetingDetail(Long meetingId, Member member);
}
