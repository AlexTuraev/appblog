package org.tasks.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;
import org.tasks.dto.PostDto;
import org.tasks.config.BlogServiceTestConfig;
import org.tasks.service.BlogService;
import org.tasks.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {BlogServiceTestConfig.class})
class BlogServiceImplTest {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService mockCommentService;

    @Autowired
    private BlogRepository mockBlogRepository;

    private List<PostEntity> posts;
    private List<PostDto> postDtos;

    private final int COUNT_COMMENT = 10;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        PostEntity postEntity1 = new PostEntity(1L, "title1", "content1", 10, "#аренда", null, null);
        PostEntity postEntity2 = new PostEntity(2L, "title2", "content2", 20, "#продажа", null, null);
        posts = new ArrayList<>();
        posts.add(postEntity1);
        posts.add(postEntity2);

        PostDto postDto1 = new PostDto(1L, "title1", "content1", 10, "#аренда", null, null, null, COUNT_COMMENT);
        PostDto postDto2 = new PostDto(2L, "title2", "content2", 20, "#продажа", null, null, null, COUNT_COMMENT);
        postDtos = new ArrayList<>();
        postDtos.add(postDto1);
        postDtos.add(postDto2);
    }

    @Test
    void getAllPost() {
        when(mockBlogRepository.findAll(null, 3, 0)).thenReturn(posts);
        when(mockCommentService.getCountCommentByPostId(anyLong())).thenReturn(COUNT_COMMENT);
        List<PostDto> actualPosts = blogService.getAllPost(null, 3, 0);

        assertEquals(2, actualPosts.size());
        assertEquals(postDtos.get(0), actualPosts.get(0));
        assertEquals(postDtos.get(1), actualPosts.get(1));
    }

    @Test
    void getById() {
        when(mockBlogRepository.findById(1L)).thenReturn(Optional.of(posts.get(0)));
        when(mockBlogRepository.findById(50L)).thenReturn(Optional.empty());

        PostDto expected = postDtos.get(0);
        expected.setCountComment(null);
        PostDto actualDto = blogService.getById(1L);
        assertEquals(expected, actualDto);

        actualDto = blogService.getById(50L);
        assertTrue(Objects.isNull(actualDto));
    }

}