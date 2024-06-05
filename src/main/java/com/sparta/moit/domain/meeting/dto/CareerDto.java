package com.sparta.moit.domain.meeting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CareerDto {
    private final Long careerId;
    private final String careerName;

    @Builder
    public CareerDto(Long careerId, String careerName) {
        this.careerId = careerId;
        this.careerName = careerName;
    }
}