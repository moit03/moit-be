package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor()
public class GetPopularResponseDto {
    private Long meetingId;
    private String meetingName;
    private LocalDate meetingDate;
    private String locationAddress;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingEndTime;



    @Builder
    public GetPopularResponseDto(Long meetingId, String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String locationAddress) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.locationAddress = locationAddress;
    }

    public static GetPopularResponseDto fromEntity(Meeting meeting){
        String formattedLocationAddress = formatLocationAddress(meeting.getLocationAddress());

        return GetPopularResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .locationAddress(formattedLocationAddress)
                .build();
    }

     /* locationAddress 포맷 변경 메서드 */
    private static String formatLocationAddress(String locationAddress) {
        String[] parts = locationAddress.split("\\s+");
        StringBuilder formattedAddress = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            formattedAddress.append(parts[i]);
            if (i == 0) {
                formattedAddress.append(" ");
            }
        }
        return formattedAddress.toString();
    }

    /* meetingDate 형식 변경 메서드 */
    private static String formatMeetingDate(LocalDate meetingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E");
        return meetingDate.format(formatter);
    }
}
