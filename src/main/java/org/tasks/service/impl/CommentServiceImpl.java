package org.tasks.service.impl;

import org.springframework.stereotype.Service;
import org.tasks.dao.repository.CommentRepository;
import org.tasks.dto.CommentDto;
import org.tasks.service.CommentService;
import org.tasks.service.mapping.CommentMapper;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Integer getCountCommentById(Long postId) {
        return repository.countCommentById(postId);
    }

    @Override
    public void addComment(CommentDto commentDto) {
        repository.save(mapper.toEntity(commentDto));
    }

    @Override
    public List<CommentDto> findByPostId(Long postId) {
        return repository.findByPostId(postId)
                .stream().map(mapper::toDto).toList();
    }
}
