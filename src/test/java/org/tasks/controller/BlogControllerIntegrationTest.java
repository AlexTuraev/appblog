package org.tasks.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tasks.config.DataSourceTestConfig;
import org.tasks.config.MapperConfig;
import org.tasks.config.web.WebConfigTest;
import org.tasks.dao.model.CommentEntity;
import org.tasks.dao.repository.impl.BlogRepositoryImpl;
import org.tasks.dao.repository.impl.CommentRepositoryImpl;
import org.tasks.service.impl.BlogServiceImpl;
import org.tasks.service.impl.CommentServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitConfig(classes = {DataSourceTestConfig.class, WebConfigTest.class,
        BlogServiceImpl.class,
        CommentServiceImpl.class,
        BlogRepositoryImpl.class,
        CommentRepositoryImpl.class,
        MapperConfig.class,
        BlogController.class
})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
class BlogControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApp).build();

        jdbcTemplate.execute("delete from comment");
        jdbcTemplate.execute("delete from post");

        jdbcTemplate.execute("""
                insert into post(title, content, tags, count_like) values ('title1', 'content1', 'tag1', 10);
                insert into post(title, content, tags, count_like) values ('title2', 'content2', 'tag1', 8);
                insert into post(title, content, tags, count_like) values ('title3', 'content3', 'tag2', 5);
        """);

        Long id = getExistedPostId();
        jdbcTemplate.update("insert into comment(content, post_id) values (?, ?);", "Комментарий 1_1", id);
        jdbcTemplate.update("insert into comment(content, post_id) values (?, ?);", "Комментарий 1_2", id);

    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from comment");
        jdbcTemplate.execute("delete from post");
    }

    @Test
    void getAllPost() throws Exception {
        mockMvc.perform(get("/blog").param("search", "").param("pageSize", "2").param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("blogpage"))
                .andExpect(model().attributeExists( "posts"))
                .andExpect(model().attributeExists( "paging"));
    }

    @Test
    void createOrUpdatePost() throws Exception {
        MockMultipartFile fileImage = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test.jpg".getBytes());

        mockMvc.perform(multipart("/blog").file(fileImage)
                                .param("title", "new title")
                .param("content", "new content")
                .param("tags", "new tags")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
    }

    @Test
    void getPost() throws Exception {
        Long id = getExistedPostId();

        mockMvc.perform(get("/blog/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }

    @Test
    void deleteById() throws Exception {
        Long id = getExistedPostId();

        mockMvc.perform(post("/blog/" + id)
                .param("_method", "delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));

    }

    @Test
    void addComment() throws Exception {
        Long id = getExistedPostId();

        mockMvc.perform(post("/blog/comment")
                        .param("content", "some comment")
                        .param("postId", id.toString())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("article"));

    }

    @Test
    void addLike() throws Exception {
        Long id = getExistedPostId();

        mockMvc.perform(post("/blog/" + id + "/like")
                        .param("like", "true")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + id));
    }

    @Test
    void getEditPage() throws Exception {
        Long id = getExistedPostId();

        mockMvc.perform(post("/blog/" + id + "/geteditpostpage"))
                .andExpect(status().isOk())
                .andExpect(view().name("editpost"));
    }

    @Test
    void deleteCommentById() throws Exception {
        CommentEntity comment = getExistedCommentEntity();

        mockMvc.perform(post("/blog/comment/" + comment.getId())
                        .param("_method", "delete")
                        .param("postId", comment.getPostId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }

    @Test
    void editCommentById() throws Exception {
        CommentEntity comment = getExistedCommentEntity();

        mockMvc.perform(post("/blog/comment/" + comment.getId())
                        .param("_method", "put")
                        .param("postId", comment.getPostId().toString())
                        .param("content", "new modified comment"))
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }

    private Long getExistedPostId() {
        List<Long> ids = jdbcTemplate.query("select id from post order by id limit 1", (rs, rowNum) -> rs.getLong("id"));
        assertTrue(ids.size() == 1);
        return ids.getFirst();
    }

    private CommentEntity getExistedCommentEntity() {
        List<CommentEntity> comments = jdbcTemplate.query("select id, content, post_id from comment order by id limit 1",
                (rs, rowNum) -> new CommentEntity(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getLong("post_id")));
        assertFalse(comments.isEmpty());

        return comments.getFirst();
    }

}