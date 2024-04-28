package com.sparta.moit.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.util.CareerMapper;
import com.sparta.moit.global.util.SkillMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j(topic = "CreateRequestDto")
@Getter
public class CreateMeetingRequestDto {
    @Schema(description = "미팅 제목", example = "[모각코] 석촌호수 카페 모집중!")
    @NotNull(message = "모임 이름 작성은 필수입니다.")
    private String meetingName;

    @Schema(description = "미팅 날짜", example = "2024-04-11")
    @NotNull(message = "미팅 날짜 선택은 필수입니다.")
    private LocalDate meetingDate;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Seoul")
    @NotNull(message = "모임 시작 시간 선택은 필수입니다.")
    private LocalDateTime meetingStartTime;

    @NotNull(message = "모임 종료 시간 선택은 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime meetingEndTime;

    @Schema(description = "미팅 예산", example = "10000")
    @NotNull(message = "예산 입력은 필수입니다.")
    @Min(value = 0, message = "0원 이상 입력해주세요.")
    @Max(value = 1000000, message = "최대 100만원까지만 입력 가능합니다.")
    private Integer budget;

    @Schema(description = "미팅 내용", example = "석촌호수 근처 카페에서 모각코 진행합니다! 관심있으신 분은 채팅 참여해주세요 :)")
    @NotNull(message = "모임 내용 입력은 필수입니다.")
    private String contents;

    @Schema(description = "총 인원", example = "8")
    @NotNull(message = "총 인원 선택은 필수입니다.")
    private Short totalCount;

    @Schema(description = "미팅 장소", example = "서울특별시 송파구 석촌동 172-24 석촌동 24-11 나인파크A 201호")
    @NotNull(message = "모임 장소 선택은 필수입니다.")
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
    @NotNull(message = "기술 스택 선택은 필수입니다.")
    private List<Long> skillIds;

    @Schema(description = "경력 ID 리스트", example = "[1, 2]")
    @NotNull(message = "경력 선택은 필수입니다.")
    private List<Long> careerIds;

    public Meeting toEntity(Member creator) {
        /* 시간 관련 validation */
        if (meetingStartTime != null && meetingEndTime != null && meetingStartTime.isAfter(meetingEndTime)) {
            throw new IllegalArgumentException("모임 시작 시간은 종료 시간보다 빨라야 합니다.");
        }
        SkillMapper skillMapper = new SkillMapper();
        List<SkillResponseDto> skillList = skillMapper.createSkillResponseList(skillIds);

        CareerMapper careerMapper = new CareerMapper();
        List<CareerResponseDto> careerList = careerMapper.createCareerResponseList(careerIds);

        ZonedDateTime seoulStartTime = meetingStartTime.atZone(ZoneId.of("Asia/Seoul")).plusHours(9);
//        ZonedDateTime seoulEndTime = meetingEndTime.atZone(ZoneId.of("Asia/Seoul")).plusHours(18);

        log.info("RequestDto에 담겨온 meetingStartTime " + meetingStartTime.toString());

        log.info("ZonedDateTime seoulStartTime.toString " + seoulStartTime.toString());
        log.info("ZonedDateTime seoulStartTime.toLocalDateTime " + seoulStartTime.toLocalDateTime());


        /* startTime, endTime 변환해보기 */

        LocalDateTime combineStartTime = meetingDate.atTime(meetingStartTime.getHour(), meetingStartTime.getMinute());
        LocalDateTime combineEndTime = meetingDate.atTime(meetingEndTime.getHour(), meetingEndTime.getMinute());

        log.info("combineStartTime: " + combineStartTime.toString());
        log.info("combineEndTime: " + combineEndTime.toString());

        return Meeting.builder()
                .meetingName(this.meetingName)
                .meetingDate(this.meetingDate)
                .meetingStartTime(combineStartTime.plusHours(9))
                .meetingEndTime(combineEndTime.plusHours(9))
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
                .skillList(skillList)
                .careerList(careerList)
                .build();
    }
}
