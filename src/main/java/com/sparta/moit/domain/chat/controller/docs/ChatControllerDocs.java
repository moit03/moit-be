package com.sparta.moit.domain.chat.controller.docs;

import com.sparta.moit.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "채팅", description = "채팅 API")
public interface ChatControllerDocs {

    @Operation(summary = "채팅 목록 불러오기 기능", description = "채팅 불러오기 API")
    ResponseEntity<?> getChatList(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails);

}
