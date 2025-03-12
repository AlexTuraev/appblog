package org.tasks.dao.repository;

import org.tasks.dao.model.PostEntity;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Integer getCountAll(String search);

    List<PostEntity> findAll(String search, int pageSize, int pageNumber);

    void save(PostEntity model);

    Optional<PostEntity> findById(long id);

    void deleteById(long id);
}
