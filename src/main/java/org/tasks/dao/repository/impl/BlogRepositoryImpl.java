package org.tasks.dao.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BlogRepositoryImpl implements BlogRepository {

    private final JdbcTemplate jdbcTemplate;

    public BlogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String FIND_ALL_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post";
    private final String FIND_BYID_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post where id = ?";

    private final String SAVE_POST_QUERY = "insert into public.post (title, content, tags, image_type, image) values (?, ?, ?, ?, ?)";

    @Override
    public List<PostEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapperPostEntity);
    }

    @Override
    public void save(PostEntity model) {
        jdbcTemplate.update(SAVE_POST_QUERY,
                model.getTitle(), model.getContent(), model.getTags(), model.getImageType(), model.getImage());
    }

    @Override
    public Optional<PostEntity> findById(long id) {
        List<PostEntity> entities = jdbcTemplate.query(FIND_BYID_QUERY, rowMapperPostEntity, id);
        return Optional.ofNullable(entities.getFirst());
    }

    RowMapper<PostEntity> rowMapperPostEntity = (rs, rowNum) -> new PostEntity(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getInt("count_like"),
            rs.getString("tags"),
            rs.getString("image_type"),
            rs.getBytes("image")
    );

}