package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.RegionFirstResponseDto;
import com.sparta.moit.domain.meeting.dto.RegionSecondResponseDto;
import com.sparta.moit.domain.meeting.entity.RegionFirst;
import com.sparta.moit.domain.meeting.entity.RegionSecond;
import com.sparta.moit.domain.meeting.repository.RegionFirstRepository;
import com.sparta.moit.domain.meeting.repository.RegionSecondRepository;
import com.sparta.moit.global.common.dto.AddressResponseDto;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import com.sparta.moit.global.util.AddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "Region Service Log")
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionFirstRepository regionFirstRepository;
    private final RegionSecondRepository regionSecondRepository;
    private final AddressUtil addressUtil;

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

    /* DB 위도-경도 저장용 메서드 (사용 금지) */
    @Override
    @Transactional
    public AddressResponseDto updateAddress() throws JsonProcessingException {
        List<RegionSecond> regionSecondList = regionSecondRepository.findAllWithRegionFirstName();
        for (RegionSecond regionSecond : regionSecondList) {
            String first = regionSecond.getRegionFirst().getRegionFirstName();
            String second = regionSecond.getRegionSecondName();
            try {
                AddressResponseDto addressResponseDto = addressUtil.searchAddress(first, second);
                regionSecond.update(Double.parseDouble(addressResponseDto.getLat()), Double.parseDouble(addressResponseDto.getLng()));
            } catch (Exception e) {
                log.error("주소 중 에러 발생", e);
                regionSecond.update(0.0, 0.0);
            }

        }
        return null;
    }
}
