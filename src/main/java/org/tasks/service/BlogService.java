package org.tasks.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.tasks.dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    List<PostDto> getAllPost(String search, Integer pageSize, Integer pageNumber);

    void save(PostDto postDto, MultipartFile file) throws IOException;

    PostDto getById(long id);

    void deleteById(long id);

    Model getAllPostModel(Model model, String search, Integer pageSize, Integer pageNumber);
}
