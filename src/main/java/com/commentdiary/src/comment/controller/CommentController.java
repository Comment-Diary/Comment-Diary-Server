package com.commentdiary.src.comment.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.comment.dto.*;
import com.commentdiary.src.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public CommonResponse<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest createCommentRequest, Authentication authentication) {
        CreateCommentResponse result = commentService.createComment(createCommentRequest);
        log.info("[CommentController] createComment, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    @PatchMapping("/like/{commentId}")
    public CommonResponse<LikeResponse> like(@PathVariable(value = "commentId") long commentId, Authentication authentication) {
        LikeResponse result = commentService.like(commentId);
        log.info("[CommentController] like, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    @GetMapping("/all")
    public CommonResponse<List<MyCommentResponse>> getMyComment(Authentication authentication) {
        List<MyCommentResponse> result = commentService.getMyComment();
        log.info("[CommentController] getMyComment, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    @GetMapping("")
    public CommonResponse<List<MyCommentResponse>> getMyCommentByDate(@RequestParam(value = "date") String date, Authentication authentication) {
        List<MyCommentResponse> result = commentService.getMyCommentByDate(date);
        log.info("[CommentController] getMyCommentByDate, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

}
