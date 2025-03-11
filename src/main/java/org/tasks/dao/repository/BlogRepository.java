package org.tasks.dao.repository;

import org.tasks.dao.model.PostEntity;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    List<PostEntity> findAll();

    void save(PostEntity model);

    Optional<PostEntity> findById(long id);
}
