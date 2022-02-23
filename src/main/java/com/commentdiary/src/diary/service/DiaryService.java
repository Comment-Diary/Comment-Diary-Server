package com.commentdiary.src.diary.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.dto.CreateDiaryRequest;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commentdiary.common.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    public void createDiary(CreateDiaryRequest createDiaryRequest) {
        Long memberId = getMemberId();
        Diary diary = Diary.builder()
                .diaryDate(createDiaryRequest.getDiaryDate())
                .content(createDiaryRequest.getContent())
                .deliveryYn(createDiaryRequest.getDeliveryYn())
                .member(Member.of(memberId))
                .build();
        diaryRepository.save(diary);
    }



    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getCurrentMemberId() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(MEMBER_NOT_FOUND));
    }

}
