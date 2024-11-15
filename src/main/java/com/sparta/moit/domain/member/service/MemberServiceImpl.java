package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final KakaoService kakaoService;

    /*회원 탈퇴*/
    @Override
    @Transactional
    public void signOut(Member member) {

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER, "memberId: " +member.getId()));

        /*카카오 unlink*/
        if (member1.getKakaoId() != null) {
            try {
                kakaoService.signOut(member1);
            } catch (JsonProcessingException e) {
                throw new CustomException(ErrorCode.KAKAOID_UNLINK_FAILURE, "memberId: ," + member1.getId()+ " kakaoId: "+ member1.getKakaoId());
            }
        }
        member1.signOutStatus();
    }
}
