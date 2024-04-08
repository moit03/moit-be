package com.sparta.moit.domain.meeting.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class GetMyPageDto {
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;
    private Long memberId;

    @QueryProjection
    public GetMyPageDto(LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, Long memberId) {
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.memberId = memberId;
    }
}
