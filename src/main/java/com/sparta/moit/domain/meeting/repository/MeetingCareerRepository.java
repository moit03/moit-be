package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingCareer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingCareerRepository extends JpaRepository<MeetingCareer, Long> {
    void deleteByMeeting(Meeting meeting);
}
