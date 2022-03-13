package com.commentdiary.src.diary.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.diary.dto.CreateDiaryRequest;
import com.commentdiary.src.diary.dto.CreateDiaryResponse;
import com.commentdiary.src.diary.dto.DiaryDetailResponse;
import com.commentdiary.src.diary.dto.DiaryResponse;
import com.commentdiary.src.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CommonResponse<CreateDiaryResponse> createDiary(@RequestBody CreateDiaryRequest createDiaryRequest) {
        CreateDiaryResponse result = diaryService.createDiary(createDiaryRequest);
        return new CommonResponse<>(result);
    }

    /**
     * 일기 수정
     */
    @PatchMapping("/{diaryId}")
    public CommonResponse<Void> updateDiary(@PathVariable(value = "diaryId") long diaryId, @RequestBody CreateDiaryRequest createDiaryRequest) {
        diaryService.updateDiary(diaryId, createDiaryRequest);
        return new CommonResponse<>(SUCCESS);
    }

    /**
     * 일기 삭제
     */
    @DeleteMapping("/{diaryId}")
    public CommonResponse<Void> deleteDiary(@PathVariable(value = "diaryId") long diaryId) {
        diaryService.deleteDiary(diaryId);
        return new CommonResponse<>(SUCCESS);
    }

    /**
     * 일기 메인 페이지 조회
     * 특정 달의 일기 id, 제목, 내용, 코멘트 리턴
     */
    @GetMapping("/main")
    public CommonResponse<List<DiaryDetailResponse>> getAllMainDiary(@RequestParam(value = "date") String date) {
        List<DiaryDetailResponse> result = diaryService.getAllMainDiary(date);
        return new CommonResponse<>(result);
    }

    /**
     * 일기 하나 조회
     * 특정 일자의 일기 id, 제목, 내용, 코멘트 리턴
     */
    @GetMapping("/{diaryId}")
    public CommonResponse<DiaryDetailResponse> getOneDiary(@PathVariable(value = "diaryId") long diaryId) {
        DiaryDetailResponse result = diaryService.getOneDiary(diaryId);
        return new CommonResponse<>(result);
    }

    /**
     * 일기 모아보기 (검색 필터: 전체)
     * 내가 쓴 일기 전체 id, 제목, 내용, 코멘트 개수 리턴
     */
    @GetMapping("/my/all")
    public CommonResponse<List<DiaryResponse>> getDiaryByAllDate() {
        return new CommonResponse<>(diaryService.getDiaryByAllDate());
    }

    /**
     * 일기 모아보기 (검색 필터: 특정 달)
     * 특정 달 일기의 id, 제목, 내용, 코멘트 개수
     */
    @GetMapping("/my")
    public CommonResponse<List<DiaryResponse>> getDiaryByDate(@RequestParam(value = "date") String date) {
        return new CommonResponse<>(diaryService.getDiaryByDate(date));
    }

}

