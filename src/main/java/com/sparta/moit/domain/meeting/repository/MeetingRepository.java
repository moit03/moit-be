package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {

    Optional<Meeting> findByIdAndMember(Long id, Member member);

    @Query(value = "SELECT * FROM meeting ORDER BY "
            + "(6371 * acos(cos(radians(:locationLat)) * cos(radians(location_lat)) * cos(radians(location_lng) - radians(:locationLng)) + sin(radians(:locationLat)) * sin(radians(location_lat)))) ASC "
            + "LIMIT :limit OFFSET :page",
            nativeQuery = true)
    List<Meeting> findNearestMeetings(Double locationLat, Double locationLng, int limit, int page);

}
