package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.SkillResponseDto;
import com.sparta.moit.domain.meeting.entity.Skill;
import com.sparta.moit.domain.meeting.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "Skill Service Log")
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService{
    private final SkillRepository skillRepository;
    @Override
    public List<SkillResponseDto> getSkillList() {
        List<Skill> skillList = skillRepository.findAll();
        return skillList.stream().map(SkillResponseDto::fromEntity).toList();
    }
}
