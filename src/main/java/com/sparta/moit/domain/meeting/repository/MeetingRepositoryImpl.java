package com.sparta.moit.domain.meeting.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.sparta.moit.domain.meeting.entity.QCareer.career;
import static com.sparta.moit.domain.meeting.entity.QMeeting.meeting;
import static com.sparta.moit.domain.meeting.entity.QMeetingCareer.meetingCareer;
import static com.sparta.moit.domain.meeting.entity.QMeetingSkill.meetingSkill;
import static com.sparta.moit.domain.meeting.entity.QSkill.skill;


@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /* 모임 조회 */
    @Override
    public Slice<Meeting> getMeetingSlice(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, Pageable pageable) {
        List<Meeting> meetingList = queryFactory
                .selectFrom(meeting)
                .distinct()
                .leftJoin(meetingSkill)
                .on(meeting.id.eq(meetingSkill.meeting.id))
                .leftJoin(meetingCareer)
                .on(meeting.id.eq(meetingCareer.meeting.id))
                .where(
                        skillEq(skillId),
                        careerEq(careerId)
                )
                .orderBy(
                        distanceExpression(locationLat, locationLng).asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(meetingList, pageable, hasNextPage(meetingList, pageable.getPageSize()));
    }

    /* 검색 */
    @Override
    public Slice<Meeting> findByKeyword(String keyword, Pageable pageable) {
        List<Meeting> meetingList = queryFactory
                .selectFrom(meeting)
                .where(
                        titleLike(keyword)
                                .or(addressLike(keyword))
                                .or(contentLike(keyword))
                )
                .orderBy(
                        meeting.meetingDate.asc(),
                        meeting.registeredCount.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return new SliceImpl<>(meetingList, pageable, hasNextPage(meetingList, pageable.getPageSize()));
    }

    private BooleanExpression titleLike(String keyword) {
        return keyword != null ? meeting.meetingName.contains(keyword) : null;
    }

    private BooleanExpression addressLike(String keyword) {
        return keyword != null ? meeting.locationAddress.contains(keyword) : null;
    }

    private BooleanExpression contentLike(String keyword) {
        return keyword != null ? meeting.contents.contains(keyword) : null;
    }

    /* Method */

    private boolean hasNextPage(List<Meeting> meetingList, int pageSize) {
        if (meetingList.size() > pageSize) {
            meetingList.remove(pageSize);
            return true;
        }
        return false;
    }

    private BooleanExpression careerEq(List<Long> careerId) {
        return careerId == null || careerId.isEmpty() ? null : career.Id.in(careerId);
    }

    private BooleanExpression skillEq(List<Long> skillId) {
        return skillId == null || skillId.isEmpty() ? null : meetingSkill.skill.Id.in(skillId);
    }

    private ComparableExpressionBase<Double> distanceExpression(Double locationLat, Double locationLng) {
        return Expressions.numberTemplate(Double.class,
                "ST_DISTANCE_SPHERE(point({0}, {1}), point(meeting.locationLng, meeting.locationLat))",
                locationLng, locationLat);
    }

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
