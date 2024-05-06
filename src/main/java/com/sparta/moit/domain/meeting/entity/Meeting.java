package com.sparta.moit.domain.meeting.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.moit.domain.meeting.dto.CareerResponseDto;
import com.sparta.moit.domain.meeting.dto.SkillResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.common.entity.Timestamped;
import com.sparta.moit.global.util.PointUtil;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.moit.global.util.CareerMapper.createCareerResponseList;
import static com.sparta.moit.global.util.SkillMapper.createSkillResponseList;

@Entity(name = "meeting")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meeting extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @Column(columnDefinition = "geography(Point,4326)")
    private Point locationPosition;

    @Column(name = "region_first_name")
    private String regionFirstName;

    @Column(name = "region_second_name")
    private String regionSecondName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MeetingStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member creator;

    @OneToMany(mappedBy = "meeting")
    @JsonIgnore
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    @Type(JsonType.class)
    @Column(name = "skill_list",columnDefinition = "jsonb")
    private List<SkillResponseDto> skillList = new ArrayList<>();

    @Type(JsonType.class)
    @Column(name = "career_list",columnDefinition = "jsonb")
    private List<CareerResponseDto> careerList = new ArrayList<>();

    @Column(name = "career_id_list", columnDefinition = "bigint[]")
    private Long[] careerIdList;

    @Column(name = "skill_id_list", columnDefinition = "bigint[]")
    private Long[] skillIdList;


    @Builder
    public Meeting(Long id, String meetingName, LocalDate meetingDate, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime,
                   Integer budget, String locationAddress, String contents, Short registeredCount, Short totalCount,
                   Double locationLat, Double locationLng, Point locationPosition, String regionFirstName, String regionSecondName, MeetingStatusEnum status,
                   Member creator, List<SkillResponseDto> skillList, List<CareerResponseDto> careerList, Long[] careerIdList,
                   Long[] skillIdList) {
        this.id = id;
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
        this.locationPosition = locationPosition;
        this.regionFirstName = regionFirstName;
        this.regionSecondName = regionSecondName;
        this.status = MeetingStatusEnum.OPEN;
        this.creator = creator;
        this.skillList = skillList;
        this.careerList = careerList;
        this.skillIdList = skillIdList;
        this.careerIdList = careerIdList;
    }

    public void updateMeeting(UpdateMeetingRequestDto requestDto) {
        List<SkillResponseDto> skillList = createSkillResponseList(requestDto.getSkillIds());

        List<CareerResponseDto> careerList = createCareerResponseList(requestDto.getCareerIds());

        this.meetingName = requestDto.getMeetingName();
        this.budget = requestDto.getBudget();
        this.locationAddress = requestDto.getLocationAddress();
        this.contents = requestDto.getContents();
        this.totalCount = requestDto.getTotalCount();
        this.locationLat = requestDto.getLocationLat();
        this.locationLng = requestDto.getLocationLng();
        this.locationPosition = PointUtil.createPointFromLngLat(requestDto.getLocationLng(), requestDto.getLocationLat());
        this.regionFirstName = requestDto.getRegionFirstName();
        this.regionSecondName = requestDto.getRegionSecondName();
        this.skillList = skillList;
        this.careerList = careerList;
    }
    public void updateMeetingArray(UpdateMeetingRequestDto requestDto) {
        Long[] skillArray = requestDto.getSkillIds().toArray(new Long[0]);
        Long[] careerArray = requestDto.getCareerIds().toArray(new Long[0]);

        this.meetingName = requestDto.getMeetingName();
        this.budget = requestDto.getBudget();
        this.locationAddress = requestDto.getLocationAddress();
        this.contents = requestDto.getContents();
        this.totalCount = requestDto.getTotalCount();
        this.locationLat = requestDto.getLocationLat();
        this.locationLng = requestDto.getLocationLng();
        this.locationPosition = PointUtil.createPointFromLngLat(requestDto.getLocationLng(), requestDto.getLocationLat());
        this.regionFirstName = requestDto.getRegionFirstName();
        this.regionSecondName = requestDto.getRegionSecondName();
        this.skillIdList = skillArray;
        this.careerIdList = careerArray;
    }

    public Short incrementRegisteredCount() {
        return registeredCount++;
    }

    public void decrementRegisteredCount() {
        if (registeredCount > 0) {
            registeredCount--;
        }
    }

    public void updateStatus() {
        this.status = MeetingStatusEnum.FULL;
    }

    public void addMeetingMember(MeetingMember meetingMember) {
        this.meetingMembers.add(meetingMember);
    }

    public void deleteStatus() {
        this.status = MeetingStatusEnum.DELETE;
    }

    public void completeStatus() {
        this.status = MeetingStatusEnum.COMPLETE;
    }

}

