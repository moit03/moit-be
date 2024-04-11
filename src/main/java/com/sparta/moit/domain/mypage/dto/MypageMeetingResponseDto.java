package com.sparta.moit.domain.mypage.dto;

import com.sparta.moit.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class MypageMeetingResponseDto {
    private Long meetingId;
    private String meetingName;

    @Builder
    public MypageMeetingResponseDto(Long meetingId, String meetingName) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
    }

    public static MypageMeetingResponseDto fromEntity(Meeting meeting){
        return MypageMeetingResponseDto.builder()
                .meetingId(meeting.getId())
                .meetingName(meeting.getMeetingName())
                .build();
    }
}
