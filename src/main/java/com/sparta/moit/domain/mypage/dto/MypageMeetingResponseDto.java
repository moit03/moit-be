package com.sparta.moit.domain.mypage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
public class MypageMeetingResponseDto {
    private final Long meetingId;
    private final String meetingName;
    private final LocalDate meetingDate;
    @JsonFormat(pattern = "HH:mm")
    private final ZonedDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm")
    private final ZonedDateTime meetingEndTime;

    @Builder
    public MypageMeetingResponseDto(Long meetingId, String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        this.meetingEndTime = meetingEndTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));

    }

    public static MypageMeetingResponseDto fromEntity(Meeting meeting){
        return MypageMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .build();
    }
}
