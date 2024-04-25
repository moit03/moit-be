package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.RegionFirstResponseDto;
import com.sparta.moit.domain.meeting.dto.RegionIntegratedResponseDto;
import com.sparta.moit.domain.meeting.dto.RegionSecondResponseDto;
import com.sparta.moit.global.common.dto.AddressResponseDto;

import java.util.List;

public interface RegionService {
    List<RegionFirstResponseDto> getRegionFirst();

    List<RegionSecondResponseDto> getRegionSecond(Short regionFirstId);

    AddressResponseDto updateAddress() throws JsonProcessingException;

    List<RegionIntegratedResponseDto> getRegion();
}
