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
    private Long firstSkillId;
    private Double locationLat;
    private Double locationLong;

    @Builder
    public GetMeetingResponseDto(Long meetingId, String meetingName, Short registeredCount, Short totalCount, Long firstSkillId, Double locationLat, Double locationLong) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.firstSkillId = firstSkillId;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }

    public static GetMeetingResponseDto fromEntity(Meeting meeting){
        return GetMeetingResponseDto.builder()
                .meetingId(meeting.getMeetingId())
                .meetingName(meeting.getMeetingName())
                .registeredCount(meeting.getRegisteredCount())
                .totalCount(meeting.getTotalCount())
                .firstSkillId(meeting.getFirstSkillId())
                .locationLat(meeting.getLocationLat())
                .locationLong(meeting.getLocationLong())
                .build();
    }
}
