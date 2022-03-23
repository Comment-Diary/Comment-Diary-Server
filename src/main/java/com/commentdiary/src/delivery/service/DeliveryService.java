package com.commentdiary.src.delivery.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.delivery.domain.Delivery;
import com.commentdiary.src.delivery.domain.enums.DeliveryStatus;
import com.commentdiary.src.delivery.dto.DeliveryRequest;
import com.commentdiary.src.delivery.dto.DeliveryResponse;
import com.commentdiary.src.delivery.repository.DeliveryRepository;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.commentdiary.common.exception.ErrorCode.NOT_FOUND_DELIVERY;
import static com.commentdiary.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryResponse getDeliveredDiary(String date) {
        Member member = getMyMember();

        Delivery delivery = deliveryRepository.findByReceiverIdAndDateContains(member.getId(), date)
                .filter(d -> d.getStatus().equals(DeliveryStatus.ACTIVE))
                .orElseThrow(() -> new CommonException(NOT_FOUND_DELIVERY));
        return DeliveryResponse.of(delivery.getDiary(), member);
    }

    @Transactional
    public void delivery() {
        DeliveryRequest deliveryRequest = new DeliveryRequest();

        Date today = new Date();
        Date yesterday = new Date(today.getTime()+(1000*60*60*24*-1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

        // 어제 일기를 찾아와서, 오늘 전달하는데, 전달 테이블에는 오늘 날짜를 저장해야 됨. 클라에서 오늘 날짜가 들어온다.
        List<Member> members = memberRepository.findAll();
        List<Diary> diaries = diaryRepository.findAllByDeliveryYnIsAndDateContainsAndMemberIsNotNullAndTempYnEquals('Y', simpleDateFormat.format(yesterday), 'N');

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

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getMyMember() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }
}
