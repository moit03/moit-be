package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import com.sparta.moit.global.util.CareerMapper;
import com.sparta.moit.global.util.SkillMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetMeetingArrayResponseDto {
    private Long meetingId;
    private String meetingName;
    private Short registeredCount;
    private Short totalCount;
    private LocalDate meetingDate;
    private Double locationLat;
    private Double locationLng;
    private String locationAddress;
    @JsonFormat(pattern = "HH:mm") // 타임존 아시아 있었음
    private LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime meetingEndTime;
    private List<SkillDto> skillList;
    private List<CareerDto> careerList;
    private MeetingStatusEnum status;

    @Builder
    GetMeetingArrayResponseDto(
            Long meetingId, String meetingName, Short registeredCount, Short totalCount, Double locationLat,
            Double locationLng,  List<SkillDto> skillList, List<CareerDto> careerList, LocalDate meetingDate, LocalDateTime meetingStartTime,
            LocalDateTime meetingEndTime, String locationAddress, MeetingStatusEnum status)
    {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.meetingDate = meetingDate;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.locationAddress = locationAddress;
        this.skillList = skillList;
        this.careerList = careerList;
        this.status = status;
    }
    public static GetMeetingArrayResponseDto fromEntity(Meeting meeting) {

        SkillMapper skillMapper = new SkillMapper();
        List<SkillDto> skillList = skillMapper.createSkillResponseList(meeting.getSkillIdList());

        CareerMapper careerMapper = new CareerMapper();
        List<CareerDto> careerList = careerMapper.createCareerResponseList(meeting.getCareerIdList());


        return GetMeetingArrayResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .registeredCount(meeting.getRegisteredCount())
                .totalCount(meeting.getTotalCount())
                .locationLat(meeting.getLocationLat())
                .locationLng(meeting.getLocationLng())
                .meetingDate(meeting.getMeetingDate())
                .meetingStartTime(meeting.getMeetingStartTime())
                .meetingEndTime(meeting.getMeetingEndTime())
                .locationAddress(meeting.getLocationAddress())
                .skillList(skillList)
                .careerList(careerList)
                .status(meeting.getStatus())
                .build();
    }

}
