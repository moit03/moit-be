package com.sparta.moit.domain.meeting.controller.docs;

import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "미팅", description = "미팅 API")
public interface MeetingControllerDocs {

    @Operation(summary = "미팅 리스트 조회", description = "미팅 리스트 조회 API")
    ResponseEntity<List<GetMeetingResponseDto>> getMeetingList(
            @RequestParam List<Integer> careerTypes,
            @RequestParam List<Integer> skillTypes,
            @RequestParam String region1depthName,
            @RequestParam String region2depthName
    );

    @Operation(summary = "미팅 등록", description = "미팅 등록 API")
    ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "미팅 수정", description = "미팅 수정 API")
    ResponseEntity<?> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);
}
