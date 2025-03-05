package org.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;

import java.util.List;

@Controller
@RequestMapping("/")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    @ResponseBody
    public String getAll() {
        return """
                <h1>Hello</h1>
                <h3>Blog</h3>
                """;
    }

    @GetMapping("/somepage")
    public String getSomePage() {
        return "somepage";
    }

    @GetMapping("/blogpage")
    public String getBlogPage() {
        return "blogpage";
    }

    @GetMapping("/post")
    @ResponseBody
    public String getAllPost() {
        List<PostDto> posts =  blogService.getAllPost();
        posts.forEach(System.out::println);
        return "<h1>" + posts.toString() + "</h1>";
    }

}
