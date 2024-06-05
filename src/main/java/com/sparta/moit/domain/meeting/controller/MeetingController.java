package com.sparta.moit.domain.meeting.controller;

import com.sparta.moit.domain.meeting.controller.docs.MeetingControllerDocs;
import com.sparta.moit.domain.meeting.dto.*;
import com.sparta.moit.domain.meeting.service.MeetingService;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/json")
    public ResponseEntity<ResponseDto<Long>> createMeeting(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long meetingId = meetingService.createMeeting(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("모임 등록 완료", meetingId));
    }

    /*모임 등록*/
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createMeetingArray(@RequestBody CreateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long meetingId = meetingService.createMeetingArray(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("모임 등록 완료", meetingId));
    }

    /*모임 수정*/
    @PutMapping("/json/{meetingId}")
    public ResponseEntity<ResponseDto<Long>> updateMeeting(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long updatedMeetingId = meetingService.updateMeeting(requestDto, userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 수정 완료", updatedMeetingId));
    }
    /*모임 array 수정*/
    @PutMapping("/{meetingId}")
    public ResponseEntity<ResponseDto<Long>> updateMeetingArray(@PathVariable Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long updatedMeetingId = meetingService.updateMeetingArray(requestDto, userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 수정 완료", updatedMeetingId));
    }

    /*모임 삭제*/
    @DeleteMapping("/{meetingId}")
    public ResponseEntity<ResponseDto<String>> deleteMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.deleteMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 삭제 완료", "삭제"));
    }

    @GetMapping("/json")
    public ResponseEntity<ResponseDto<Slice<GetMeetingResponseDto>>> getMeetingListPostgreJson(
            @RequestParam Double locationLat,
            @RequestParam Double locationLng,
            @RequestParam(required = false) List<String> skillId,
            @RequestParam(required = false) List<String> careerId,
            @RequestParam(defaultValue = "1") int page)
    {

        String skillIdsStr = (skillId == null || skillId.isEmpty()) ? null : String.join(",", skillId);
        String careerIdsStr = (careerId == null || careerId.isEmpty()) ? null : String.join(",", careerId);

        Slice<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListPostgreJson(page
                , locationLat
                , locationLng
                , skillIdsStr
                , careerIdsStr
        );

        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Slice<GetMeetingArrayResponseDto>>> getMeetingListPostgreArray(
            @RequestParam Double locationLat,
            @RequestParam Double locationLng,
            @RequestParam(required = false) List<String> skillId,
            @RequestParam(required = false) List<String> careerId,
            @RequestParam(defaultValue = "1") int page)
    {

        String skillIdsStr = (skillId == null || skillId.isEmpty()) ? null : String.join(",", skillId);
        String careerIdsStr = (careerId == null || careerId.isEmpty()) ? null : String.join(",", careerId);

        Slice<GetMeetingArrayResponseDto> responseDtoList = meetingService.getMeetingListPostgreArray(
                page
                , locationLat
                , locationLng
                , skillIdsStr
                , careerIdsStr
        );

        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }



    /*모임 조회*/
    @GetMapping("/mysql")
    public ResponseEntity<ResponseDto<Slice<GetMeetingResponseDto>>> getMeetingList
    (@RequestParam Double locationLat,
     @RequestParam Double locationLng,
     @RequestParam(required = false) List<Long> skillId,
     @RequestParam(required = false) List<Long> careerId,
     @RequestParam(defaultValue = "1") int page) {
        Slice<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingList(page, locationLat, locationLng, skillId, careerId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    /* 모임 조회 NativeQuery */
    @GetMapping("/native")
    public ResponseEntity<ResponseDto<List<GetMeetingResponseDto>>> getMeetingListNativeQuery(@RequestParam Double locationLat,
                                                                                              @RequestParam Double locationLng,
                                                                                              @RequestParam(required = false) List<Long> skillId,
                                                                                              @RequestParam(required = false) List<Long> careerId,
                                                                                              @RequestParam(defaultValue = "1") int page) {
        List<GetMeetingResponseDto> responseDtoList = meetingService.getMeetingListNativeQuery(page, locationLat, locationLng, skillId, careerId);
        return ResponseEntity.ok().body(ResponseDto.success("조회 완료", responseDtoList));
    }

    /*모임 상세 조회*/
    @GetMapping("/{meetingId}")
    public ResponseEntity<ResponseDto<GetMeetingDetailResponseDto>> getMeetingDetail(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Optional<Member> member = Optional.ofNullable(userDetails).map(UserDetailsImpl::getUser);
        GetMeetingDetailResponseDto responseDto = meetingService.getMeetingDetail(meetingId, member);

        String message = member.isPresent() ? "로그인 유저의 조회 완료" : "비로그인 조회 완료";
        return ResponseEntity.ok().body(ResponseDto.success(message, responseDto));
    }


    /* 모임 검색 */
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<Slice<GetMeetingArrayResponseDto>>> getMeetingListBySearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) {
        Slice<GetMeetingArrayResponseDto> responseDtoList = meetingService.getMeetingListBySearch(keyword, page);
        return ResponseEntity.ok().body(ResponseDto.success("검색 완료", responseDtoList));
    }

    /* 인기 모임 top 5 */
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularMeeting() {
        List<GetPopularResponseDto> responseDtoList = meetingService.getPopularMeeting();
        return ResponseEntity.ok().body(ResponseDto.success("인기 모임 top 5", responseDtoList));
    }

    /*모임 참가*/
    @PostMapping("my-meetings/{meetingId}")
    public ResponseEntity<ResponseDto<Long>> enterMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long enterMeetingId = meetingService.enterMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 참가 완료", enterMeetingId));
    }

    /*모임 탈퇴*/
    @DeleteMapping("/{meetingId}/signout")
    public ResponseEntity<ResponseDto<String>> leaveMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.leaveMeeting(userDetails.getUser(), meetingId);
        return ResponseEntity.ok().body(ResponseDto.success("모임 탈퇴 완료", "탈퇴"));
    }
}
