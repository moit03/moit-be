package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<Skill> skillList;
    private List<Career> careerList;


    @Builder
    public GetMeetingResponseDto(Long meetingId, String meetingName, Short registeredCount, Short totalCount, Double locationLat, Double locationLng, List<Skill> skillList, List<Career> careerList) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
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
                .skillList(skillList)
                .careerList(careerList)
                .build();
    }
}
