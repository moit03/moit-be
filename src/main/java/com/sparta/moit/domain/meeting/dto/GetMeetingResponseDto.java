package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class GetMeetingResponseDto {
    private Long meetingId;
    private String meetingName;
    private Short registeredCount;
    private Short totalCount;
    private Double locationLat;
    private Double locationLng;

    @Builder
    public GetMeetingResponseDto(Long meetingId, String meetingName, Short registeredCount, Short totalCount, Double locationLat, Double locationLng) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
    }

    public static GetMeetingResponseDto fromEntity(Meeting meeting){
        return GetMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .registeredCount(meeting.getRegisteredCount())
                .totalCount(meeting.getTotalCount())
                .locationLat(meeting.getLocationLat())
                .locationLng(meeting.getLocationLng())
                .build();
    }
}
