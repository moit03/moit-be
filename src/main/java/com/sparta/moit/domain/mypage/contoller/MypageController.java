package com.sparta.moit.domain.mypage.contoller;

import com.sparta.moit.domain.mypage.dto.MypageResponseDto;
import com.sparta.moit.domain.mypage.service.MypageService;
import com.sparta.moit.domain.mypage.service.MypageServiceImpl;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "MyPage")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MypageController implements MypageControllerDocs{
    private final MypageService mypageService;

    /* 참여한 모임, 스터디 시간, 개최한 모임 데이터 전달 */
    @GetMapping("/myinfo")
    public ResponseEntity<?> getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        MypageResponseDto mypageResponseDto = mypageService.getMypageInfo(userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success("마이페이지 조회 완료", mypageResponseDto));
    }

}
