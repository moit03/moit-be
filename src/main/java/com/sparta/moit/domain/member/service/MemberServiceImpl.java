package com.sparta.moit.domain.member.service;

import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /*회원 탈퇴*/
    @Override
    @Transactional
    public void signOut(Member member) {

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. memberId= " + member));

        member.signOutStatus();
    }
}
