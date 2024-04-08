package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.RegionFirst;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegionFirstResponseDto {
    private Short regionFirstId;
    private String regionFirstName;
    @Builder
    public RegionFirstResponseDto(Short regionFirstId, String regionFirstName) {
        this.regionFirstId = regionFirstId;
        this.regionFirstName = regionFirstName;
    }

    public static RegionFirstResponseDto fromEntity(RegionFirst regionFirst){
        String regionName = regionFirst.getRegionFirstName().substring(0, 2);
        return RegionFirstResponseDto.builder()
                .regionFirstId(regionFirst.getRegionFirstId())
                .regionFirstName(regionName)
                .build();
    }
}
