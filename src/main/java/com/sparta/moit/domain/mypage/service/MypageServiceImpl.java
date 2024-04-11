package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.meeting.dto.GetMyPageDto;
import com.sparta.moit.domain.meeting.entity.Meeting;
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
        int enterMeetingCount = meetingMemberRepository.countByMemberId(member.getId());

        /* 개최한 모임 개수 */
        int heldMeetingCount = meetingRepository.countByCreator(member);

        List<GetMyPageDto> studyTimeList = meetingRepository.getMyPage(member.getId());

        /* 총 공부시간 */
        long totalStudyTimeMinutes = 0;
        for (GetMyPageDto meeting : studyTimeList) {
            LocalDateTime startTime = meeting.getMeetingStartTime();
            LocalDateTime endTime = meeting.getMeetingEndTime();
            long studyTimeMinutes = calculateStudyTime(startTime, endTime);
            totalStudyTimeMinutes += studyTimeMinutes;
            log.info(meeting.getMeetingStartTime().toString());
            log.info(meeting.getMeetingEndTime().toString());
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
            Duration duration = Duration.between(meetingStartTime, meetingEndTime);
            return duration.toMinutes();
        } else {
            return 0;
        }
    }

    @Override
    public List<MypageMeetingResponseDto> getMypageMeetingList(Long memberId) {
        List<Meeting> meetingList= meetingRepository.findMeetingsByMember(memberId);
        return meetingList.stream().map(MypageMeetingResponseDto::fromEntity).toList();
    }
}