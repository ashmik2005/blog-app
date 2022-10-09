package com.jbdl.blogapp.service;

import com.jbdl.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> findAllComments(Long postId);

    CommentDto getComment(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);

    void validateRequest(Long postId, Long commentId);

}
