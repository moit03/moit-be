package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.meeting.entity.*;
import com.sparta.moit.domain.meeting.repository.CareerRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.meeting.repository.SkillRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "Meeting Service Log")
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final SkillRepository skillRepository;
    private final CareerRepository careerRepository;
    private final MemberRepository memberRepository;

    public List<GetMeetingResponseDto> getMeetingList(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName) {
        List<Meeting> result = meetingRepository.findAllByFilter(careerTypes, skillTypes, region1depthName, region2depthName);
        return result.stream().map(GetMeetingResponseDto::fromEntity)
                .toList();
    }

    @Override
    public Long createMeeting(CreateMeetingRequestDto requestDto, Member member) {

        List<Long> skillIds = requestDto.getSkillIds();
        Meeting meeting = requestDto.toEntity(member);
        List<Skill> skills = skillRepository.findByIdIn(skillIds);

        for (Skill skill : skills) {
            MeetingSkill meetingSkill = MeetingSkill.builder()
                    .meeting(meeting)
                    .skill(skill)
                    .build();
            meeting.getSkills().add(meetingSkill);
        }

        List<Long> careerIds = requestDto.getCareerIds();
        List<Career> careers = careerRepository.findByIdIn(careerIds);
        for (Career career : careers) {
            MeetingCareer meetingCareer = MeetingCareer.builder()
                    .meeting(meeting)
                    .career(career)
                    .build();
            meeting.getCareers().add(meetingCareer);
        }

        Meeting savedMeeting = meetingRepository.save(meeting);
        Long meetingId = savedMeeting.getId();
        return meetingId;
    }

    @Override
    @Transactional
    public Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId) {

        Meeting meeting = meetingRepository.findByIdAndMember(meetingId,member)
                        .orElseThrow(()-> new CustomException(ErrorCode.AUTHORITY_ACCESS));

        meeting.updateMeeting(requestDto);
        return meetingId;
    }
}
