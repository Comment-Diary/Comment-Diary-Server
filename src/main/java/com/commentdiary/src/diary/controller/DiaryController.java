package com.commentdiary.src.diary.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.diary.dto.CreateDiaryRequest;
import com.commentdiary.src.diary.dto.CreateDiaryResponse;
import com.commentdiary.src.diary.dto.DiaryDetailResponse;
import com.commentdiary.src.diary.dto.DiaryResponse;
import com.commentdiary.src.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * 일기 작성
     */
    @PostMapping("")
    public CommonResponse<CreateDiaryResponse> createDiary(@RequestBody CreateDiaryRequest createDiaryRequest, Authentication authentication) {
        CreateDiaryResponse result = diaryService.createDiary(createDiaryRequest);
        log.info("[DiaryController] createDiary, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    /**
     * 일기 수정
     */
    @PatchMapping("/{diaryId}")
    public CommonResponse<Void> updateDiary(@PathVariable(value = "diaryId") long diaryId, @RequestBody CreateDiaryRequest createDiaryRequest, Authentication authentication) {
        diaryService.updateDiary(diaryId, createDiaryRequest);
        log.info("[DiaryController] updateDiary, id : {}", authentication.getName());
        return new CommonResponse<>(SUCCESS);
    }

    /**
     * 일기 삭제
     */
    @DeleteMapping("/{diaryId}")
    public CommonResponse<Void> deleteDiary(@PathVariable(value = "diaryId") long diaryId, Authentication authentication) {
        diaryService.deleteDiary(diaryId);
        log.info("[DiaryController] deleteDiary, id : {}", authentication.getName());
        return new CommonResponse<>(SUCCESS);
    }

    /**
     * 일기 메인 페이지 조회
     */
    @GetMapping("/main")
    public CommonResponse<List<DiaryDetailResponse>> getAllMainDiary(@RequestParam(value = "date") String date, Authentication authentication) {
        List<DiaryDetailResponse> result = diaryService.getAllMainDiary(date);
        log.info("[DiaryController] getAllMainDiary, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    /**
     * 일기 하나 조회
     * 특정 일자의 일기 id, 제목, 내용, 코멘트 리턴
     */
    @GetMapping("/{diaryId}")
    public CommonResponse<DiaryDetailResponse> getOneDiary(@PathVariable(value = "diaryId") long diaryId, Authentication authentication) {
        DiaryDetailResponse result = diaryService.getOneDiary(diaryId);
        log.info("[DiaryController] getOneDiary, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    /**
     * 일기 모아보기 (검색 필터: 전체)
     */
    @GetMapping("/my/all")
    public CommonResponse<List<DiaryResponse>> getDiaryByAllDate(Authentication authentication) {
        log.info("[DiaryController] getDiaryByAllDate, id : {}", authentication.getName());
        return new CommonResponse<>(diaryService.getDiaryByAllDate());
    }

    /**
     * 일기 모아보기 (검색 필터: 특정 달)
     */
    @GetMapping("/my")
    public CommonResponse<List<DiaryResponse>> getDiaryByDate(@RequestParam(value = "date") String date, Authentication authentication) {
        log.info("[DiaryController] getDiaryByDate, id : {}", authentication.getName());
        return new CommonResponse<>(diaryService.getDiaryByDate(date));
    }

}

