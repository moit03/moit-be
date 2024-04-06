package com.sparta.moit.domain.chat.service;

import com.sparta.moit.domain.chat.dto.ChatResponseDto;
import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.chat.repository.ChatRepository;
import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.repository.MeetingMemberRepository;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository meetingMemberRepository;

    @Override
    public ChatResponseDto getChatList(Member member, Long meetingId) {
        /*
         * 해당 모임이 존재하는지 확인한다.
         * 해당 모임에 가입한 유져가 맞는 지 확인한다.
         * 채팅 리스트를 가져온다.
         * */
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));

        Boolean isMeetingMember = meetingMemberRepository.existsByMemberAndMeeting(member, meeting);

        if (!isMeetingMember) {
            throw new CustomException(ErrorCode.NOT_MEETING_MEMBER);
        }

        List<Chat> chatList = chatRepository.findAllByMeetingOrderById(meeting);

        return ChatResponseDto.fromEntity(chatList);
    }
}
