package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.mypage.dto.MypageMeetingResponseDto;
import com.sparta.moit.domain.mypage.dto.MypageResponseDto;


import java.time.LocalDateTime;
import java.util.List;

public interface MypageService {
    MypageResponseDto getMypageInfo(Member member);
    long calculateStudyTime(LocalDateTime meetingStartTime, LocalDateTime meetingEndTime);
    List<MypageMeetingResponseDto> getMypageMeetingList(Long memberId);
    List<MypageMeetingResponseDto> getMypageHeldList(Long memberId);
    List<MypageMeetingResponseDto> getCompletedMeetings(Long memberId);
}
