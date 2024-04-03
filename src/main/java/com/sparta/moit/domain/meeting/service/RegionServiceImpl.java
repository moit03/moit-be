package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.RegionFirstResponseDto;
import com.sparta.moit.domain.meeting.dto.RegionSecondResponseDto;
import com.sparta.moit.domain.meeting.entity.RegionFirst;
import com.sparta.moit.domain.meeting.entity.RegionSecond;
import com.sparta.moit.domain.meeting.repository.RegionFirstRepository;
import com.sparta.moit.domain.meeting.repository.RegionSecondRepository;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "Region Service Log")
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{
    private final RegionFirstRepository regionFirstRepository;
    private final RegionSecondRepository regionSecondRepository;

    @Override
    public List<RegionFirstResponseDto> getRegionFirst() {
        List<RegionFirst> regionFirstList = regionFirstRepository.findAll();
        return regionFirstList.stream().map(RegionFirstResponseDto::fromEntity).toList();
    }

    @Override
    public List<RegionSecondResponseDto> getRegionSecond(Short regionFirstId) {
        RegionFirst regionFirst = regionFirstRepository.findById(regionFirstId).orElseThrow(() ->
                new CustomException(ErrorCode.VALIDATION_ERROR)
        );
        List<RegionSecond> regionSecondList = regionSecondRepository.findAllByRegionFirst(regionFirst);
        return regionSecondList.stream().map(RegionSecondResponseDto::fromEntity).toList();
    }
}
