package com.commentdiary.src.comment.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.comment.dto.*;
import com.commentdiary.src.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public CommonResponse<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        CreateCommentResponse result = commentService.createComment(createCommentRequest);
        return new CommonResponse<>(result);
    }

    @PatchMapping("/like/{commentId}")
    public CommonResponse<LikeResponse> like(@PathVariable(value = "commentId") long commentId) {
        LikeResponse result = commentService.like(commentId);
        return new CommonResponse<>(result);
    }

    @GetMapping("")
    public CommonResponse<List<MyCommentResponse>> getMyComment() {
        List<MyCommentResponse> result = commentService.getMyComment();
        return new CommonResponse<>(result);
    }
}
