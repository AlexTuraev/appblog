package org.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tasks.service.mapper.CommentTestMapper;
import org.tasks.service.mapper.PostMappingImplTest;
import org.tasks.service.mapping.CommentMapper;
import org.tasks.service.mapping.PostMapping;

@Configuration
public class MapperConfig {

    @Bean
    public CommentMapper commentMapper() {
        return new CommentTestMapper();
    }

    @Bean
    public PostMapping postMapper() {
        return new PostMappingImplTest();
    }

}
