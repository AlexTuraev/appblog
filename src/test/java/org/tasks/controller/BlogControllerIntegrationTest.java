package org.tasks.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tasks.config.DataSourceTestConfig;
import org.tasks.config.MapperConfig;
import org.tasks.config.web.WebConfigTest;
import org.tasks.dao.repository.BlogRepository;
import org.tasks.dao.repository.impl.BlogRepositoryImpl;
import org.tasks.dao.repository.impl.CommentRepositoryImpl;
import org.tasks.service.impl.BlogServiceImpl;
import org.tasks.service.impl.CommentServiceImpl;
import org.tasks.service.mapping.CommentMapper;
import org.tasks.service.mapping.CommentMapperImpl;
import org.tasks.service.mapping.PostMappingImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        jdbcTemplate.execute("delete from post");
        jdbcTemplate.execute("" +
                "insert into post(title, content, tags, count_like) values ('title1', 'content1', 'tag1', 10);\n" +
                "insert into post(title, content, tags, count_like) values ('title2', 'content2', 'tag1', 8);\n" +
                "insert into post(title, content, tags, count_like) values ('title3', 'content3', 'tag2', 5);");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from post");
    }

    @Test
    void getAllPost() throws Exception {
        mockMvc.perform(get("/blog").param("search", "").param("pageSize", "2").param("pageNumber", "1"))
                .andExpect(status().isOk());
    }


    //        model.getTitle(), model.getContent(), model.getTags(), model.getImageType(), model.getImage());
    @Test
    void createOrUpdatePost() throws Exception {
//        mockMvc.perform(post("/blog")
//                .param("id", "1") // можно убрать, тогда новая запись
//                .param("title", "new title")
//                .param("content", "new content")
//                .param("tags", "new tags")
//                )
//                .andExpect(redirectedUrl("/blog"));

    }

    @Test
    void getPost() throws Exception {
//        mockMvc.perform(get("/1"))
//                .andExpect(status().isOk());
    }

    @Test
    void deleteById() {
    }

    @Test
    void addComment() {
    }

    @Test
    void addLike() {
    }

    @Test
    void getEditPage() {
    }

    @Test
    void deleteCommentById() {
    }

    @Test
    void editCommentById() {
    }
}