package com.sparta.moit.domain.member.repository;

import com.sparta.moit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByNaverId(String naverId);
}
