package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name="career")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Career {
    @Column(name = "career_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @Column(name = "meeting_name")
    private String careerName;

    @OneToMany(mappedBy = "career")
    private Set<MeetingCareer> meetingCareerSet = new HashSet<>();

    @Builder
    public Career(String careerName) {
        this.careerName = careerName;
    }
}
