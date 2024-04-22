package com.sparta.moit.domain.bookmark.controller;

import com.sparta.moit.domain.bookmark.controller.docs.BookMarkControllerDocs;
import com.sparta.moit.domain.bookmark.dto.BookMarkDto;
import com.sparta.moit.domain.bookmark.service.BookMarkService;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.common.dto.ResponseDto;
import com.sparta.moit.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookMarkController implements BookMarkControllerDocs {
    private final BookMarkService bookMarkService;
    private final MemberRepository memberRepository;

    /**
     * 북마크 추가
     *
     * @param meetingId 즐겨찾기에 추가할 모임 ID
     * @param userDetails 현재 인증된 회원 정보
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<BookMarkDto>> addMeetingBookmark(@RequestParam Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        BookMarkDto bookmarkDto = new BookMarkDto(meetingId, member.getId());

        bookMarkService.addMeetingBookmark(bookmarkDto, member);
        return ResponseEntity.ok().body(ResponseDto.success("북마크 완료", bookmarkDto));
    }

    /**
     * 북마크 해제
     *
     * @param meetingId 즐겨찾기에서 제거할 모임 ID
     * @param userDetails 현재 인증된 회원 정보
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDto<BookMarkDto>> removeMeetingBookmark(@RequestParam Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        BookMarkDto bookmarkDto = new BookMarkDto(meetingId, member.getId());

        bookMarkService.removeMeetingBookmark(bookmarkDto, member);
        return ResponseEntity.ok().body(ResponseDto.success("북마크 해제", bookmarkDto));
    }

    /**
     * 해당 모임이 북마크 되어있는지 확인
     *
     * @param meetingId 확인할 모임 ID
     * @param userDetails 현재 인증된 회원 정보
     * @return 즐겨찾기에 있으면 true, 없으면 false
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> isBookmarked(@RequestParam Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null || userDetails.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        Member member = userDetails.getUser();
        BookMarkDto bookmarkDto = new BookMarkDto(meetingId, member.getId());

        boolean isBookmarked = bookMarkService.isBookmarked(bookmarkDto, member);
        return ResponseEntity.ok(isBookmarked);
    }
}
