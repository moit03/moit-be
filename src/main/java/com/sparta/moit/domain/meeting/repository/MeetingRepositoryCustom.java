package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface MeetingRepositoryCustom {
    /*List<Meeting> findAllByFilter(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);*/

    Slice<Meeting> getMeetingSlice(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, Pageable pageable);
}
