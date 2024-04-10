package com.sparta.moit.domain.chat.dto;

import com.sparta.moit.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SenderResponseDto {

    private final Long memberId;
    private final String memberName;
    private final String memberEmail;

    @Builder
    public SenderResponseDto(Long memberId, String memberName, String memberEmail) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberEmail = memberEmail;

    }
    public static SenderResponseDto fromEntity(Member member) {
        return SenderResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getUsername())
                .memberName(member.getEmail())
                .build();
    }

}
