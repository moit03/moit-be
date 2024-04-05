package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {
}
