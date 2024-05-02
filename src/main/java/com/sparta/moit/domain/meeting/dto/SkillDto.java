package com.sparta.moit.domain.meeting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SkillDto {
    private final Long skillId;
    private final String skillName;

    @Builder
    public SkillDto(Long skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }
}