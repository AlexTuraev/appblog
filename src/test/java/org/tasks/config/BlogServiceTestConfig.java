package org.tasks.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tasks.dao.repository.BlogRepository;
import org.tasks.service.BlogService;
import org.tasks.service.CommentService;
import org.tasks.service.impl.BlogServiceImpl;
import org.tasks.service.mapping.PostMapping;
import org.tasks.service.mapper.PostMappingImplTest;

@Configuration
public class BlogServiceTestConfig {

    @Bean
    @Primary
    public BlogRepository mockBlogRepository() {
        return Mockito.mock(BlogRepository.class);
    }

    @Bean
    public PostMapping postMapper() {
        return new PostMappingImplTest();
    }

    @Bean
    public CommentService mockCommentService() {
        return Mockito.mock(CommentService.class);
    }

    @Bean
    public BlogService blogService(BlogRepository mockBlogRepository, CommentService commentService, PostMapping postMapper) {
        return new BlogServiceImpl(mockBlogRepository, postMapper, commentService);
    }

}
