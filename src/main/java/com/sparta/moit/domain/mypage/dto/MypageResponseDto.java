package com.sparta.moit.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MypageResponseDto {
    private int enterMeeting;
    private String studyTime;
    private int heldMeeting;

    @Builder
    public MypageResponseDto(int enterMeeting, String studyTime, int heldMeeting) {
        this.enterMeeting = enterMeeting;
        this.studyTime = studyTime;
        this.heldMeeting = heldMeeting;

    }
}
