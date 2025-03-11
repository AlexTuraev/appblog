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
import java.util.Objects;

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
//        Optional<PostEntity> entities = blogRepository.findById(1L);


    }

    @Override
    public void save(PostDto postDto, MultipartFile file) throws IOException {
        if (isFileSizeValid(file)) {
            PostEntity postEntity = mapper.toModel(postDto);
            postEntity.setImage(file.getBytes());
            blogRepository.save(postEntity);
        }
        else {
            throw new RuntimeException("Слишком большой файл. Размер не более: " + maxFileSize);
        }

    }

    private boolean isFileSizeValid(MultipartFile file) {
        if (file.getSize() <= maxFileSize) {
            return true;
        }
        else {
            return false;
        }
    }

    private byte[] getFileBytes(MultipartFile file) throws IOException {
        if (Objects.isNull(file)) {
            return new byte[0];
        }
        else {
            return file.getBytes();
        }

    }

}
