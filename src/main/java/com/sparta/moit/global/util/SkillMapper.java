package com.sparta.moit.global.util;

import com.sparta.moit.domain.meeting.dto.SkillDto;
import com.sparta.moit.domain.meeting.dto.SkillResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkillMapper {

    private static final Map<Long, String> SKILL_MAP = Map.ofEntries(
            Map.entry(1L, "Java"),
            Map.entry(2L, "React"),
            Map.entry(3L, "Python"),
            Map.entry(4L,"JavaScript"),
            Map.entry(5L,"Spring"),
            Map.entry(6L,"NodeJs"),
            Map.entry(7L,"C#"),
            Map.entry(8L,"Vue.js"),
            Map.entry(9L,"Ruby"),
            Map.entry(10L,"Swift"),
            Map.entry(11L,"HTML"),
            Map.entry(12L,"CSS"),
            Map.entry(13L,"SQL"),
            Map.entry(14L,"Angular"),
            Map.entry(15L,"PHP"),
            Map.entry(16L,"Kotlin"),
            Map.entry(17L,"Docker"),
            Map.entry(18L,"Kubernetes"),
            Map.entry(19L,"AWS"),
            Map.entry(20L,"GCP"),
            Map.entry(21L,"Git"),
            Map.entry(22L,"Jenkins"),
            Map.entry(23L,"Agile Methodology"),
            Map.entry(24L,"React Native"),
            Map.entry(25L,"Flutter"),
            Map.entry(26L,"TypeScript"),
            Map.entry(27L,"AngularJS"),
            Map.entry(28L,"Express.js"),
            Map.entry(29L,"Flask"),
            Map.entry(30L,"Django"),
            Map.entry(31L,"Laravel"),
            Map.entry(32L,"Ruby"),
            Map.entry(33L,"ASP.NET"),
            Map.entry(34L,"Unity"),
            Map.entry(35L,"Unreal Engine"),
            Map.entry(36L,"PostgreSQL"),
            Map.entry(37L,"MySQL"),
            Map.entry(38L,"MongoDB"),
            Map.entry(39L,"Redis"),
            Map.entry(40L,"Elasticsearch"),
            Map.entry(41L,"GraphQL"),
            Map.entry(42L,"Microservices"),
            Map.entry(43L,"Serverless Architecture"),
            Map.entry(44L,"Blockchain"),
            Map.entry(45L,"Machine Learning"),
            Map.entry(46L,"Deep Learning"),
            Map.entry(47L,"NLP"),
            Map.entry(48L,"Data Engineering"),
            Map.entry(49L,"DevOps"),
            Map.entry(50L,"CI/CD"),
            Map.entry(51L,"IaC"),
            Map.entry(52L,"C++")
    );

    public List<String> mapSkillIdsToNames(Long[]  skillIds) {
        return Arrays.stream(skillIds)
                .map(SKILL_MAP::get)
                .collect(Collectors.toList());
    }
    public List<SkillResponseDto> createSkillResponseList(List<Long> skillIds) {
        return skillIds.stream()
                .map(id -> SkillResponseDto.builder()
                        .skillId(id)
                        .skillName(SKILL_MAP.get(id))
                        .build())
                .collect(Collectors.toList());
    }
    public List<SkillDto> createSkillResponseList(Long[] skillIds) {
        return Arrays.stream(skillIds)
                .map(id -> SkillDto.builder()
                        .skillId(id)
                        .skillName(SKILL_MAP.get(id))
                        .build())
                .collect(Collectors.toList());
    }
    
}
