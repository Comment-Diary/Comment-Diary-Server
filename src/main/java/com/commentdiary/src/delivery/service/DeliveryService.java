package com.commentdiary.src.delivery.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.delivery.domain.Delivery;
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
        Delivery delivery = deliveryRepository.findByReceiverIdAndDateContains(member.getId(), date).orElseThrow(() -> new CommonException(NOT_FOUND_DELIVERY));
        return DeliveryResponse.of(delivery.getDiary(), member);
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getMyMember() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }
}
