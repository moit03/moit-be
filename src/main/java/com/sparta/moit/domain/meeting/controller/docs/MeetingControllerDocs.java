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

    @Operation(summary = "모임 등록 기능", description = "모임 등록 API")
    ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 수정 기능", description = "모임 수정 API")
    ResponseEntity<?> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 삭제 기능", description = "모임 삭제 API")
    ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 조회 기능", description = "모임 조회 API")
    ResponseEntity<?> getMeetingList(@RequestParam Double locationLat,
                                     @RequestParam Double locationLng,
                                     @RequestParam(required = false) List<Long> skillId,
                                     @RequestParam(required = false) List<Long> careerId,
                                     @RequestParam(defaultValue = "1") int page);

    @Operation(summary = "모임 조회 기능 (NativeQuery)", description = "모임 조회 API")
    public ResponseEntity<?> getMeetingListNativeQuery(@RequestParam Double locationLat,
                                                       @RequestParam Double locationLng,
                                                       @RequestParam(required = false) List<Long> skillId,
                                                       @RequestParam(required = false) List<Long> careerId,
                                                       @RequestParam(defaultValue = "1") int page);

    @Operation(summary = "모임 상세 조회 기능", description = "모임 상세 조회 API")
    @ApiResponse(responseCode = "200", description = "모임 상세 조회 완료",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMeetingDetailResponseDto.class)))
    ResponseEntity<?> getMeetingDetail(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 검색 기능", description = "모임 검색 API")
    ResponseEntity<?> getMeetingListBySearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page);

    @Operation(summary = "인기 모임", description = "인기 모임 API")
    public ResponseEntity<?> getPopularMeeting();

    @Operation(summary = "회원 모임 참가 기능", description = "모임 참가 API")
    ResponseEntity<?> enterMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "모임 탈퇴 기능", description = "모임 탈퇴 API")
    ResponseEntity<?> leaveMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);
}

