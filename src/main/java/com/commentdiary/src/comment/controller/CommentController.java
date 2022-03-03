package com.commentdiary.src.comment.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.comment.dto.CreateCommentRequest;
import com.commentdiary.src.comment.dto.LikeResponse;
import com.commentdiary.src.comment.dto.MyCommentResponse;
import com.commentdiary.src.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public CommonResponse<Void> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        commentService.createComment(createCommentRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PatchMapping("/like/{commentId}")
    public CommonResponse<LikeResponse> like(@PathVariable(value = "commentId") long commentId) {
        LikeResponse likeResponse = commentService.like(commentId);
        return new CommonResponse<>(likeResponse);
    }

    @GetMapping("")
    public CommonResponse<List<MyCommentResponse>> getMyComment() {
        List<MyCommentResponse> result = commentService.getMyComment();
        return new CommonResponse<>(result);
    }
}
