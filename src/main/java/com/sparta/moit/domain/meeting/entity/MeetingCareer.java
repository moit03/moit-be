package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    @Builder
    public MeetingCareer(Meeting meeting, Career career) {
        this.meeting = meeting;
        this.career = career;
    }
}