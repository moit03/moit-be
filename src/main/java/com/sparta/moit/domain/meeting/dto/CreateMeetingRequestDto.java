package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.*;
import com.sparta.moit.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateMeetingRequestDto {
    private String meetingName;
    private LocalDate meetingDate;
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;
    private Integer budget;
    private String contents;
    private Short registeredCount;
    private Short totalCount;
    private String locationAddress;
    private Double locationLat;
    private Double locationLng;
    private String regionFirstName;
    private String regionSecondName;
    private List<Long> skillIds;
    private List<Long> careerIds;

    public Meeting toEntity(Member member) {
        return Meeting.builder()
                .meetingName(this.meetingName)
                .meetingDate(this.meetingDate)
                .meetingStartTime(this.meetingStartTime)
                .meetingEndTime(this.meetingEndTime)
                .budget(this.budget)
                .contents(this.contents)
                .locationAddress(this.locationAddress)
                .registeredCount(this.registeredCount)
                .totalCount(this.totalCount)
                .locationLat(this.locationLat)
                .locationLng(this.locationLng)
                .regionFirstName(this.regionFirstName)
                .regionSecondName(this.regionSecondName)
                .build();
    }
}
