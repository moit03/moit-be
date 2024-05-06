package com.sparta.moit.domain.meeting.scheduler;

import com.sparta.moit.domain.meeting.entity.Meeting;
import com.sparta.moit.domain.meeting.repository.MeetingRepository;
import com.sparta.moit.domain.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MeetingService meetingService;
    private final MeetingRepository meetingRepository;

//    @Scheduled(cron = "0 0 * * * *")/* 매시각 0분 */
    @Scheduled(cron = "0 */10 * * * *")
    @Transactional
    public void updateMeetingStatus() {
        /* STATUS = COMPLETE or DELETE 가 아닌 meetings 만 조회 */
        List<Meeting> meetingList = meetingRepository.findAllIncompleteMeetingsForHour();
        /* meeting  */
        for (Meeting meeting : meetingList) {
            meeting.completeStatus();
        }
    }
}
