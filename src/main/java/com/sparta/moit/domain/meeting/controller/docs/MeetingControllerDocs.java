package com.sparta.moit.domain.meeting.controller.docs;

import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
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
}
