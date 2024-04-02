package com.sparta.moit.domain.meeting.controller;

import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.CreateMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.service.MeetingService;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "미팅", description = "미팅 API")
@Slf4j(topic = "미팅 로그")
@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    public ResponseEntity<List<GetMeetingResponseDto>> getMeetingList(
            @RequestParam List<Integer> careerTypes,
            @RequestParam List<Integer> skillTypes,
            @RequestParam String region1depthName,
            @RequestParam String region2depthName
    ) {
        List<GetMeetingResponseDto> responseDto = meetingService.getMeetingList(
                careerTypes,
                skillTypes,
                region1depthName,
                region2depthName
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @Operation(summary = "미팅 등록", description = "미팅 등록 API")
    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CreateMeetingResponseDto responseDto = meetingService.createMeeting(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("모임 등록", responseDto));
    }
}
