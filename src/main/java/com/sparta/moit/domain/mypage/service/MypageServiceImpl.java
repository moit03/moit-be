package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.meeting.dto.GetMyPageDto;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import com.sparta.moit.domain.meeting.repository.MeetingMemberRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.domain.mypage.dto.MypageMeetingResponseDto;
import com.sparta.moit.domain.mypage.dto.MypageResponseDto;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j(topic = "Mypage")
@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
    private final MemberRepository memberRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final MeetingRepository meetingRepository;


    /*select * from meeting
    left join meeting_member on meeting.id = meeting_member.meeting_id
            where
    meeting_id = 110
    and
    meeting_member.member_id=5;*/

    @Override
    @Transactional(readOnly = true)
    public MypageResponseDto getMypageInfo(Member member) {

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        /* 참여한 모임 개수 조회 */
        int enterMeetingCount = meetingMemberRepository.countByMemberIdAndMeeting_StatusNot(member.getId(), MeetingStatusEnum.DELETE);
        // TODO: meeting 중 status != DELETE 인 것만 count 하도록 변경

        /* 개최한 모임 개수 */
        int heldMeetingCount = meetingRepository.countByCreatorAndStatusNot(member, MeetingStatusEnum.DELETE);
        // TODO: meeting 중 status != DELETE 인 것만 count 하도록 변경


        List<GetMyPageDto> studyTimeList = meetingRepository.getMyPage(member.getId(), MeetingStatusEnum.DELETE);
        // TODO: meeting 중 status != DELETE 인 것만 count 하도록 변경

        /* 총 공부시간 */
        long totalStudyTimeMinutes = 0;
        for (GetMyPageDto meeting : studyTimeList) {
            LocalDateTime startTime = meeting.getMeetingStartTime();
            LocalDateTime endTime = meeting.getMeetingEndTime();
            long studyTimeMinutes = calculateStudyTime(startTime, endTime);
            totalStudyTimeMinutes += studyTimeMinutes;
        }

        /* 시간 형식으로 변환 */
        long hours = totalStudyTimeMinutes / 60;
        long minutes = totalStudyTimeMinutes % 60;
        String studyTime = String.format("%02d:%02d", hours, minutes);

        return MypageResponseDto.builder()
                .enterMeeting(enterMeetingCount)
                .studyTime(studyTime)
                .heldMeeting(heldMeetingCount)
                .build();
    }

    public long calculateStudyTime(LocalDateTime meetingStartTime, LocalDateTime meetingEndTime) {
        if (meetingStartTime != null && meetingEndTime != null) {
            // LocalDateTime 값을 UTC 시간대의 ZonedDateTime으로 변환합니다.
            ZonedDateTime utcStartTime = meetingStartTime.atZone(ZoneId.of("UTC"));
            ZonedDateTime utcEndTime = meetingEndTime.atZone(ZoneId.of("UTC"));

            // UTC ZonedDateTime 값을 서울 시간대로 변환합니다.
            ZonedDateTime seoulStartTime = utcStartTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            ZonedDateTime seoulEndTime = utcEndTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

            /* 시간 범위 유효성 검증 */
            if (seoulStartTime.isAfter(seoulEndTime)) {
                throw new IllegalArgumentException("meetingStartTime은 meetingEndTime보다 늦을 수 없습니다.");
            }

            Duration duration = Duration.between(seoulStartTime, seoulEndTime);
            return duration.toMinutes();
        } else {
            return 0;
        }
    }

    @Override
    public List<MypageMeetingResponseDto> getMypageMeetingList(Long memberId) {
        List<Meeting> meetingList = meetingRepository.findMeetingsByMember(memberId);
        return meetingList.stream().map(MypageMeetingResponseDto::fromEntity).toList();
    }
    // TODO : OPEN, FULL / COMPLETE 인것 분리해서 api 작성, 모두 무한 스크롤로 구성 (pageSize 10개)

    @Override
    public List<MypageMeetingResponseDto> getMypageHeldList(Long memberId) {
        List<Meeting> heldMeetingList = meetingRepository.findMeetingsByCreatorIdAndStatusNot(memberId, MeetingStatusEnum.DELETE);
        return heldMeetingList.stream().map(MypageMeetingResponseDto::fromEntity).toList();
    }
    @Override
    public List<MypageMeetingResponseDto> getCompletedMeetings(Long memberId) {
        List<Meeting> completedMeetingList = meetingRepository.findMeetingsByCreatorIdAndStatus(memberId, MeetingStatusEnum.COMPLETE);
        return completedMeetingList.stream().map(MypageMeetingResponseDto::fromEntity).toList();
    }
}