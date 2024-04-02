package com.sparta.moit.domain.meeting.service;

import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.CreateMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.entity.*;
import com.sparta.moit.domain.meeting.repository.CareerRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.meeting.repository.MeetingSkillRepository;
import com.sparta.moit.domain.meeting.repository.SkillRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "Meeting Service Log")
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{
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
    public CreateMeetingResponseDto createMeeting(CreateMeetingRequestDto requestDto) {
        Member member = memberRepository.findById(1L).orElseThrow(() ->
                new IllegalArgumentException("아이디 문제")
        );

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

        meetingRepository.save(meeting);
        return CreateMeetingResponseDto.fromEntity(meeting);
    }
}
