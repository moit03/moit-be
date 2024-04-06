package com.sparta.moit.domain.meeting.repository;

import java.util.List;

public interface MeetingRepositoryCustom {
    /*List<Meeting> findAllByFilter(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName);*/
    List<String> findCareerNameList(Long meetingId);

    List<String> findSkillNameList(Long meetingId);
}
