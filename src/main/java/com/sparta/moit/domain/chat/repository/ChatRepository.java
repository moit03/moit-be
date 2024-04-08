package com.sparta.moit.domain.chat.repository;

import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByMeetingOrderById(Meeting meeting);
}
