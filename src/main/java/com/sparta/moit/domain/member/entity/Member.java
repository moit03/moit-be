package com.sparta.moit.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.moit.domain.meeting.entity.MeetingMember;
import jakarta.persistence.*;
import lombok.*;

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

    //    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberStatusEnum status;

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column(name = "naver_id")
    private String naverId;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    public Member(String username, String password, String email, UserRoleEnum role, MemberStatusEnum status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    @Builder
    public Member(String username, String password, String email, UserRoleEnum role, MemberStatusEnum status, Long kakaoId, String naverId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public Member updateNaverId(String naverId) {
        this.naverId = naverId;
        return this;
    }

    public void signOutStatus() {
        this.status = MemberStatusEnum.NONMEMBER;
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