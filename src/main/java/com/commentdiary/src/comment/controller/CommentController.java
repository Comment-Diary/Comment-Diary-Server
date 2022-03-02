package com.commentdiary.src.comment.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.comment.dto.CommentRequest;
import com.commentdiary.src.comment.dto.LikeResponse;
import com.commentdiary.src.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public CommonResponse<Void> createComment(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PatchMapping("/like/{commentId}")
    public CommonResponse<LikeResponse> like(@PathVariable(value = "commentId") long commentId) {
        LikeResponse likeResponse = commentService.like(commentId);
        return new CommonResponse<>(likeResponse);
    }

    @GetMapping("")
    public CommonResponse<Void> getMyComment() {
        return new CommonResponse<>(SUCCESS);
    }
}
