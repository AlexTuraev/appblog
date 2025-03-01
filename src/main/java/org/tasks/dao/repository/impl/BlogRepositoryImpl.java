package org.tasks.dao.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;

import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
public class BlogRepositoryImpl implements BlogRepository {

    private final JdbcTemplate jdbcTemplate;

    public BlogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String FIND_ALL_QUERY = "select id, title, content, count_like from public.post";

    @Override
    public List<PostEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY,
                (rs, rowNum) -> new PostEntity(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("countLike")
                ));
    }

}