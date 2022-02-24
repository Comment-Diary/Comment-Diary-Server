package com.commentdiary.src.diary.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.dto.CreateDiaryRequest;
import com.commentdiary.src.diary.dto.DiaryResponse;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.commentdiary.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.commentdiary.common.exception.ErrorCode.NOT_FOUND_DIARY;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    public void createDiary(CreateDiaryRequest createDiaryRequest) {
        Member member = getCurrentMemberId();
        Diary diary = diaryRepository.save(createDiaryRequest.toEntity(member, createDiaryRequest));
    }

    public void updateDiary(Long diaryId, CreateDiaryRequest createDiaryRequest) {
        Member member = getCurrentMemberId();
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new CommonException(NOT_FOUND_DIARY));
        diary.updateDiary(createDiaryRequest.getTitle(), createDiaryRequest.getContent());

    }

    public List<DiaryResponse> getDiaryByDate(String date) {
        Long memberId = getMemberId();
        List<Diary> diaryList = diaryRepository.findByMemberIdAndDateContains(memberId, date);

        return diaryList.stream()
                .map(diary -> DiaryResponse.of(diary))
                .collect(Collectors.toList());
    }


    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getCurrentMemberId() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }

}
