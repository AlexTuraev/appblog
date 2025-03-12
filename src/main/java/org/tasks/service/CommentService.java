package org.tasks.service;

import org.tasks.dto.CommentDto;

import java.util.List;

public interface CommentService {

    Integer getCountCommentById(Long postId);

    void addComment(CommentDto commentDto);

    List<CommentDto> findByPostId(Long postId);
}
