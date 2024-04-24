package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateMeetingResponseDto {
    private final Long meetingId;
    private final String meetingName;
    private final LocalDate meetingDate;
    private final ZonedDateTime meetingStartTime;
    private final ZonedDateTime meetingEndTime;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
//    private final LocalDateTime meetingStartTime;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
//    private final LocalDateTime meetingEndTime;
    private final String locationAddress;
    private final Integer budget;
    private final String contents;
    private final Short registeredCount;
    private final Short totalCount;
    private final Double locationLat;
    private final Double locationLng;
    private final String regionFirstName;
    private final String regionSecondName;
    private final List<Skill> skillList;
    private final List<Career> careerList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime modifiedAt;

    @Builder
    public CreateMeetingResponseDto(Long meetingId, String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String locationAddress, Integer budget, String contents, Short registeredCount, Short totalCount, Double locationLat, Double locationLng, String regionFirstName, String regionSecondName, List<Skill> skillList, List<Career> careerList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        this.meetingEndTime = meetingEndTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
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

        LocalDateTime startTime = meeting.getMeetingStartTime().toLocalDateTime();
        LocalDateTime endTime = meeting.getMeetingEndTime().toLocalDateTime();

        ZonedDateTime seoulStartTime = startTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        ZonedDateTime seoulEndTime = endTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        return CreateMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(seoulStartTime.toLocalDateTime())
                .meetingEndTime(seoulEndTime.toLocalDateTime())
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
