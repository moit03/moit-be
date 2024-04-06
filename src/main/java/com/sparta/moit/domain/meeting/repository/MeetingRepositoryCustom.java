package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.dto.MeetingFilterCondition;
import com.sparta.moit.domain.meeting.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MeetingRepositoryCustom {
    /*List<Meeting> findAllByFilter(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);*/

    Page<Meeting> getMeetingsQueryDsl(Double locationLat, Double locationLng, Pageable pageable);

    Page<Meeting> getMeetingsWithSkillAndCareer(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, Pageable pageable);

    List<Meeting> getMeetingTest(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, int page);
}
