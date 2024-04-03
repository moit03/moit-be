package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateMeetingResponseDto {
    private Long meetingId;
    private String meetingName;
    private LocalDate meetingDate;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingEndTime;
    private String locationAddress;
    private Integer budget;
    private String contents;
    private Short registeredCount;
    private Short totalCount;
    private Double locationLat;
    private Double locationLng;
    private String regionFirstName;
    private String regionSecondName;
    private List<Skill> skillList;
    private List<Career> careerList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public CreateMeetingResponseDto(Long meetingId, String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String locationAddress, Integer budget, String contents, Short registeredCount, Short totalCount, Double locationLat, Double locationLng, String regionFirstName, String regionSecondName, List<Skill> skillList, List<Career> careerList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.budget = budget;
        this.contents = contents;
        this.locationAddress = locationAddress;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.regionFirstName = regionFirstName;
        this.regionSecondName = regionSecondName;
        this.skillList = skillList;
        this.careerList = careerList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CreateMeetingResponseDto fromEntity(Meeting meeting) {
        List<Skill> skillList = meeting.getSkills().stream()
                .map(MeetingSkill::getSkill)
                .collect(Collectors.toList());

        List<Career> careerList = meeting.getCareers().stream()
                .map(MeetingCareer::getCareer)
                .collect(Collectors.toList());

        return CreateMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .budget(meeting.getBudget())
                .contents(meeting.getContents())
                .registeredCount(meeting.getRegisteredCount())
                .totalCount(meeting.getTotalCount())
                .locationAddress(meeting.getLocationAddress())
                .locationLat(meeting.getLocationLat())
                .locationLng(meeting.getLocationLng())
                .regionFirstName(meeting.getRegionFirstName())
                .regionSecondName(meeting.getRegionSecondName())
                .skillList(skillList)
                .careerList(careerList)
                .createdAt(meeting.getCreatedAt())
                .modifiedAt(meeting.getModifiedAt())
                .build();
    }
}
