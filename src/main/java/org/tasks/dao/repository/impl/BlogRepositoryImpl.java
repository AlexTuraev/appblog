package org.tasks.dao.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;

import java.util.List;

@Repository
@Slf4j
public class BlogRepositoryImpl implements BlogRepository {

    private final JdbcTemplate jdbcTemplate;

    public BlogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String FIND_ALL_QUERY = "select id, title, content, count_like, tags, image from public.post";

    private final String SAVE_POST_QUERY = "insert into public.post (title, content, tags, image) values (?, ?, ?, ?)";

    @Override
    public List<PostEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY,
                (rs, rowNum) -> new PostEntity(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("count_like"),
                        rs.getString("tags"),
                        rs.getBytes("image")
                ));
    }

    @Override
    public void save(PostEntity model) {
        jdbcTemplate.update(SAVE_POST_QUERY,
                model.getTitle(), model.getContent(), model.getTags(), model.getImage());
    }

}