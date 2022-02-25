package com.commentdiary.src.diary.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.diary.dto.CreateDiaryRequest;
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

    @PostMapping("")
    public CommonResponse<Void> createDiary(@RequestBody CreateDiaryRequest createDiaryRequest) {
        diaryService.createDiary(createDiaryRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PatchMapping("/{diaryId}")
    public CommonResponse<Void> updateDiary(@PathVariable(value = "diaryId") long diaryId, @RequestBody CreateDiaryRequest createDiaryRequest) {
        diaryService.updateDiary(diaryId, createDiaryRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @DeleteMapping("/{diaryId}")
    public CommonResponse<Void> deleteDiary(@PathVariable(value = "diaryId") long diaryId) {
        diaryService.deleteDiary(diaryId);
        return new CommonResponse<>(SUCCESS);
    }

    @GetMapping("/main")
    public CommonResponse<List<DiaryDetailResponse>> getDiary(@RequestParam(value = "date") String date) {
        return new CommonResponse<>(diaryService.getDiary(date));
    }

    @GetMapping("/{diaryId}")
    public CommonResponse<DiaryDetailResponse> getDetailDiary(@PathVariable(value = "diaryId") long diaryId) {
        DiaryDetailResponse result = diaryService.getDetailDiary(diaryId);
        return new CommonResponse<>(result);
    }

    @GetMapping("/my/all")
    public CommonResponse<List<DiaryResponse>> getDiaryByAllDate() {
        return new CommonResponse<>(diaryService.getAllDiary());
    }

    @GetMapping("/my")
    public CommonResponse<List<DiaryResponse>> getDiaryByDate(@RequestParam(value = "date") String date) {
        return new CommonResponse<>(diaryService.getDiaryByDate(date));
    }


}

