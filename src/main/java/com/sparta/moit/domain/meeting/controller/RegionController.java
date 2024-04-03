package com.sparta.moit.domain.meeting.controller;

import com.sparta.moit.domain.meeting.controller.docs.RegionControllerDocs;
import com.sparta.moit.domain.meeting.dto.RegionFirstResponseDto;
import com.sparta.moit.domain.meeting.dto.RegionSecondResponseDto;
import com.sparta.moit.domain.meeting.service.RegionService;
import com.sparta.moit.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/region")
public class RegionController implements RegionControllerDocs {
    private final RegionService regionService;

    @GetMapping("/first")
    public ResponseEntity<?> getRegionFirst(){
        List<RegionFirstResponseDto> responseDto = regionService.getRegionFirst();
        return ResponseEntity.ok().body(ResponseDto.success("시-도 정보 조회", responseDto));
    }

    @GetMapping("/second")
    public ResponseEntity<?> getRegionFirst(@RequestParam Short regionFirstId){
        List<RegionSecondResponseDto> responseDto = regionService.getRegionSecond(regionFirstId);
        return ResponseEntity.ok().body(ResponseDto.success("시-도 정보 조회", responseDto));
    }
}
