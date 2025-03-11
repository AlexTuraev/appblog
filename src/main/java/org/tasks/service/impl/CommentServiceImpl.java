package org.tasks.service.impl;

import org.springframework.stereotype.Service;
import org.tasks.dao.repository.CommentRepository;
import org.tasks.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer getCountCommentById(Long id) {
        return repository.countCommentById(id);
    }
}
