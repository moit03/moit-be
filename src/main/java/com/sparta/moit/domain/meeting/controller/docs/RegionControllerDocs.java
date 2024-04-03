package com.sparta.moit.domain.meeting.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "시도군구 조회", description = "시도군구 API")
public interface RegionControllerDocs {

    @Operation(summary = "시-도 조회", description = "시도 리스트 조회 API")
    ResponseEntity<?> getRegionFirst();
    @Operation(summary = "시군구 조회", description = "시군구 리스트 조회 API")
    ResponseEntity<?> getRegionFirst(@RequestParam Short regionFirstId);
}
