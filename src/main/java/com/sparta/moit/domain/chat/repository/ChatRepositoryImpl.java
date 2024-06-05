package com.sparta.moit.domain.chat.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moit.domain.chat.entity.Chat;
import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.moit.domain.chat.entity.QChat.chat;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public Slice<Chat> getPreviousChats(Meeting meeting, LocalDateTime userEnterTime, Pageable pageable) {

        BooleanExpression createdBefore = chat.createdAt.before(userEnterTime);
        BooleanExpression isCorrectMeeting = chat.meeting.eq(meeting);

        List<Chat> chatList = queryFactory
                .selectFrom(chat)
                .where(
                        createdBefore,
                        isCorrectMeeting
                )
                .orderBy(chat.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (chatList.size() > pageable.getPageSize()) {
            chatList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(chatList, pageable, hasNext);
    }
}
