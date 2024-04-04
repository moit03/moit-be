package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {

    Optional<Meeting> findByIdAndMember(Long id, Member member);
}
