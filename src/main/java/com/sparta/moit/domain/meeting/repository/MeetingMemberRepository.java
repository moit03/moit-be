package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingMember;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {
    Boolean existsByMemberAndMeeting(Member member, Meeting meeting);
}
