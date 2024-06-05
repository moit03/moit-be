package com.sparta.moit.domain.chat.repository;

import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {


}
