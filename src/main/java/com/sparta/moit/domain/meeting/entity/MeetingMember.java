package com.sparta.moit.domain.meeting.entity;

import com.sparta.moit.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Builder
    public MeetingMember(Member member, Meeting meeting) {
        this.member = member;
        this.meeting = meeting;
        setReferences();
    }

    private void setReferences() {
        meeting.addMeetingMember(this);
        member.addMeetingMember(this);
    }
}
