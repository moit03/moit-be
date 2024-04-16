package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingSkillRepository extends JpaRepository<MeetingSkill, Long> {
    void deleteByMeeting(Meeting meeting);
}
