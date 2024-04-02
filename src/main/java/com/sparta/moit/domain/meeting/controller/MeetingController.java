package com.sparta.moit.domain.meeting.controller;

import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private MeetingService meetingService;

    public ResponseEntity<List<GetMeetingResponseDto>> getMeetingList(
            @RequestParam List<Integer> careerTypes,
            @RequestParam List<Integer> skillTypes,
            @RequestParam String region1depthName,
            @RequestParam String region2depthName
    ) {
        List<GetMeetingResponseDto> responseDto =  meetingService.getMeetingList(
                careerTypes,
                skillTypes,
                region1depthName,
                region2depthName
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }
}
