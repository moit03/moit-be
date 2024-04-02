package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;

@Entity
public class MeetingSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meeting_skill_id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
}