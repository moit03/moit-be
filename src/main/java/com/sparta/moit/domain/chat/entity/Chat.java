package com.sparta.moit.domain.chat.entity;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.global.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=10000)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    private Meeting meeting;

    @Builder
    public Chat(String content, Member member, Meeting meeting) {
        this.content = content;
        this.member = member;
        this.meeting = meeting;
    }
}
