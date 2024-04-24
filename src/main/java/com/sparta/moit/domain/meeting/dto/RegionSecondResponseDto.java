package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.RegionSecond;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegionSecondResponseDto {
    private Long regionSecondId;
    private String regionSecondName;
    private Double regionLat;
    private Double regionLng;


    @Builder
    public RegionSecondResponseDto(Long regionSecondId, String regionSecondName, Double regionLat, Double regionLng) {
        this.regionSecondId = regionSecondId;
        this.regionSecondName = regionSecondName;
        this.regionLat = regionLat;
        this.regionLng = regionLng;
    }

    public static RegionSecondResponseDto fromEntity(RegionSecond regionSecond) {
        return RegionSecondResponseDto.builder()
                .regionSecondId(regionSecond.getRegionSecondId())
                .regionSecondName(regionSecond.getRegionSecondName())
                .regionLat(regionSecond.getRegionLat())
                .regionLng(regionSecond.getRegionLng())
                .build();
    }
}
