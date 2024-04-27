package com.sparta.moit.domain.chat.repository;

import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

public interface ChatRepositoryCustom {
    Slice<Chat> getPreviousChats(Meeting meeting, LocalDateTime userEnterTime, Pageable pageable);
}
