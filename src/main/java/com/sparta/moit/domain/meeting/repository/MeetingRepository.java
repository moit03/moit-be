package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import com.sparta.moit.domain.member.entity.Member;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {

    Optional<Meeting> findByIdAndCreator(Long meetingId, Member member);

    int countByCreatorAndStatusNot(Member creator, MeetingStatusEnum status);

    @Query("SELECT m FROM meeting m WHERE m.creator.id = :memberId AND m.status != :status")
    List<Meeting> findMeetingsByCreatorIdAndStatusNot(Long memberId, MeetingStatusEnum status);
    @Query("SELECT m FROM meeting m WHERE m.creator.id = :memberId AND m.status = :status")
    List<Meeting> findMeetingsByCreatorIdAndStatus(Long memberId, MeetingStatusEnum status);

    @Query(value = "SELECT m.*, " +
            "ST_Distance( CAST (:point AS geography), CAST(m.location_position AS geography)) AS dist " +
            "FROM meeting m " +
            "WHERE " +
            "   ST_Dwithin( CAST (:point AS geography), CAST(m.location_position AS geography), 5000) " +
            "   AND (:skillIdsStr IS NULL OR EXISTS (" +
            "         SELECT 1 FROM jsonb_array_elements(m.skill_list) AS skill_json " +
            "         WHERE CAST(skill_json->>'skillId' AS TEXT) = ANY(string_to_array(:skillIdsStr, ',')) " +
            "       )) " +
            "   AND (:careerIdsStr IS NULL OR EXISTS (" +
            "         SELECT 1 FROM jsonb_array_elements(m.career_list) AS career_json " +
            "         WHERE CAST(career_json->>'careerId' AS TEXT) = ANY(string_to_array(:careerIdsStr, ',')) " +
            "       )) " +
            "   AND m.status <> 'DELETE' " +
            "   AND m.status <> 'COMPLETE' "+
            " ORDER BY dist asc " +
            "LIMIT :pageSize " +
            "OFFSET :offset", nativeQuery = true)
    List<Meeting> findMeetingST_Dwithin(@Param("point") Point point,
                                        @Param("skillIdsStr") String skillIdsStr,
                                        @Param("careerIdsStr") String careerIdsStr,
                                        @Param("pageSize") int pageSize,
                                        @Param("offset") int offset);


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
