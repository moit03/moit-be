package com.sparta.moit.domain.meeting.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "meeting")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Meeting {
    @Column(name = "meeting_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    @Column(name = "meeting_name")
    private String meetingName;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "meeting_datetime")
    private LocalDateTime meetingDatetime;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "contents", length = 2000)
    private String contents;

    @Column(name="registered_count")
    private Short registeredCount;

    @Column(name = "total_count")
    private Short totalCount;

    @Column(name = "first_skill_id")
    private Long firstSkillId;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_long")
    private Double locationLong;

    @Column(name = "region1_depth_name")
    private String region1depthName;

    @Column(name = "region2_depth_name")
    private String region2depthName;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MeetingSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MeetingCareer> careers = new HashSet<>();

    @Builder
    public Meeting(String meetingName, Short registeredCount, Short totalCount, Long firstSkillId, Double locationLat, Double locationLong) {
        this.meetingName = meetingName;
        this.registeredCount = registeredCount;
        this.totalCount = totalCount;
        this.firstSkillId = firstSkillId;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }
}
