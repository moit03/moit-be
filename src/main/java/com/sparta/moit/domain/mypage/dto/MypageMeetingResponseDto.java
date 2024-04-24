package com.sparta.moit.domain.mypage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

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
    private final LocalDateTime meetingStartTime;

    @JsonFormat(pattern = "HH:mm")
    private final LocalDateTime meetingEndTime;

    private final String status;

    @Builder
    public MypageMeetingResponseDto(Long meetingId, String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String status) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.status = status;
    }

    public static MypageMeetingResponseDto fromEntity(Meeting meeting){
        String status = meeting.getStatus().toString(); /* Enum을 문자열로 변환 */
        return MypageMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .status(status)
                .build();
    }
}
