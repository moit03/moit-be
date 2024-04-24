package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.Career;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CareerResponseDto {
    private final Long careerId;
    private final String careerName;

    @Builder
    public CareerResponseDto(Long careerId, String careerName) {
        this.careerId = careerId;
        this.careerName = careerName;
    }

    public static CareerResponseDto fromEntity(Career career) {
        return CareerResponseDto.builder()
                .careerId(career.getId())
                .careerName(career.getCareerName())
                .build();
    }
}
