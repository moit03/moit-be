package com.sparta.moit.domain.meeting.controller.docs;

import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "미팅", description = "미팅 API")
public interface MeetingControllerDocs {
    @Operation(summary = "미팅 등록", description = "미팅 등록 API")
    ResponseEntity<?> createMeeting(CreateMeetingRequestDto requestDto);
}
