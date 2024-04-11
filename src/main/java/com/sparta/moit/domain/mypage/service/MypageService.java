package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.mypage.dto.MypageResponseDto;


import java.time.LocalDateTime;

public interface MypageService {
    MypageResponseDto getMypageInfo(Member member);
    long calculateStudyTime(LocalDateTime meetingStartTime, LocalDateTime meetingEndTime);

}
