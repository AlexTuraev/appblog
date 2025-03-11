package org.tasks.service;

import org.springframework.web.multipart.MultipartFile;
import org.tasks.dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    List<PostDto> getAllPost();

    void save(PostDto postDto, MultipartFile file) throws IOException;
}
