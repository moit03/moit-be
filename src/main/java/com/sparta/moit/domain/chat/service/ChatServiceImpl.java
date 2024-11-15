package com.sparta.moit.domain.chat.service;

import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.dto.SendChatRequestDto;
import com.sparta.moit.domain.chat.dto.SendChatResponseDto;
import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.chat.repository.ChatRepository;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.entity.MeetingStatusEnum;
import com.sparta.moit.domain.meeting.repository.MeetingMemberRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j(topic = "채팅 로그")
@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    public ChatResponseDto getChatList(Long meetingId, int page, LocalDateTime userEnterTime, Member member) {
        /*
         * 해당 모임이 존재하는지 확인한다.
         * 해당 모임에 가입한 유져가 맞는 지 확인한다.
         * 현재 참여한 채팅방의 채팅내역를 가져온다.
         * */
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND,  "meetingId : " + meetingId));

        if (meeting.getStatus().equals(MeetingStatusEnum.DELETE)) {
            throw new CustomException(ErrorCode.MEETING_NOT_FOUND, "meetingId : " + meetingId);
        }

        if (!isMeetingMember(member, meeting)) {
            throw new CustomException(ErrorCode.NOT_MEETING_MEMBER,"meetingId: "+ meetingId+ ", member: "+ member.getId());
        }

        int CHAT_PAGE_SIZE = 20;
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), CHAT_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));

        Slice<Chat> chatList = chatRepository.getPreviousChats(meeting, userEnterTime, pageable);

        return ChatResponseDto.fromEntity(chatList, meetingId, meeting.getStatus());
    }

    @Override
    public SendChatResponseDto sendChat(Long meetingId, String email, SendChatRequestDto sendChatRequestDto) {
        /*
         * 해당 모임이 존재하는지 확인한다.
         * 해당 모임에 가입한 유저가 맞는 지 확인한다.
         * 채팅을 DB 에 저장한다.
         * */

        Member testMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER, "userEmail: " + email));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND,  "meetingId : " + meetingId));

        if (meeting.getStatus().equals(MeetingStatusEnum.COMPLETE)){
            throw new CustomException(ErrorCode.MEETING_COMPLETE, "meeting status : COMPLETE, meetingId :" + meetingId);
        }

        if (!isMeetingMember(testMember, meeting)) {
            throw new CustomException(ErrorCode.NOT_MEETING_MEMBER,"meetingId: "+ meetingId+ ", member: "+ testMember.getId());
        }

        Chat chat = Chat.builder()
                .content(sendChatRequestDto.getContent())
                .member(testMember)
                .meeting(meeting)
//                .createdAt(LocalDateTime.now())
                .build();

        chatRepository.save(chat);

        return SendChatResponseDto.fromEntity(chat);
    }

    private boolean isMeetingMember(Member member, Meeting meeting) {
        return meetingMemberRepository.existsByMemberAndMeeting(member, meeting);
    }


}
