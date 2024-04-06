package com.sparta.moit.domain.meeting.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.moit.domain.meeting.entity.QCareer.career;
import static com.sparta.moit.domain.meeting.entity.QMeetingCareer.meetingCareer;
import static com.sparta.moit.domain.meeting.entity.QMeetingSkill.meetingSkill;
import static com.sparta.moit.domain.meeting.entity.QSkill.skill;

@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findCareerNameList(Long meetingId) {
        return queryFactory
                .select(career.careerName)
                .from(meetingCareer)
                .join(meetingCareer.career, career)
                .where(meetingCareer.meeting.id.eq(meetingId))
                .fetch();
    }

    @Override
    public List<String> findSkillNameList(Long meetingId) {
        return queryFactory
                .select(skill.skillName)
                .from(meetingSkill)
                .join(meetingSkill.skill, skill)
                .where(meetingSkill.meeting.id.eq(meetingId))
                .fetch();
    }





    /*@Override
    public List<Meeting> findAllByFilter(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName) {
        JPAQuery<Meeting> query = queryFactory.selectFrom(meeting);

//        // careerTypes가 비어있지 않은 경우 해당 조건을 쿼리에 포함
//        if (careerTypes != null && !careerTypes.isEmpty()) {
//            BooleanExpression careerCondition = meeting.careerType.id.in(careerTypes);
//            query.where(careerCondition);
//        }
//
//        // skillTypes, region1depthName, region2depthName 등 다른 필터 조건들도 여기에 추가할 수 있습니다.
//        // 예를 들어, region1depthName 조건 추가
//        if (region1depthName != null && !region1depthName.isEmpty()) {
//            query.where(meeting.region1depthName.eq(region1depthName));
//        }
//
//        // region2depthName 조건 추가
//        if (region2depthName != null && !region2depthName.isEmpty()) {
//            query.where(meeting.region2depthName.eq(region2depthName));
//        }
//
//        // skillTypes 조건 추가 (skillTypes 구현 방식에 따라 다를 수 있음)
//        if (skillTypes != null && !skillTypes.isEmpty()) {
//            BooleanExpression skillCondition = meeting.skillType.id.in(skillTypes);
//            query.where(skillCondition);
//        }

        return query.fetch();
    }*/



}
