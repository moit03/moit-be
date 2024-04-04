package com.sparta.moit.domain.meeting.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "기술스택 조회", description = "기술스택 API")
public interface SkillControllerDocs {

    @Operation(summary = "기술스택 조회", description = "기술스택 조회 API")
    ResponseEntity<?> getSkillList();
}
