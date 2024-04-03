package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.SkillResponseDto;

import java.util.List;

public interface SkillService {
    List<SkillResponseDto> getSkillList();
}
