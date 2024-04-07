package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {

    Optional<Meeting> findByIdAndCreator(Long meetingId, Member member);

//    @Query(value = "SELECT * FROM meeting ORDER BY "
//            + "(6371 * acos(cos(radians(:locationLat)) * cos(radians(location_lat)) * cos(radians(location_lng) - radians(:locationLng)) + sin(radians(:locationLat)) * sin(radians(location_lat)))) ASC "
//            + "LIMIT :limit OFFSET :page",
//            nativeQuery = true)
//    List<Meeting> getNearestMeetings(Double locationLat, Double locationLng, int limit, int page);

    @Query(value = "SELECT * FROM meeting " +
            "ORDER BY ST_DISTANCE_SPHERE(point(:locationLng, :locationLat), point(location_lng, location_lat)) " +
            "LIMIT :limit OFFSET :page",
            nativeQuery = true)
    List<Meeting> getNearestMeetings(Double locationLat, Double locationLng, int limit, int page);

    @Query(value = "SELECT m.* FROM meeting m "
            + "JOIN meeting_career mc ON m.id = mc.meeting_id "
            + "WHERE mc.id IN (:careerId) "
            + "ORDER BY (6371 * acos(cos(radians(:locationLat)) * cos(radians(m.location_lat)) * cos(radians(m.location_lng) - radians(:locationLng)) + sin(radians(:locationLat)) * sin(radians(m.location_lat)))) ASC "
            + "LIMIT :limit OFFSET :page",
            nativeQuery = true)
    List<Meeting> getMeetingsWithCareer(Double locationLat, Double locationLng, List<Long> careerId, int limit, int page);

    @Query(value = "SELECT m.* FROM meeting m "
            + "JOIN meeting_skill ms ON m.id = ms.meeting_id "
            + "WHERE ms.skill_id IN (:skillId) "
            + "ORDER BY (6371 * acos(cos(radians(:locationLat)) * cos(radians(m.location_lat)) * cos(radians(m.location_lng) - radians(:locationLng)) + sin(radians(:locationLat)) * sin(radians(m.location_lat)))) ASC "
            + "LIMIT :limit OFFSET :page",
            nativeQuery = true)
    List<Meeting> getMeetingsWithSkill(Double locationLat, Double locationLng, List<Long> skillId, int limit, int page);

    @Query(value = "SELECT m.* FROM meeting m "
            + "JOIN meeting_career mc ON m.id = mc.meeting_id "
            + "JOIN meeting_skill ms ON m.id = ms.meeting_id "
            + "WHERE ms.skill_id IN (:skillId) AND mc.career_id IN (:careerId) "
            + "ORDER BY (6371 * acos(cos(radians(:locationLat)) * cos(radians(m.location_lat)) * cos(radians(m.location_lng) - radians(:locationLng)) + sin(radians(:locationLat)) * sin(radians(m.location_lat)))) ASC "
            + "LIMIT :limit OFFSET :page",
            nativeQuery = true)
    List<Meeting> getMeetingsWithSkillAndCareer(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, int limit, int page);
}
