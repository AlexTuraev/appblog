package org.tasks.dao.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tasks.dao.repository.CommentRepository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String COUNT_COMMENT_BY_ID_QUERY= "select count(*) from public.comment where id = ?";

    @Override
    public Integer countCommentById(Long id) {
        return jdbcTemplate.query(COUNT_COMMENT_BY_ID_QUERY, (rs, rowNum) -> rs.getInt(1), id).getFirst();
    }

}
