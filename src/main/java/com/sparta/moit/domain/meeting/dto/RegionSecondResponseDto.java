package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.RegionFirst;
import com.sparta.moit.domain.meeting.entity.RegionSecond;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegionSecondResponseDto {
    private Short regionSecondId;
    private String regionSecondName;

    @Builder
    public RegionSecondResponseDto(Short regionSecondId, String regionSecondName) {
        this.regionSecondId = regionSecondId;
        this.regionSecondName = regionSecondName;
    }

    public static RegionSecondResponseDto fromEntity(RegionSecond regionSecond){
        return RegionSecondResponseDto.builder()
                .regionSecondId(regionSecond.getRegionSecondId())
                .regionSecondName(regionSecond.getRegionSecondName())
                .build();
    }
}
