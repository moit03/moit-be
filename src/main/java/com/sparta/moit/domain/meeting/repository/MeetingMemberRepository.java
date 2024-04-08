package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingMember;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {
    Optional<MeetingMember> findByMemberAndMeeting(Member member0, Meeting meeting);

    int countByMemberId(Long memberId);

    Boolean existsByMemberAndMeeting(Member member, Meeting meeting);

}
