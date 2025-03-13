package org.tasks.test.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tasks.dao.repository.CommentRepository;
import org.tasks.service.CommentService;
import org.tasks.service.impl.CommentServiceImpl;
import org.tasks.service.mapping.CommentMapper;
import org.tasks.test.service.mapper.CommentTestMapper;

@Configuration
public class CommentServiceTestConfig {

    @Bean
    public CommentMapper commentMapper() {
        return new CommentTestMapper();
    }

    @Bean
    public CommentRepository mockCommentRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    public CommentService commentService(CommentRepository mockCommentRepository, CommentMapper commentMapper) {
        return new CommentServiceImpl(mockCommentRepository, commentMapper);
    }

}
