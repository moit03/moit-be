package com.sparta.moit.domain.meeting.controller;

import com.sparta.moit.domain.meeting.controller.docs.SkillControllerDocs;
import com.sparta.moit.domain.meeting.dto.SkillResponseDto;
import com.sparta.moit.domain.meeting.service.SkillService;
import com.sparta.moit.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skill")
public class SkillController implements SkillControllerDocs {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<?> getSkillList(){
        List<SkillResponseDto> responseDto = skillService.getSkillList();
        return ResponseEntity.ok().body(ResponseDto.success("기술스택 리스트 조회", responseDto));
    }
}
