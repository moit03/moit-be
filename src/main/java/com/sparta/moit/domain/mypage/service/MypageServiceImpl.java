package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.bookmark.repository.BookMarkRepository;
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
    private final BookMarkRepository bookMarkRepository;


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

        List<GetMyPageDto> studyTimeList = meetingRepository.getMyPage(member.getId(), MeetingStatusEnum.COMPLETE);
        // TODO: meeting 중 status = COMPLETE 인 것만 count 하도록 변경

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
            /* meetingStartTime이 meetingEndTime 이후나 같은지 체크*/
            if (meetingStartTime.isAfter(meetingEndTime) || meetingStartTime.equals(meetingEndTime)) {
                throw new IllegalArgumentException("meetingStartTime은 meetingEndTime보다 이전이어야 합니다.");
            }

            Duration duration = Duration.between(meetingStartTime, meetingEndTime);

            /* - 시간 방지 */
            if (duration.isNegative()) {
                throw new IllegalArgumentException("meetingStartTime은 meetingEndTime보다 이전이어야 합니다.");
            }

            return duration.toMinutes();
        } else {
            return 0;
        }
    }

    @Override
    public List<MypageMeetingResponseDto> getMypageMeetingList(Long memberId) {
        List<Meeting> meetingList = meetingRepository.findMeetingsByMember(memberId);
        return meetingList.stream()
                .filter(meeting -> meeting.getStatus() == MeetingStatusEnum.OPEN ||
                        meeting.getStatus() == MeetingStatusEnum.FULL ||
                        meeting.getStatus() == MeetingStatusEnum.COMPLETE)
                .map(MypageMeetingResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<MypageMeetingResponseDto> getMypageHeldList(Long memberId) {
        List<Meeting> heldMeetingList = meetingRepository.findMeetingsByCreatorIdAndStatusNot(memberId, MeetingStatusEnum.DELETE);
        return heldMeetingList.stream()
                .filter(meeting -> meeting.getStatus() == MeetingStatusEnum.OPEN ||
                        meeting.getStatus() == MeetingStatusEnum.FULL ||
                        meeting.getStatus() == MeetingStatusEnum.COMPLETE)
                .map(MypageMeetingResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<MypageMeetingResponseDto> getCompletedMeetings(Long memberId) {
        List<Meeting> completedMeetingList = meetingRepository.findMeetingsByCreatorIdAndStatus(memberId, MeetingStatusEnum.COMPLETE);
        return completedMeetingList.stream()
                .map(MypageMeetingResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<MypageMeetingResponseDto> getMypageBookmarkedMeetings(Long memberId) {
        List<Meeting> bookmarkedMeetings = bookMarkRepository.findBookmarkedMeetingsByMemberId(memberId);
        return bookmarkedMeetings.stream()
                .map(meeting -> MypageMeetingResponseDto.fromEntity(meeting, true))
                .toList();
    }
}