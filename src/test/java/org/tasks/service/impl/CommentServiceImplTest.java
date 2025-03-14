package org.tasks.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tasks.dao.model.CommentEntity;
import org.tasks.dao.repository.CommentRepository;
import org.tasks.dto.CommentDto;
import org.tasks.config.CommentServiceTestConfig;
import org.tasks.service.CommentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = CommentServiceTestConfig.class)
class CommentServiceImplTest {

    @Autowired
    private CommentRepository mockCommentRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
    }

    private final long POST_ID = 1L;

    @Test
    void getCountCommentById() {
        when(mockCommentRepository.countCommentById(1L)).thenReturn(10);
        int actual = commentService.getCountCommentById(1L);
        assertEquals(10, actual);
    }

    @Test
    void findByPostId() {
        List<CommentEntity> comments = List.of(
                new CommentEntity(2L, "comment1", POST_ID),
                new CommentEntity(5L, "comment2", POST_ID),
                new CommentEntity(10L, "comment3", POST_ID)
        );

        List<CommentDto> expectedDtos = List.of(
                new CommentDto(2L, "comment1", POST_ID),
                new CommentDto(5L, "comment2", POST_ID),
                new CommentDto(10L, "comment3", POST_ID)
        );

        when(mockCommentRepository.findByPostId(POST_ID)).thenReturn(comments);

        List<CommentDto> actualDtos = commentService.findByPostId(POST_ID);
        assertEquals(3, actualDtos.size());
        assertEquals(expectedDtos, actualDtos);
    }
}