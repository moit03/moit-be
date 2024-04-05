package com.sparta.moit.domain.meeting.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "meeting")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meeting extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "meeting_name")
    private String meetingName;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "meeting_start_time")
    private LocalDateTime meetingStartTime;

    @Column(name = "meeting_end_time")
    private LocalDateTime meetingEndTime;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "contents", length = 2000)
    private String contents;

    @Column(name = "registered_count")
    private Short registeredCount;

    @Column(name = "total_count")
    private Short totalCount;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_lng")
    private Double locationLng;

    @Column(name = "region_first_name")
    private String regionFirstName;

    @Column(name = "region_second_name")
    private String regionSecondName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member creator;

    @OneToMany(mappedBy = "meeting")
    @JsonIgnore
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MeetingSkill> skills = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MeetingCareer> careers = new ArrayList<>();

    @Builder
    public Meeting(String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime, Integer budget,
                   String locationAddress, String contents, Short registeredCount, Short totalCount,
                   Double locationLat, Double locationLng, String regionFirstName, String regionSecondName, Member creator, List<MeetingMember> meetingMembers) {
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.budget = budget;
        this.locationAddress = locationAddress;
        this.contents = contents;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.regionFirstName = regionFirstName;
        this.regionSecondName = regionSecondName;
        this.creator = creator;
        this.meetingMembers = meetingMembers;
    }

    public void updateMeeting(UpdateMeetingRequestDto requestDto) {
        this.meetingName = requestDto.getMeetingName();
        this.budget = requestDto.getBudget();
        this.locationAddress = requestDto.getLocationAddress();
        this.contents = requestDto.getContents();
        this.totalCount = requestDto.getTotalCount();
        this.locationLat = requestDto.getLocationLat();
        this.locationLng = requestDto.getLocationLng();
        this.regionFirstName = requestDto.getRegionFirstName();
        this.regionSecondName = requestDto.getRegionSecondName();
    }

    public void addMeetingMember(MeetingMember meetingMember) {
        this.meetingMembers.add(meetingMember);
    }

}
