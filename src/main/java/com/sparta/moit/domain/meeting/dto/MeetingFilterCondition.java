package com.sparta.moit.domain.meeting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class MeetingFilterCondition {
    @Schema(description = "강남 신사역 위도", example = "37.51607117494551")
    private Double locationLat;
    @Schema(description = "강남 신사역 경도", example = "127.01968181304784")
    private Double locationLng;
    @Schema(description = "기술 스택", example = "1, 2")
    private List<Long> skillId;
    @Schema(description = "경력", example = "1, 2")
    private List<Long> careerId;
}
