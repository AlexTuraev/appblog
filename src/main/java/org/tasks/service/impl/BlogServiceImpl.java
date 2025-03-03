package org.tasks.service.impl;

import org.springframework.stereotype.Service;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;
import org.tasks.service.mapping.PostMapping;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final PostMapping mapper;

    public BlogServiceImpl(BlogRepository blogRepository, PostMapping mapper) {
        this.blogRepository = blogRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<PostEntity> entities = blogRepository.findAll();
        return mapper.toDto(entities);
//        Optional<PostEntity> entities = blogRepository.findById(1L);


    }
}
