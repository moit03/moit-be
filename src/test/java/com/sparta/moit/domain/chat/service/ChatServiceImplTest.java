//package com.sparta.moit.domain.chat.service;
//
//import com.sparta.moit.domain.chat.dto.ChatResponseDto;
//import com.sparta.moit.domain.chat.dto.SingleChatResponseDto;
//import com.sparta.moit.domain.chat.entity.Chat;
//import com.sparta.moit.domain.chat.repository.ChatRepository;
//import com.sparta.moit.domain.meeting.entity.Meeting;
//import com.sparta.moit.domain.meeting.repository.MeetingMemberRepository;
//import com.sparta.moit.domain.meeting.repository.MeetingRepository;
//import com.sparta.moit.domain.member.entity.Member;
//import com.sparta.moit.domain.member.entity.UserRoleEnum;
//import com.sparta.moit.global.error.CustomException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//@ExtendWith(MockitoExtension.class)
//class ChatServiceImplTest {
//
//    @Mock
//    private ChatRepository chatRepository;
//    @Mock
//    private MeetingRepository meetingRepository;
//    @Mock
//    private MeetingMemberRepository meetingMemberRepository;
//    @InjectMocks
//    private ChatServiceImpl chatService;
//
//
//    @Test
//    @DisplayName("채팅 내역 가져오기 - 성공")
//    void getChatList() {
//        // given
//        Member member1 = new Member("test_username", "test_password", "email@email.com", UserRoleEnum.USER);
//        Member member2 = new Member("test_username2", "test_password2", "email2@email.com", UserRoleEnum.USER);
//        Long meetingId = 1L;
//        Meeting meeting = Meeting.builder()
//                .id(meetingId)
//                .build();
//        Chat chat1 = Chat.builder()
//                .content("메시지 입니다.")
//                .member(member1)
//                .meeting(meeting)
//                .build();
//        Chat chat2 = Chat.builder()
//                .content("메시지 입니다.2")
//                .member(member2)
//                .meeting(meeting)
//                .build();
//
//        List<Chat> chatList = new ArrayList<>() {{
//            add(chat1);
//            add(chat2);
//        }};
//
//        Mockito.when(meetingRepository.findById(meetingId)).thenReturn(Optional.ofNullable(meeting));
//        Mockito.when(meetingMemberRepository.existsByMemberAndMeeting(member1, meeting)).thenReturn(Boolean.TRUE);
//        Mockito.when(chatRepository.findAllByMeetingOrderById(meeting)).thenReturn(chatList);
//
//        // when
//        ChatResponseDto chatResponseDto = chatService.getChatList(member1, meetingId);
//
//        // then
//        assertEquals(meetingId, chatResponseDto.getMeetingId());
//        List<SingleChatResponseDto> resultChatList = chatResponseDto.getChats();
//
//        assertEquals(chatList.size(), resultChatList.size());
//
//
//    }
//    @Test
//    @DisplayName("채팅 내역 가져오기 - 실패")
//    void MEETING_NOT_FOUND_failure_getChatList() {
//        // given
//        Member member1 = new Member("test_username", "test_password", "email@email.com", UserRoleEnum.USER);
//        Long meetingId = 1L;
//
//        Mockito.when(meetingRepository.findById(meetingId)).thenReturn(Optional.ofNullable(null));
//
//        // when
//        Exception exception = assertThrows(CustomException.class, () -> {
//            chatService.getChatList(member1, meetingId);
//        });
//
//        //then
//        assertEquals("모임을 찾을 수 없습니다", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("채팅 내역 가져오기 - 실패")
//    void NOT_MEETING_MEMBER_failure_getChatList() {
//        Member member1 = new Member("test_username", "test_password", "email@email.com", UserRoleEnum.USER);
//        Long meetingId = 1L;
//        Meeting meeting = Meeting.builder()
//                .id(meetingId)
//                .build();
//
//        Mockito.when(meetingRepository.findById(meetingId)).thenReturn(Optional.ofNullable(meeting));
//        Mockito.when(meetingMemberRepository.existsByMemberAndMeeting(member1, meeting)).thenReturn(Boolean.FALSE);
//
//        // when
//        Exception exception = assertThrows(CustomException.class, () -> {
//            chatService.getChatList(member1, meetingId);
//        });
//
//        //then
//        assertEquals("모임에 가입한 유저가 아닙니다.", exception.getMessage());
//    }
//
//
//}