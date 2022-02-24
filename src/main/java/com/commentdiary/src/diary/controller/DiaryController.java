package com.commentdiary.src.diary.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.diary.dto.CreateDiaryRequest;
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

    @GetMapping("/search")
    public CommonResponse<List<DiaryResponse>> getDiaryByDate(@RequestParam(value = "date") String date) {
        return new CommonResponse<>(diaryService.getDiaryByDate(date));
    }

}

