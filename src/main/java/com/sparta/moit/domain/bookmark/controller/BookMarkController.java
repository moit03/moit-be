package com.sparta.moit.domain.bookmark.controller;

import com.sparta.moit.domain.bookmark.controller.docs.BookMarkControllerDocs;
import com.sparta.moit.domain.bookmark.dto.BookMarkRequestDto;
import com.sparta.moit.domain.bookmark.dto.BookMarkResponseDto;
import com.sparta.moit.domain.bookmark.dto.GetBookMarkResponseDto;
import com.sparta.moit.domain.bookmark.entity.BookMark;
import com.sparta.moit.domain.bookmark.repository.BookMarkRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookMarkController implements BookMarkControllerDocs {
    private final BookMarkService bookMarkService;
    private final MemberRepository memberRepository;
    private final BookMarkRepository bookMarkRepository;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<BookMarkResponseDto>> addMeetingBookmark(@RequestBody BookMarkRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        BookMarkResponseDto bookmarkResponseDto = new BookMarkResponseDto(requestDto.getMeetingId(), member.getId());

        bookMarkService.addMeetingBookmark(bookmarkResponseDto, member);
        return ResponseEntity.ok().body(ResponseDto.success("북마크 완료", bookmarkResponseDto));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDto<BookMarkResponseDto>> removeMeetingBookmark(@RequestBody BookMarkRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        BookMarkResponseDto bookmarkResponseDto = new BookMarkResponseDto(requestDto.getMeetingId(), member.getId());

        bookMarkService.removeMeetingBookmark(bookmarkResponseDto, member);
        return ResponseEntity.ok().body(ResponseDto.success("북마크 해제", bookmarkResponseDto));
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
        BookMarkResponseDto bookmarkResponseDto = new BookMarkResponseDto(meetingId, member.getId());

        boolean isBookmarked = bookMarkService.isBookmarked(bookmarkResponseDto, member);
        return ResponseEntity.ok(isBookmarked);
    }

    /**
     *
     * @param userDetails 현재 인증된 회원 정보
     * @return 즐겨찾기에 있으면 true, 없으면 false
     */
    @GetMapping("/checkByMemberId")
    public ResponseEntity<ResponseDto<GetBookMarkResponseDto>> isBookmarkedByMemberId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getUser().getId();

        try {
            List<BookMark> bookmarks = bookMarkRepository.findByMemberId(memberId);

            List<Long> bookmarkedMeetingIds = bookmarks.stream()
                    .map(bookmark -> bookmark.getMeeting().getId())
                    .toList();

            GetBookMarkResponseDto responseDto = new GetBookMarkResponseDto(bookmarkedMeetingIds);

            return ResponseEntity.ok().body(ResponseDto.success("유저가 북마크한 모임 조회 완료", responseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail("북마크 조회 실패", null));
        }
    }
}
