package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.RegionFirst;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class RegionIntegratedResponseDto {
    private Short regionFirstId;
    private String regionFirstName;
    private List<RegionSecondResponseDto> secondResponseDtos;

    @Builder
    public RegionIntegratedResponseDto(Short regionFirstId, String regionFirstName, List<RegionSecondResponseDto> secondResponseDtos) {
        this.regionFirstId = regionFirstId;
        this.regionFirstName = regionFirstName;
        this.secondResponseDtos = secondResponseDtos;
    }

    public static RegionIntegratedResponseDto fromEntity(RegionFirst regionFirst){
        List<RegionSecondResponseDto> seconds = regionFirst.getRegionSeconds()
                .stream()
                .map(RegionSecondResponseDto::fromEntity).toList();
        return RegionIntegratedResponseDto.builder()
                .regionFirstId(regionFirst.getRegionFirstId())
                .regionFirstName(regionFirst.getRegionFirstName())
                .secondResponseDtos(seconds)
                .build();
    }
}
