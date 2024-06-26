package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByIdIn(List<Long> ids);
}
