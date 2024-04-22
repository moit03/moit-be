package com.sparta.moit.domain.mypage.controller;

import com.sparta.moit.domain.mypage.controller.docs.MypageControllerDocs;
import com.sparta.moit.domain.mypage.dto.MypageMeetingResponseDto;
import com.sparta.moit.domain.mypage.dto.MypageResponseDto;
import com.sparta.moit.domain.mypage.service.MypageService;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "MyPage")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MypageController implements MypageControllerDocs {
    private final MypageService mypageService;

    /* 참여한 모임, 스터디 시간, 개최한 모임 데이터 전달 */
    @GetMapping("/myinfo")
    public ResponseEntity<ResponseDto<MypageResponseDto>> getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MypageResponseDto mypageResponseDto = mypageService.getMypageInfo(userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("마이페이지 조회 완료", mypageResponseDto));
    }

    /* 참여한 모임 정보 리스트 */
    @GetMapping("/meeting")
    public ResponseEntity<ResponseDto<List<MypageMeetingResponseDto>>> getMypageMeetingList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MypageMeetingResponseDto> responseDtoList = mypageService.getMypageMeetingList(userDetails.getUser().getId());
        return ResponseEntity.ok().body(ResponseDto.success("마이페이지 조회 완료", responseDtoList));
    }

    /* 개최한 모임 정보 리스트 */
    @GetMapping("/meeting/held")
    public ResponseEntity<ResponseDto<List<MypageMeetingResponseDto>>> getMypageHeldList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MypageMeetingResponseDto> responseDtoList = mypageService.getMypageHeldList(userDetails.getUser().getId());
        return ResponseEntity.ok().body(ResponseDto.success("개최한 모임 조회 완료", responseDtoList));
    }

    /* 완료한 모임 정보 리스트 */
    @GetMapping("/meeting/complete")
    public ResponseEntity<ResponseDto<List<MypageMeetingResponseDto>>> getCompletedMeetings(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MypageMeetingResponseDto> responseDtoList = mypageService.getCompletedMeetings(userDetails.getUser().getId());
        return ResponseEntity.ok().body(ResponseDto.success("완료된 모임 조회 완료", responseDtoList));
    }
}
