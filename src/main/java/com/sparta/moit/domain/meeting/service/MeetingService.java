package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.*;
import com.sparta.moit.domain.member.entity.Member;

import java.util.List;

public interface MeetingService {
    List<GetMeetingResponseDto> getMeetingList(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);

    Long createMeeting(CreateMeetingRequestDto requestDto, Member member);

    Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId);
}
