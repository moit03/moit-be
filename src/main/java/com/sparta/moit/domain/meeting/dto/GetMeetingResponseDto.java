package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor()
public class GetMeetingResponseDto {
    private Long meetingId;
    private String meetingName;
    private Short registeredCount;
    private Short totalCount;
    private Double locationLat;
    private Double locationLng;
    private String locationAddress;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingEndTime;
    private List<Skill> skillList;
    private List<Career> careerList;



    @Builder
    public GetMeetingResponseDto(Long meetingId, String meetingName, Short registeredCount, Short totalCount, Double locationLat, Double locationLng, List<Skill> skillList, List<Career> careerList, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String locationAddress) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.locationAddress = locationAddress;
        this.skillList = skillList;
        this.careerList = careerList;
    }

    public static GetMeetingResponseDto fromEntity(Meeting meeting){
        List<Skill> skillList = meeting.getSkills().stream()
                .map(MeetingSkill::getSkill)
                .collect(Collectors.toList());
        List<Career> careerList = meeting.getCareers().stream()
                .map(MeetingCareer::getCareer)
                .collect(Collectors.toList());

        return GetMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .registeredCount(meeting.getRegisteredCount())
                .totalCount(meeting.getTotalCount())
                .locationLat(meeting.getLocationLat())
                .locationLng(meeting.getLocationLng())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .locationAddress(meeting.getLocationAddress())
                .skillList(skillList)
                .careerList(careerList)
                .build();
    }
}
