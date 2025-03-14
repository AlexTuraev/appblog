package org.tasks.dao.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tasks.config.DataSourceTestConfig;
import org.tasks.dao.repository.CommentRepository;

//import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig(classes = {DataSourceTestConfig.class, CommentRepositoryImpl.class})
@TestPropertySource("classpath:application-test.properties")
class CommentRepositoryImplIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void countCommentById() {
    }

    @Test
    void save() {
    }

    @Test
    void findByPostId() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateById() {
    }
}