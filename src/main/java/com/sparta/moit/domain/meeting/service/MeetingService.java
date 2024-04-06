package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.MeetingFilterCondition;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MeetingService {

//    List<GetMeetingResponseDto> getMeetingList(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);

    Long createMeeting(CreateMeetingRequestDto requestDto, Member member);

    Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId);

    Page<GetMeetingResponseDto> getFilteredMeetingList(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId);

    Long enterMeeting(Member member, Long meetingId);

    List<GetMeetingResponseDto> getMeetingListByAddress(String firstRegion, String secondRegion, int page) throws JsonProcessingException;

    List<GetMeetingResponseDto> getMeetingTest(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId);

}
