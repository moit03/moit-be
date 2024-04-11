package com.sparta.moit.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.moit.domain.meeting.entity.MeetingMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    private Long kakaoId;
    private Long naverId;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<MeetingMember> meetingMembers = new ArrayList<>();


    public Member(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member(String username, String password, String email, UserRoleEnum role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public Member updateNaverId(Long naverId) {
        this.naverId = naverId;
        return this;
    }

    /* 테스트용 */
    public void setUsername(String username) {
    }

    public void setRole(UserRoleEnum userRoleEnum) {
    }

    public void addMeetingMember(MeetingMember meetingMember) {
        this.meetingMembers.add(meetingMember);
    }
}