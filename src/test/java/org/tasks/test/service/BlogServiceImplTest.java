package org.tasks.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tasks.WebConfig;
import org.tasks.dao.model.PostEntity;
import org.tasks.dao.repository.BlogRepository;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;
import org.tasks.service.CommentService;
import org.tasks.test.config.BlogServiceTestConfig;
import org.tasks.test.config.WebConfigTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


//import static org.junit.jupiter.api.Assertions.assertTrue;
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {BlogServiceTestConfig.class/*, WebConfig.class*/})

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

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        PostEntity postEntity1 = new PostEntity(1L, "title1", "content1", 10, "#аренда", null, null);
        PostEntity postEntity2 = new PostEntity(2L, "title2", "content2", 20, "#продажа", null, null);
        posts = new ArrayList<>();
        posts.add(postEntity1);
        posts.add(postEntity2);

        PostDto postDto1 = new PostDto(1L, "title1", "content1", 10, "#аренда", null, null, null, 10);
        PostDto postDto2 = new PostDto(2L, "title2", "content2", 20, "#продажа", null, null, null, 10);
        postDtos = new ArrayList<>();
        postDtos.add(postDto1);
        postDtos.add(postDto2);
    }

    @Test
    void getAllPost() {
        when(mockBlogRepository.findAll(null, 3, 0)).thenReturn(posts);
        when(mockCommentService.getCountCommentById(anyLong())).thenReturn(10);

        List<PostDto> actualPosts = blogService.getAllPost(null, 3, 0);

        assertEquals(2, actualPosts.size());
        assertEquals(postDtos.get(0), actualPosts.get(0));
        assertEquals(postDtos.get(1), actualPosts.get(1));
    }

/*    @Test
    void save() {
    }

    @Test
    void getById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getAllPostModel() {
    }

    @Test
    void addLike() {
    }*/
}