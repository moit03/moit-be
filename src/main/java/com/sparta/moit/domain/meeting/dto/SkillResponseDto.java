package com.sparta.moit.domain.meeting.dto;

import com.sparta.moit.domain.meeting.entity.Skill;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SkillResponseDto {
    private final Long skillId;
    private final String skillName;

    @Builder
    public SkillResponseDto(Long skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }

    public static SkillResponseDto fromEntity(Skill skill) {
        return SkillResponseDto.builder()
                .skillId(skill.getId())
                .skillName(skill.getSkillName())
                .build();
    }
}
