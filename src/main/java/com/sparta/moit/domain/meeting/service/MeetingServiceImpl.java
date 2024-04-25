package com.sparta.moit.domain.meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.dto.*;
import com.sparta.moit.domain.meeting.entity.*;
import com.sparta.moit.domain.meeting.repository.*;
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
import java.util.Objects;
import java.util.Optional;

import static com.sparta.moit.domain.meeting.entity.QMeeting.meeting;

@Slf4j(topic = "Meeting Service Log")
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final SkillRepository skillRepository;
    private final CareerRepository careerRepository;
    private final MemberRepository memberRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final MeetingSkillRepository meetingSkillRepository;
    private final MeetingCareerRepository meetingCareerRepository;
    private final AddressUtil addressUtil;

    /*모임 등록*/
    @Override
    @Transactional
    public Long createMeeting(CreateMeetingRequestDto requestDto, Member member) {
        Meeting meeting = requestDto.toEntity(member);
        Meeting savedMeeting = meetingRepository.save(meeting);

        log.info("Meeting created at: " + savedMeeting.getCreatedAt());
        log.info("Meeting modified at: " + savedMeeting.getModifiedAt());
        log.info("meetingStartTime : " + savedMeeting.getMeetingStartTime());
        log.info("meetingEndTime : " + savedMeeting.getMeetingEndTime());


        saveSkills(requestDto.getSkillIds(), savedMeeting);
        saveCareers(requestDto.getCareerIds(), savedMeeting);
        saveMeetingMember(member, savedMeeting);

        return savedMeeting.getId();
    }

    /*모임 수정*/
    @Override
    @Transactional
    public Long updateMeeting(UpdateMeetingRequestDto requestDto, Member member, Long meetingId) {

        Meeting meeting = meetingRepository.findByIdAndCreator(meetingId, member)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORITY_ACCESS));

        meetingSkillRepository.deleteByMeeting(meeting);
        meetingCareerRepository.deleteByMeeting(meeting);

        saveSkills(requestDto.getSkillIds(), meeting);
        saveCareers(requestDto.getCareerIds(), meeting);
        meeting.updateMeeting(requestDto);
        return meetingId;
    }

    /*모임 삭제*/
    @Override
    @Transactional
    public void deleteMeeting(Member member, Long meetingId) {

        Meeting meeting = meetingRepository.findByIdAndCreator(meetingId, member)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORITY_ACCESS));

        meeting.deleteStatus();

    }

    /*모임 조회*/
    @Override
    public Slice<GetMeetingResponseDto> getMeetingList(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), 10);
        Slice<Meeting> sliceList = meetingRepository.getMeetingSlice(locationLat, locationLng, skillId, careerId, pageable);
        return sliceList.map(GetMeetingResponseDto::fromEntity);
    }

    /* 모임 조회 (NativeQuery) */
    @Override
    public List<GetMeetingResponseDto> getMeetingListNativeQuery(int page, Double locationLat, Double locationLng, List<Long> skillId, List<Long> careerId) {
        List<Meeting> meetingList;
        if (skillId != null) {
            if (careerId != null) {
                // skillId와 careerId가 모두 존재하는 경우
                meetingList = meetingRepository.getMeetingsWithSkillAndCareer(locationLat, locationLng, skillId, careerId, 16, page);
            } else {
                // skillId만 존재하는 경우
                meetingList = meetingRepository.getMeetingsWithSkill(locationLat, locationLng, skillId, 16, page);
            }
        } else {
            if (careerId != null) {
                // careerId만 존재하는 경우
                meetingList = meetingRepository.getMeetingsWithCareer(locationLat, locationLng, careerId, 16, page);
            } else {
                // skillId와 careerId 모두 존재하지 않는 경우
                meetingList = meetingRepository.getNearestMeetings(locationLat, locationLng, 16, page);
            }
        }
        return meetingList.stream().map(GetMeetingResponseDto::fromEntity).toList();
    }



    /*모임 상세 조회 */
    @Override
    public GetMeetingDetailResponseDto getMeetingDetail(Long meetingId, Optional<Member> member) {

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        if (meeting.getStatus().equals(MeetingStatusEnum.DELETE)) {
            throw new CustomException(ErrorCode.MEETING_NOT_FOUND);
        }

        List<String> careerNameList = meetingRepository.findCareerNameList(meetingId);
        List<String> skillNameList = meetingRepository.findSkillNameList(meetingId);
        if (member.isEmpty()) {
            return GetMeetingDetailResponseDto.fromEntity(meeting, careerNameList, skillNameList, false, false);
        }
        boolean isJoin = meetingMemberRepository.existsByMemberIdAndMeetingId(member.get().getId(), meetingId);
        boolean isbookMarked = meetingMemberRepository.existsByMemberIdAndMeetingId(member.get().getId(), meetingId);
        return GetMeetingDetailResponseDto.fromEntity(meeting, careerNameList, skillNameList, isJoin, isbookMarked);
    }

    /*주소별 모임 조회*/
    @Override
    public List<GetMeetingResponseDto> getMeetingListByAddress(String firstRegion, String secondRegion, int page) throws JsonProcessingException {
        AddressResponseDto address = addressUtil.searchAddress(firstRegion, secondRegion);
        List<Meeting> meetingList = meetingRepository.getNearestMeetings(Double.parseDouble(address.getLat()), Double.parseDouble(address.getLng()), 16, page);
        return meetingList.stream().map(GetMeetingResponseDto::fromEntity).toList();
    }

    /* 모임 검색 */
    @Override
    public Slice<GetMeetingResponseDto> getMeetingListBySearch(String keyword, int page) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), 10);
        Slice<Meeting> meetingList = meetingRepository.findByKeyword(keyword, pageable);
        return meetingList.map(GetMeetingResponseDto::fromEntity);
    }

    /* 인기 모임 top 5 */
    @Override
    public List<GetPopularResponseDto> getPopularMeeting() {
        List<Meeting> meetingList = meetingRepository.getPopularMeetings();
        return meetingList.stream().map(GetPopularResponseDto::fromEntity).toList();
    }

    /*모임 참가*/
    @Override
    @Transactional
    public Long enterMeeting(Member member, Long meetingId) {

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        /*중복 참가 여부 확인*/
        boolean isMember = meetingMemberRepository.existsByMemberIdAndMeetingId(member.getId(), meetingId);
        if (isMember) {
            throw new CustomException(ErrorCode.ALREADY_MEMBER);
        }

        /* 현재 모임 참가 인원 수를 가져오기 */
        Short registeredCount = meeting.getRegisteredCount();

        /* 모임의 최대 인원 수를 가져오기 */
        Short totalCount = meeting.getTotalCount();

        /* 인원이 다 찼는지 확인 : 예외처리 */
        if (registeredCount >= totalCount) {
            throw new CustomException(ErrorCode.MEETING_FULL);
        }

        /*모임 엔티티의 등록된 참가자 수 업데이트*/
        registeredCount = meeting.incrementRegisteredCount(); /*모임 참가자 수 증가*/

        /* 인원이 다 찼는지 확인 */
        if (Objects.equals(registeredCount, totalCount)) {
            meeting.updateStatus();
        }

        MeetingMember meetingMember = MeetingMember.builder()
                .member(member)
                .meeting(meeting)
                .build();
        meetingMemberRepository.save(meetingMember);
        return meetingId;
    }

    /*모임 탈퇴*/
    @Override
    public void leaveMeeting(Member member, Long meetingId) {

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        MeetingMember meetingMember = meetingMemberRepository.findByMemberAndMeeting(member1, meeting)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_MEETING_MEMBER));

        /*작성자 탈퇴 불가 코드*/
        if (member1.equals(meeting.getCreator())) {
            throw new CustomException(ErrorCode.CREATOR_CAN_NOT_LEAVE);
        }

        meeting.decrementRegisteredCount();

        meetingMemberRepository.delete(meetingMember);
    }

    /*기술 저장*/
    private void saveSkills(List<Long> skillIds, Meeting meeting) {
        for (Long skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new CustomException(ErrorCode.VALIDATION_ERROR));

            MeetingSkill meetingSkill = MeetingSkill.builder()
                    .meeting(meeting)
                    .skill(skill)
                    .build();

            meetingSkillRepository.save(meetingSkill);
        }
    }

    /*경력 저장*/
    private void saveCareers(List<Long> careerIds, Meeting meeting) {
        for (Long careerId : careerIds) {
            Career career = careerRepository.findById(careerId)
                    .orElseThrow(() -> new CustomException(ErrorCode.VALIDATION_ERROR));

            MeetingCareer meetingCareer = MeetingCareer.builder()
                    .meeting(meeting)
                    .career(career)
                    .build();

            meetingCareerRepository.save(meetingCareer);
        }
    }

    /* 모임 회원 저장 */
    private void saveMeetingMember(Member member, Meeting meeting) {
        MeetingMember meetingMember = MeetingMember.builder()
                .member(member)
                .meeting(meeting)
                .build();
        meetingMemberRepository.save(meetingMember);
    }
}
