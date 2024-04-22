package com.sparta.moit.domain.bookmark.controller.docs;

import com.sparta.moit.domain.bookmark.dto.BookMarkDto;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

public interface BookMarkControllerDocs {
    @Operation(summary = "북마크 추가", description = "북마크 추가 API")
    ResponseEntity<ResponseDto<BookMarkDto>> addMeetingBookmark(@RequestParam Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);
    @Operation(summary = "북마크 해제", description = "북마크 해제 API")
    ResponseEntity<ResponseDto<BookMarkDto>> removeMeetingBookmark(@RequestParam Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);
    @Operation(summary = "북마크 확인", description = "북마크 확인 API")
    ResponseEntity<Boolean> isBookmarked(@RequestParam Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);
}
