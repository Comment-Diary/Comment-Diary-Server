package com.commentdiary.common.schedule;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.delivery.dto.DeliveryRequest;
import com.commentdiary.src.delivery.repository.DeliveryRepository;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.commentdiary.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@Slf4j
@RequiredArgsConstructor
public class Scheduler {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;

    @Scheduled(cron = "0 0 7 * * *")
    @Transactional
    public void deliveryDiary() {
        DeliveryRequest deliveryRequest = new DeliveryRequest();

        log.info("start delivery");
        Date today = new Date();
        Date yesterday = new Date(today.getTime()+(1000*60*60*24*-1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

        // 어제 일기를 찾아와서, 오늘 전달하는데, 전달 테이블에는 오늘 날짜를 저장해야 됨. 클라에서 오늘 날짜가 들어온다.
        List<Member> members = memberRepository.findAll();
        List<Diary> diaries = diaryRepository.findAllByDeliveryDiaries(simpleDateFormat.format(yesterday));

        int idx = 0;

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId() == diaries.get(idx).getMember().getId()) {
                idx = (idx + 1) % diaries.size();
                if(diaries.size() == 1) {
                    continue;
                }
            }
            deliveryRepository.save(deliveryRequest.toEntity(members.get(i), diaries.get(idx), simpleDateFormat.format(today)));
            idx = (idx + 1) % diaries.size();
        }
    }
}