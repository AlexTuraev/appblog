package org.tasks.dao.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BlogRepositoryImpl implements BlogRepository {

    private final JdbcTemplate jdbcTemplate;

    public BlogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String FIND_COUNT_ALL_QUERY = "select count(*) from public.post";
    private final String FIND_COUNT_ALL_QUERY_LIKE_TAG = "select count(*) from public.post where tags like ?";

    private final String FIND_ALL_PAGING_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post offset ? limit ?";
    private final String FIND_ALL_PAGING_QUERY_LIKE_TAG = "select id, title, content, count_like, tags, image_type, image from public.post where tags like ? offset ? limit ?";

    private final String FIND_BY_ID_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post where id = ?";
    private final String SAVE_POST_QUERY = "insert into public.post (title, content, tags, image_type, image) values (?, ?, ?, ?, ?)";
    private final String DELETE_BY_ID_QUERY = "delete from public.post where id = ?";

    @Override
    public Integer getCountAll(String search) {
        return (search == null || search.isEmpty()) ?
                jdbcTemplate.queryForObject(FIND_COUNT_ALL_QUERY, Integer.class) :
                jdbcTemplate.queryForObject(FIND_COUNT_ALL_QUERY_LIKE_TAG, Integer.class, "%" + search + "%");
    }

    @Override
    public List<PostEntity> findAll(String search, int pageSize, int pageNumber) {
        return (search == null || search.isEmpty()) ?
                jdbcTemplate.query(FIND_ALL_PAGING_QUERY, rowMapperPostEntity, pageNumber*pageSize, pageSize) :
                jdbcTemplate.query(FIND_ALL_PAGING_QUERY_LIKE_TAG, rowMapperPostEntity, "%" + search + "%", pageNumber*pageSize, pageSize);
    }

    @Override
    public void save(PostEntity model) {
        jdbcTemplate.update(SAVE_POST_QUERY,
                model.getTitle(), model.getContent(), model.getTags(), model.getImageType(), model.getImage());
    }

    @Override
    public Optional<PostEntity> findById(long id) {
        List<PostEntity> entities = jdbcTemplate.query(FIND_BY_ID_QUERY, rowMapperPostEntity, id);
        return Optional.ofNullable(entities.getFirst());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
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