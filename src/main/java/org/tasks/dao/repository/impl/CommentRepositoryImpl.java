package org.tasks.dao.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tasks.dao.model.CommentEntity;
import org.tasks.dao.repository.CommentRepository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String COUNT_COMMENT_BY_ID_QUERY= "select count(*) from public.comment where post_id = ?";

    private final String SAVE_COMMENT_QUERY = "insert into public.comment (content, post_id) values (?, ?)";

    private final String FIND_BY_POST_ID_QUERY= "select id, content, post_id from public.comment where post_id = ?";

    @Override
    public Integer countCommentById(Long postId) {
        return jdbcTemplate.query(COUNT_COMMENT_BY_ID_QUERY, (rs, rowNum) -> rs.getInt(1), postId).getFirst();
    }

    @Override
    public void save(CommentEntity entity) {
        jdbcTemplate.update(SAVE_COMMENT_QUERY, entity.getContent(), entity.getPostId());
    }

    @Override
    public List<CommentEntity> findByPostId(Long postId) {
        return jdbcTemplate.query(FIND_BY_POST_ID_QUERY,
                (rs, rowNum) -> new CommentEntity(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getLong("post_id")
                ),
                postId);
    }

}
