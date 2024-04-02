package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name="skill")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Skill {
    @Column(name = "skill_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(name = "skill_name")
    private String skillName;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MeetingSkill> meetings = new HashSet<>();

    @Builder
    public Skill(String skillName) {
        this.skillName = skillName;
    }
}
