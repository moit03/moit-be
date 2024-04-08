package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.mypage.dto.MypageResponseDto;


import java.time.LocalDateTime;

public interface MypageService {
    MypageResponseDto getMypageInfo(Member member, Long memberId);
    String calculateStudyTime(Long memberId, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime);

}
