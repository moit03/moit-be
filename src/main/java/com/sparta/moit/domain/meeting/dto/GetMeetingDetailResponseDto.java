package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class GetMeetingDetailResponseDto {

    private final Long meetingId;
    private final String meetingName;
    private final String creatorName;
    private final String creatorEmail;
    private final List<String> careerNameList;
    private final List<String> skillNameList;
    private final LocalDate meetingDate;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime meetingEndTime;

    private final String locationAddress;

    private final Short registeredCount;
    private final Short totalCount;
    private final Integer budget;
    private final String contents;
    private final Double locationLat;
    private final Double locationLng;
    private final boolean isJoin;

    @Builder
    public GetMeetingDetailResponseDto(Long meetingId, String meetingName, String creatorName, String creatorEmail, List<String> careerNameList, List<String> skillNameList, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String locationAddress, Short registeredCount, Short totalCount, Integer budget, String contents, Double locationLat, Double locationLng, boolean isJoin) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.careerNameList = careerNameList;
        this.skillNameList = skillNameList;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.locationAddress = locationAddress;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.budget = budget;
        this.contents = contents;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.isJoin = isJoin;
    }

    public static GetMeetingDetailResponseDto fromEntity(Meeting meeting, List<String> careerNameList, List<String> skillNameList, boolean isJoin) {
        return GetMeetingDetailResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .creatorName(meeting.getCreator().getUsername())
                .creatorEmail(meeting.getCreator().getEmail())
                .careerNameList(careerNameList)
                .skillNameList(skillNameList)
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .locationAddress(meeting.getLocationAddress())
                .registeredCount(meeting.getRegisteredCount())
                .totalCount(meeting.getTotalCount())
                .budget(meeting.getBudget())
                .contents(meeting.getContents())
                .locationLat(meeting.getLocationLat())
                .locationLng(meeting.getLocationLng())
                .isJoin(isJoin)
                .build();
    }
}
