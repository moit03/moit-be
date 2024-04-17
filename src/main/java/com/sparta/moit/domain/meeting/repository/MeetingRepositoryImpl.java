package com.sparta.moit.domain.meeting.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moit.domain.meeting.dto.GetMyPageDto;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import com.sparta.moit.domain.meeting.entity.QMeeting;
import com.sparta.moit.domain.meeting.entity.QMeetingMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.moit.domain.meeting.entity.QCareer.career;
import static com.sparta.moit.domain.meeting.entity.QMeeting.meeting;
import static com.sparta.moit.domain.meeting.entity.QMeetingCareer.meetingCareer;
import static com.sparta.moit.domain.meeting.entity.QMeetingMember.meetingMember;
import static com.sparta.moit.domain.meeting.entity.QMeetingSkill.meetingSkill;
import static com.sparta.moit.domain.meeting.entity.QSkill.skill;
import static org.hibernate.query.results.Builders.fetch;


@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetMyPageDto> getMyPage(Long memberId, MeetingStatusEnum status) {
        return queryFactory
                .select(Projections.constructor(
                        GetMyPageDto.class,
                        meeting.meetingStartTime,
                        meeting.meetingEndTime,
                        meetingMember.member.id))
                .from(meetingMember)
                .leftJoin(meetingMember.meeting, meeting)
                .where(meetingMember.member.id.eq(memberId)
                        .and(meeting.status.ne(MeetingStatusEnum.DELETE))) // status != DELETE 조건 추가
                .fetch();
    }

    @Override
    public List<Meeting> findMeetingsByMember(Long memberId) {
        List<Meeting> response = queryFactory
                .selectFrom(meeting)
                .distinct()
                .leftJoin(meeting.meetingMembers, meetingMember)
                .where(meetingMember.member.id.eq(memberId))
                .fetch();
        return response;
    }

    /* 모임 조회 */ /*모임 조회 시간 지연 이슈 */
    @Override
    public Slice<Meeting> getMeetingSlice(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, Pageable pageable) {
        List<Meeting> meetingList = queryFactory
                .selectFrom(meeting)
                .distinct()
                .leftJoin(meeting.skills, meetingSkill)
                .leftJoin(meeting.careers, meetingCareer)
                .where(
                        skillEq(skillId),
                        careerEq(careerId),
                        isOpenOrFull()
                )
                .orderBy(
                        distanceExpression2(locationLat, locationLng).asc()
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
                                .or(contentLike(keyword)),
                        isOpenOrFull()
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

    @Override
    public List<Meeting> findAllIncompleteMeetingsForHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return queryFactory.selectFrom(meeting)
                .where(meeting.status.notIn(MeetingStatusEnum.COMPLETE, MeetingStatusEnum.DELETE))
                /* now() - 1hr <= meetingEndTime < now() */
                .where(meeting.meetingStartTime.between(oneHourAgo, LocalDateTime.now()))
                .fetch();
    }

    /* Method */

    private BooleanExpression isOpenOrFull() {
        return meeting.status.in(MeetingStatusEnum.OPEN, MeetingStatusEnum.FULL);
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

    /* MySQL 내장 함수 */
    private ComparableExpressionBase<Double> distanceExpression(Double locationLat, Double locationLng) {
        return Expressions.numberTemplate(Double.class,
                "ST_DISTANCE_SPHERE(point({0}, {1}), point(meeting.locationLng, meeting.locationLat))",
                locationLng, locationLat);
    }

    /* 직접 작성한 OrderBy (하버사인 공식) */
    private ComparableExpressionBase<Double> distanceExpression2(Double locationLat, Double locationLng) {
        return Expressions.numberTemplate(Double.class,
                "(6371 * acos(cos(radians(meeting.locationLat)) * cos(radians({0})) * cos(radians({1}) - radians(meeting.locationLng)) + sin(radians(meeting.locationLat)) * sin(radians({0}))))",
                locationLat, locationLng);
    }

}
