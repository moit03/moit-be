package com.sparta.moit.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.moit.domain.meeting.entity.MeetingMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
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
    @Column(name = "kakao_id")
    private Long kakaoId;
    @Column(name = "naver_id")
    private String naverId;

    private String refreshToken;  // 추가: 리프레시 토큰
    private Date refreshTokenExpiry;  // 추가: 리프레시 토큰 만료 시간

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<MeetingMember> meetingMembers = new ArrayList<>();


    public Member(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Builder
    public Member (Long id, String username, String password, String email, UserRoleEnum role, Long kakaoId, String naverId,
                   String refreshToken, Date refreshTokenExpiry) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
        this.refreshToken = refreshToken;  // 추가
        this.refreshTokenExpiry = refreshTokenExpiry;  // 추가
    }

//    @Builder
//    public Member (Long id, String username, String password, String email, UserRoleEnum role, Long kakaoId, String naverId){
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.kakaoId = kakaoId;
//        this.naverId = naverId;
//    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public Member updateNaverId(String naverId) {
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