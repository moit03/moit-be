package com.sparta.moit.domain.mypage.service;

import com.sparta.moit.domain.meeting.dto.GetMyPageDto;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.repository.MeetingMemberRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
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
    public MypageResponseDto getMypageInfo(Member member, Long memberId) {
        // 사용자 확인
        Member user = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        // 참여한 모임 개수 조회
        int enterMeetingCount = meetingMemberRepository.countByMemberId(member.getId());

        // 개최한 모임 개수 조회
        int heldMeetingCount = meetingRepository.countByCreator(member);

        List<GetMyPageDto> studyTimeList = meetingRepository.getMyPage(member.getId());



        for (GetMyPageDto meeting : studyTimeList) {
            log.info(meeting.getMeetingStartTime().toString());
            log.info(meeting.getMeetingEndTime().toString());
        }

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        String studyTime = calculateStudyTime(member.getId(), startTime, endTime);

        // 마이페이지 응답 DTO를 빌더 패턴을 사용하여 생성
        return MypageResponseDto.builder()
                .enterMeeting(enterMeetingCount)  // 참여한 모임 수 설정
                .studyTime(studyTime)  // 스터디 시간 설정
                .heldMeeting(heldMeetingCount)  // 개최한 모임 수 설정
                .build();  // MypageResponseDto 객체 생성
    }

    // 수정된 calculateStudyTime 메서드
    public String calculateStudyTime(Long memberId, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime) {
        if (meetingStartTime != null && meetingEndTime != null) {
            Duration duration = Duration.between(meetingStartTime, meetingEndTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            return String.format("%02d:%02d", hours, minutes);
        } else {
            return null;
        }
    }
}