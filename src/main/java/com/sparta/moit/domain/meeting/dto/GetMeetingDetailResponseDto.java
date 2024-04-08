package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetMeetingDetailResponseDto {

    private Long meetingId;
    private String meetingName;

    private String creatorName;

    private List<String> careerNameList;
    private List<String> skillNameList;

    private LocalDate meetingDate;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime meetingEndTime;

    private String locationAddress;

    private Short registeredCount;
    private Short totalCount;

    private Integer budget;

    private String contents;

    private Double locationLat;
    private Double locationLng;

    @Builder
    public GetMeetingDetailResponseDto(Long meetingId, String meetingName, String creatorName, List<String> careerNameList, List<String> skillNameList, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, String locationAddress, Short registeredCount, Short totalCount, Integer budget, String contents, Double locationLat, Double locationLng) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.creatorName = creatorName;
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
    }

    public static GetMeetingDetailResponseDto fromEntity(Meeting meeting, List<String> careerNameList, List<String> skillNameList) {
        return GetMeetingDetailResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .creatorName(meeting.getCreator().getUsername())
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
                .build();
    }
}
