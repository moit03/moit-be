package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.RegionFirstResponseDto;
import com.sparta.moit.domain.meeting.dto.RegionSecondResponseDto;

import java.util.List;

public interface RegionService {
    List<RegionFirstResponseDto> getRegionFirst();

    List<RegionSecondResponseDto> getRegionSecond(Short regionFirstId);
}
