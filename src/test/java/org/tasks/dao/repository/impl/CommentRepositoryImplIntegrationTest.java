package org.tasks.dao.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tasks.config.DataSourceTestConfig;
import org.tasks.dao.model.CommentEntity;
import org.tasks.dao.repository.CommentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {DataSourceTestConfig.class, CommentRepositoryImpl.class})
@TestPropertySource("classpath:application-test.properties")
class CommentRepositoryImplIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("delete from comment");

        jdbcTemplate.execute("""
                insert into comment(content, post_id) values ('Комментарий 1_1', 1);
                insert into comment(content, post_id) values ('Комментарий 1_2', 1);
                insert into comment(content, post_id) values ('Комментарий 1_3', 1);
                insert into comment(content, post_id) values ('Комментарий 2_1', 2);
                insert into comment(content, post_id) values ('Комментарий 2_2', 2);
                insert into comment(content, post_id) values ('Комментарий 2_3', 2);
                insert into comment(content, post_id) values ('Комментарий 2_4', 2);
        """);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from comment");
    }

    @Test
    void countCommentByPostId() {
        int actual = commentRepository.countCommentByPostId(1L);
        assertEquals(3, actual);

        actual = commentRepository.countCommentByPostId(2L);
        assertEquals(4, actual);

        actual = commentRepository.countCommentByPostId(3L);
        assertEquals(0, actual);
    }

    @Test
    void save() {
    }

    @Test
    void findByPostId() {
        List<CommentEntity> actual = commentRepository.findByPostId(3L);
        assertTrue(actual.isEmpty());

        actual = commentRepository.findByPostId(1L);
        assertEquals(3, actual.size());

        assertEquals(1L, actual.get(0).getPostId());
        assertEquals(1L, actual.get(1).getPostId());
        assertEquals(1L, actual.get(2).getPostId());

    }

    @Test
    void deleteById() {
        List<CommentEntity> comments = commentRepository.findByPostId(1L);
        assertFalse(comments.isEmpty());
        long id = comments.getFirst().getId();

        commentRepository.deleteById(id);
        comments = commentRepository.findByPostId(1L);
        assertEquals(2, comments.size());
    }

    @Test
    void updateById() {
        List<CommentEntity> comments = commentRepository.findByPostId(2L);
        assertFalse(comments.isEmpty());

        long id = comments.getFirst().getId();
        final String NEW_CONTENT = "new content";

        commentRepository.updateById(id, NEW_CONTENT);

        comments = commentRepository.findByPostId(2L).stream()
                .filter(comment -> comment.getContent().equals(NEW_CONTENT))
                .toList();
        assertEquals(1, comments.size());
        assertEquals(2L, comments.getFirst().getPostId());
    }
}