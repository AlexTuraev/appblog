package org.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public String getAllPost(Model model) {
        List<PostDto> posts =  blogService.getAllPost();
        model.addAttribute("posts", posts);

        return "blogpage";
    }

    @PostMapping
    public String savePost(@ModelAttribute PostDto postDto) {
        blogService.save(postDto);
        return "redirect:/blog";
    }

}
