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
import org.springframework.data.domain.Slice;
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

    /*모임 등록*/
    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long meetingId = meetingService.createMeeting(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("모임 등록", meetingId)); /*메세지 추후 리펙토링 예정*/
    }

    /*모임 수정*/
    @PutMapping("/{meetingId}")
    public ResponseEntity<?> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long updatedMeetingId = meetingService.updateMeeting(requestDto, userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 수정", updatedMeetingId)); /*메세지 추후 리펙토링 예정*/
    }

    /*모임 조회*/
    @GetMapping
    public ResponseEntity<?> getMeetingList(@RequestParam Double locationLat,
                                            @RequestParam Double locationLng,
                                            @RequestParam(required = false) List<Long> skillId,
                                            @RequestParam(required = false) List<Long> careerId,
                                            @RequestParam(defaultValue = "1") int page) {
        Slice<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingList(page, locationLat, locationLng, skillId, careerId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    /*모임 상세 조회*/
    @GetMapping("/meetings/{meetingId}")
    public ResponseEntity<?> getMeetingDetail(@PathVariable Long meetingId) {
        GetMeetingDetailResponseDto responseDto = meetingService.getMeetingDetail(meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDto));
    }

    /*모임 참가*/
    @PostMapping("my-meetings/{meetingId}")
    public ResponseEntity<?> enterMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long enterMeetingId = meetingService.enterMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 참가", enterMeetingId)); /*메세지 추후 리펙토링 예정*/
    }

    /*주소별 모임 조회*/
    @GetMapping("/address")
    public ResponseEntity<?> getMeetingListByAddress(@RequestParam String firstRegion, @RequestParam String secondRegion, @RequestParam(defaultValue = "1") int page) throws JsonProcessingException {
        List<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListByAddress(firstRegion, secondRegion, page);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    /*모임 삭제*/
    @DeleteMapping("/{meetingId}")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.deleteMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 삭제 완료", null));
    }

    /* 모임 검색 */
    @GetMapping("/search")
    public ResponseEntity<?> getMeetingListBySearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) {
        Slice<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListBySearch(keyword, page);
        return ResponseEntity.ok().body(ResponseDto.success("검색 완료", responseDtoList));
    }
}
