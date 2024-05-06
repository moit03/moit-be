package com.sparta.moit.domain.meeting.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moit.domain.meeting.dto.GetMyPageDto;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import com.sparta.moit.domain.meeting.entity.QMeeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.moit.domain.bookmark.entity.QBookMark.bookMark;
import static com.sparta.moit.domain.meeting.entity.QCareer.career;
import static com.sparta.moit.domain.meeting.entity.QMeeting.meeting;
import static com.sparta.moit.domain.meeting.entity.QMeetingMember.meetingMember;
import static com.sparta.moit.domain.meeting.entity.QMeetingSkill.meetingSkill;

@Slf4j(topic = "스케줄러")
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
                        .and(meeting.status.eq(MeetingStatusEnum.COMPLETE))) /*완료인 경우만 조회*/
                .fetch();
    }

    @Override
    public List<Meeting> findMeetingsByMember(Long memberId) {
        List<Meeting> response = queryFactory
                .selectFrom(meeting)
                .distinct()
                .leftJoin(meeting.meetingMembers, meetingMember)
                .where(
                        meetingMember.member.id.eq(memberId),
                        isOpenOrFull()
                )
                .orderBy(meeting.meetingDate.asc())
                .fetch();
        return response;
    }

    /* 모임 조회 */ /*모임 조회 시간 지연 이슈 */
    @Override
    public Slice<Meeting> getMeetingSlice(Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId, Pageable pageable) {
        List<Meeting> meetingList = queryFactory
                .selectFrom(meeting)
                .distinct()
//                .leftJoin(meeting.skills, meetingSkill)
//                .leftJoin(meeting.careers, meetingCareer)
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
        QMeeting subMeeting = new QMeeting("subMeeting");

        List<Meeting> meetings = queryFactory.selectFrom(meeting)
                .where(meeting.id.in(
                        JPAExpressions.selectDistinct(subMeeting.id)
                                .from(subMeeting)
                                .where(
                                        titleLike(keyword)
                                                .or(addressLike(keyword))
                                                .or(contentLike(keyword)),
                                        isOpenOrFull()
                                )
                ))
                .orderBy(
                        meeting.meetingDate.asc(),
                        meeting.registeredCount.desc()
                )
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .fetch();
        return new SliceImpl<>(meetings, pageable, hasNextPage(meetings, pageable.getPageSize()));
    }

    @Override
    public List<Meeting> findAllIncompleteMeetingsForHour() {

/*
        2024-05-06 17`:40:00.009 [MessageBroker-1] INFO  스케줄러 - oneHourAgo.toString(): 2024-05-07T01:40:00.008886810
        2024-05-06 17:40:00.021 [MessageBroker-1] INFO  스케줄러 - now.toString(): 2024-05-07T02:40:00.021213330
        2024-05-06 17:40:00.025 [MessageBroker-1] INFO  스케줄러 - oneHourAgoTime: 01:40:00.008886810
        2024-05-06 17`:40:00.025 [MessageBroker-1] INFO  스케줄러 - nowTime: 02:40:00.021213330
* */

        LocalDateTime oneHourAgo = LocalDateTime.now().plusHours(8);
        log.info("oneHourAgo.toString(): " + oneHourAgo);
        LocalDateTime now = LocalDateTime.now().plusHours(9);
        log.info("now.toString(): " + now);
        LocalTime oneHourAgoTime = oneHourAgo.toLocalTime();
        log.info("oneHourAgoTime: " + oneHourAgoTime);
        LocalTime nowTime = now.toLocalTime();
        log.info("nowTime: " + nowTime);

        return queryFactory.selectFrom(meeting)
                .where(isOpenOrFull())
                .where(meeting.meetingDate.eq(now.toLocalDate()))
                /* meetingDate가 오늘이고, meetingEndTime이 1시간 전부터 현재까지인 회의 */
                .where(meeting.meetingEndTime.hour().between(
                                oneHourAgoTime.getHour(), nowTime.getHour()
                        )
                )
                .fetch();
    }

    @Override
    public List<Meeting> getPopularMeetings() {
        List<Long> ids= queryFactory.select(bookMark.meeting.id)
                .from(bookMark)
                .join(bookMark.meeting, meeting)
                .where(meeting.status.eq(MeetingStatusEnum.OPEN).or(meeting.status.eq(MeetingStatusEnum.FULL)))
                .groupBy(bookMark.meeting.id)
                .orderBy(bookMark.meeting.count().desc())
                .limit(5)
                .fetch();

        return queryFactory.selectFrom(meeting)
                .where(meeting.id.in(ids))
                .fetch();
    }

    public List<Meeting> findHeldMeetingsByCreatorId(Long memberId) {
        QMeeting m = QMeeting.meeting;

        BooleanExpression whereClause = m.creator.id.eq(memberId)
                .and(m.status.ne(MeetingStatusEnum.DELETE));

        List<OrderSpecifier<?>> orderBy = new ArrayList<>();
        orderBy.add(new CaseBuilder()
                .when(m.status.eq(MeetingStatusEnum.OPEN).or(m.status.eq(MeetingStatusEnum.FULL)))
                .then(1)
                .otherwise(2).asc());
        orderBy.add(m.meetingDate.asc());

        return queryFactory
                .selectFrom(m)
                .where(whereClause)
                .orderBy(orderBy.toArray(new OrderSpecifier[0]))
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
