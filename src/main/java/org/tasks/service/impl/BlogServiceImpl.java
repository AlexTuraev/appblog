package org.tasks.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;
import org.tasks.service.mapping.PostMapping;

import java.io.IOException;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Value("${spring.servlet.multipart.max-file-size:10Mb}")
    private int maxFileSize;

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
    }

    @Override
    public void save(PostDto postDto, MultipartFile file) throws IOException {
        if (isFileSizeValid(file)) {
            PostEntity postEntity = mapper.toModel(postDto);
            postEntity.setImage(file.getBytes());
            postEntity.setImageType(file.getContentType());
            blogRepository.save(postEntity);
        }
        else {
            throw new RuntimeException("Слишком большой файл. Размер не более: " + maxFileSize);
        }

    }

    @Override
    public PostDto getById(long id) {
        return blogRepository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    private boolean isFileSizeValid(MultipartFile file) {
        return file.getSize() <= maxFileSize;
    }

}
