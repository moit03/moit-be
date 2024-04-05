package com.sparta.moit.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
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
    private Long naverId;

    public Member(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Builder
    public Member (String username, String password, String email, UserRoleEnum role, Long kakaoId, Long naverId){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
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
}