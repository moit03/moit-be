package com.sparta.moit.domain.meeting.controller.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingDetailResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "모임", description = "모임 API")
public interface MeetingControllerDocs {

    @Operation(summary = "모임 등록", description = "모임 등록 API")
    ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 수정", description = "모임 수정 API")
    ResponseEntity<?> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 조회", description = "모임 조회 API")
    ResponseEntity<?> getMeetingListByLatLng(@RequestParam Double locationLat,
                                             @RequestParam Double locationLng,
                                             @RequestParam(required = false) List<Short> skillId,
                                             @RequestParam(required = false) List<Short> careerId,
                                             @RequestParam(defaultValue = "1") int page);
    @Operation(summary = "모임 상세 조회", description = "모임 상세 조회 API")
    @ApiResponse(responseCode = "200", description = "모임 상세 조회 완료",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMeetingDetailResponseDto.class)))
    public ResponseEntity<?> getMeetingDetail(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "회원 모임 가입", description = "모임 가입 API")
    ResponseEntity<?> enterMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "주소별 모임 조회", description = "주소별 모임 조회 API")
    public ResponseEntity<?> getMeetingListByAddress(@RequestParam String firstRegion, @RequestParam String secondRegion, @RequestParam(defaultValue = "1") int page) throws JsonProcessingException;

    }
