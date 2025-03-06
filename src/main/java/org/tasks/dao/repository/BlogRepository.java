package org.tasks.dao.repository;

import org.tasks.dao.model.PostEntity;

import java.util.List;

public interface BlogRepository {
    List<PostEntity> findAll();

    void save(PostEntity model);
}
