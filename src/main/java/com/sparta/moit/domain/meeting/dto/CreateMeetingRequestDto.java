package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateMeetingRequestDto {
    @Schema(description = "미팅 제목", example = "[모각코] 석촌호수 카페 모집중!")
    private String meetingName;
    @Schema(description = "미팅 날짜", example = "2024-04-11")
    private LocalDate meetingDate;
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;
    @Schema(description = "미팅 예산", example = "10000")
    private Integer budget;
    @Schema(description = "미팅 내용", example = "석촌호수 근처 카페에서 모각코 진행합니다! 관심있으신 분은 채팅 참여해주세요 :)")
    private String contents;
    @Schema(description = "총 인원", example = "8")
    private Short totalCount;
    @Schema(description = "미팅 장소", example = "서울특별시 송파구 석촌동 172-24 석촌동 24-11 나인파크A 201호")
    private String locationAddress;
    @Schema(description = "미팅 위도", example = "37.50157")
    private Double locationLat;
    @Schema(description = "미팅 경도", example = "127.040157")
    private Double locationLng;
    @Schema(description = "시-도", example = "서울특별시")
    private String regionFirstName;
    @Schema(description = "시군구", example = "송파구")
    private String regionSecondName;
    @Schema(description = "기술스택 ID 리스트", example = "[1, 2]")
    private List<Long> skillIds;
    @Schema(description = "경력 ID 리스트", example = "[1, 2]")
    private List<Long> careerIds;

    public Meeting toEntity(Member creator) {
        return Meeting.builder()
                .meetingName(this.meetingName)
                .meetingDate(this.meetingDate)
                .meetingStartTime(this.meetingStartTime)
                .meetingEndTime(this.meetingEndTime)
                .budget(this.budget)
                .contents(this.contents)
                .locationAddress(this.locationAddress)
                .registeredCount((short) 1)
                .totalCount(this.totalCount)
                .locationLat(this.locationLat)
                .locationLng(this.locationLng)
                .regionFirstName(this.regionFirstName)
                .regionSecondName(this.regionSecondName)
                .creator(creator)
                .build();
    }
}
