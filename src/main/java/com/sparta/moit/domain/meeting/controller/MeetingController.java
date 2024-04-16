package com.sparta.moit.domain.meeting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.meeting.controller.docs.MeetingControllerDocs;
import com.sparta.moit.domain.meeting.dto.CreateMeetingRequestDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingDetailResponseDto;
import com.sparta.moit.domain.meeting.dto.GetMeetingResponseDto;
import com.sparta.moit.domain.meeting.dto.UpdateMeetingRequestDto;
import com.sparta.moit.domain.meeting.service.MeetingService;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j(topic = "미팅 로그")
@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController implements MeetingControllerDocs {
    private final MeetingService meetingService;

    /*모임 등록*/
    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long meetingId = meetingService.createMeeting(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("모임 등록 완료", meetingId));
    }

    /*모임 수정*/
    @PutMapping("/{meetingId}")
    public ResponseEntity<?> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long updatedMeetingId = meetingService.updateMeeting(requestDto, userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 수정 완료", updatedMeetingId));
    }

    /*모임 삭제*/
    @DeleteMapping("/{meetingId}")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.deleteMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 삭제 완료", null));
    }

    /*모임 조회*/
    @GetMapping
    public ResponseEntity<?> getMeetingList(@RequestParam Double locationLat,
                                            @RequestParam Double locationLng,
                                            @RequestParam(required = false) List<Long> skillId,
                                            @RequestParam(required = false) List<Long> careerId,
                                            @RequestParam(defaultValue = "1") int page) {
        Slice<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingList(page, locationLat, locationLng, skillId, careerId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    /* 모임 조회 NativeQuery */
    @GetMapping("/native")
    public ResponseEntity<?> getMeetingListNativeQuery(@RequestParam Double locationLat,
                                                @RequestParam Double locationLng,
                                                @RequestParam(required = false) List<Long> skillId,
                                                @RequestParam(required = false) List<Long> careerId,
                                                @RequestParam(defaultValue = "1") int page) {
        List<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListNativeQuery(page, locationLat, locationLng, skillId, careerId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    /*모임 상세 조회*/
    @GetMapping("/{meetingId}")
    public ResponseEntity<?> getMeetingDetail(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Optional<Member> member = Optional.ofNullable(userDetails).map(UserDetailsImpl::getUser);
        GetMeetingDetailResponseDto responseDto = meetingService.getMeetingDetail(meetingId, member);

        String message = member.isPresent() ? "로그인 유저의 조회 완료" : "비로그인 조회 완료";
        return ResponseEntity.ok().body(ResponseDto.success(message, responseDto));
    }


    /* 모임 검색 */
    @GetMapping("/search")
    public ResponseEntity<?> getMeetingListBySearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) {
        Slice<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListBySearch(keyword, page);
        return ResponseEntity.ok().body(ResponseDto.success("검색 완료", responseDtoList));
    }

    /*모임 참가*/
    @PostMapping("my-meetings/{meetingId}")
    public ResponseEntity<?> enterMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long enterMeetingId = meetingService.enterMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 참가 완료", enterMeetingId));
    }

    /*모임 탈퇴*/
    @DeleteMapping("/{meetingId}/signout")
    public ResponseEntity<?> leaveMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.leaveMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 탈퇴 완료", null));
    }
}
