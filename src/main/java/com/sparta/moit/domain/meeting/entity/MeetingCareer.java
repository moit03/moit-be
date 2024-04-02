package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;

@Entity
public class MeetingCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meeting_career_id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", referencedColumnName = "meeting_id", nullable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "career_id", referencedColumnName = "career_id", nullable = false)
    private Career career;
}