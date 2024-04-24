package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.RegionFirst;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegionFirstResponseDto {
    private Long regionFirstId;
    private String regionFirstName;
    @Builder
    public RegionFirstResponseDto(Long regionFirstId, String regionFirstName) {
        this.regionFirstId = regionFirstId;
        this.regionFirstName = regionFirstName;
    }

    public static RegionFirstResponseDto fromEntity(RegionFirst regionFirst){
        return RegionFirstResponseDto.builder()
                .regionFirstId(regionFirst.getRegionFirstId())
                .regionFirstName(regionFirst.getRegionFirstName())
                .build();
    }
}
