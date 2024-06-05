package com.sparta.moit.global.util;

import com.sparta.moit.domain.meeting.dto.CareerDto;
import com.sparta.moit.domain.meeting.dto.CareerResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CareerMapper {

    private static final Map<Long, String> CAREER_MAP = Map.ofEntries(
            Map.entry(1L, "신입"),
            Map.entry(2L, "주니어(1~3)"),
            Map.entry(3L, "미들(5~8)"),
            Map.entry(4L,"시니어(9~12)"),
            Map.entry(5L,"엑스퍼트(13이상)")
    );

    public static List<String> mapCareerIdsToNames(Long[] careerIds) {
        return Arrays.stream(careerIds)
                .map(CAREER_MAP::get)
                .collect(Collectors.toList());
    }
    public static List<CareerResponseDto> createCareerResponseList(List<Long> careerIds) {
        return careerIds.stream()
                .map(id -> CareerResponseDto.builder()
                        .careerId(id)
                        .careerName(CAREER_MAP.get(id))
                        .build())
                .collect(Collectors.toList());
    }
    public static List<CareerDto> createCareerResponseList(Long[] careerIds) {
        return Arrays.stream(careerIds)
                .map(id -> CareerDto.builder()
                        .careerId(id)
                        .careerName(CAREER_MAP.get(id))
                        .build())
                .collect(Collectors.toList());
    }
    
}
