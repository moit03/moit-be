package com.sparta.moit.domain.meeting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.controller.docs.MeetingControllerDocs;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingDetailResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.meeting.service.MeetingService;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j(topic = "미팅 로그")
@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController implements MeetingControllerDocs {
    private final MeetingService meetingService;

    /*public ResponseEntity<List<GetMeetingResponseDto>> getMeetingList(
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
    }*/

    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long meetingId = meetingService.createMeeting(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("모임 등록", meetingId));
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<?> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long updatedMeetingId = meetingService.updateMeeting(requestDto, userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 수정", updatedMeetingId));
    }

    @GetMapping
    public ResponseEntity<?> getMeetingListByLatLng(@RequestParam Double locationLat,
                                                    @RequestParam Double locationLng,
                                                    @RequestParam(required = false) List<Short> skillId,
                                                    @RequestParam(required = false) List<Short> careerId,
                                                    @RequestParam(defaultValue = "1") int page){
        List<GetMeetingResponseDto> responseDtoList =
                meetingService.getFilteredMeetingList(page, locationLat, locationLng, skillId, careerId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    @GetMapping("/meetings/{meetingId}")
    public ResponseEntity<?> getMeetingDetail(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetMeetingDetailResponseDto responseDto = meetingService.getMeetingDetail(meetingId, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDto));
    }

    @PostMapping("my-meetings/{meetingId}")
    public ResponseEntity<?> enterMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long enterMeetingId = meetingService.enterMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 참가", enterMeetingId));
    }

    @GetMapping("/address")
    public ResponseEntity<?> getMeetingListByAddress(@RequestParam String firstRegion, @RequestParam String secondRegion, @RequestParam(defaultValue = "1") int page) throws JsonProcessingException {
        List<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListByAddress(firstRegion, secondRegion, page);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }
}
