package org.tasks.service;

import org.tasks.dto.CommentDto;

import java.util.List;

public interface CommentService {

    Integer getCountCommentByPostId(Long postId);

    void addComment(CommentDto commentDto);

    List<CommentDto> findByPostId(Long postId);

    void deleteById(long id);

    void updateById(long id, String content);
}
