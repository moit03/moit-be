package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingDetailResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.meeting.entity.*;
import com.sparta.moit.domain.meeting.repository.CareerRepository;
import com.sparta.moit.domain.meeting.repository.MeetingMemberRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.meeting.repository.SkillRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.common.dto.AddressResponseDto;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import com.sparta.moit.global.util.AddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final MeetingMemberRepository meetingMemberRepository;
    private final AddressUtil addressUtil;


    /*public List<GetMeetingResponseDto> getMeetingList(List<Integer> careerTypes, List<Integer> skillTypes, String region1depthName, String region2depthName) {
        List<Meeting> result = meetingRepository.findAllByFilter(careerTypes, skillTypes, region1depthName, region2depthName);
        return result.stream().map(GetMeetingResponseDto::fromEntity)
                .toList();
    }*/

    /*모임 등록*/
    @Override
    @Transactional
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

    /*모임 수정*/
    @Override
    @Transactional
    public Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId) {

        Meeting meeting = meetingRepository.findByIdAndCreator(meetingId, member)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORITY_ACCESS));

        meeting.updateMeeting(requestDto);
        return meetingId;
    }

    /*모임 조회*/
    @Override
    public Slice<GetMeetingResponseDto> getMeetingList(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), 16);
        Slice<Meeting> sliceList = meetingRepository.getMeetingSlice(locationLat, locationLng, skillId, careerId, pageable);
        return sliceList.map(GetMeetingResponseDto::fromEntity);
    }

    /*모임 참가*/
    @Override
    @Transactional
    public Long enterMeeting(Member member, Long meetingId) {

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        MeetingMember meetingMember = MeetingMember.builder()
                .member(member1)
                .meeting(meeting)
                .build();
        meetingMemberRepository.save(meetingMember);

        return meetingId;
    }

    /*주소별 모임 조회*/
    @Override
    public List<GetMeetingResponseDto> getMeetingListByAddress(String firstRegion, String secondRegion, int page) throws JsonProcessingException {
        AddressResponseDto address = addressUtil.searchAddress(firstRegion, secondRegion);
        List<Meeting> meetingList = meetingRepository.getNearestMeetings(Double.parseDouble(address.getLat()), Double.parseDouble(address.getLng()), 16, page);
        return meetingList.stream().map(GetMeetingResponseDto::fromEntity).toList();
    }

    /*모임 상세 조회*/
    @Override
    public GetMeetingDetailResponseDto getMeetingDetail(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        List<String> careerNameList = meetingRepository.findCareerNameList(meetingId);
        List<String> skillNameList = meetingRepository.findSkillNameList(meetingId);

        return GetMeetingDetailResponseDto.fromEntity(meeting, careerNameList, skillNameList);

    }

    /*모임 삭제*/
    @Override
    @Transactional
    public void deleteMeeting(Member member, Long meetingId) {

        Meeting meeting = meetingRepository.findByIdAndCreator(meetingId, member)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORITY_ACCESS));

        meetingRepository.deleteById(meetingId);
    }

    /* 모임 검색 */
    @Override
    public Slice<GetMeetingResponseDto> getMeetingListBySearch(String keyword, int page) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), 16);
        Slice<Meeting> meetingList = meetingRepository.findByKeyword(keyword, pageable);
        return meetingList.map(GetMeetingResponseDto::fromEntity);
    }

    /*모임 탈퇴*/
    @Override
    @Transactional
    public void leaveMeeting(Member member, Long meetingId) {

        Member member0 = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        MeetingMember meetingMember = MeetingMember.builder()
                .member(member0)
                .meeting(meeting)
                .build();
        meetingMemberRepository.delete(meetingMember);
    }
}
