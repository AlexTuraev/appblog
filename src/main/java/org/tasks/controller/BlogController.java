package org.tasks.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;

import java.util.List;

@MultipartConfig
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String savePost(
            @ModelAttribute PostDto postDto,
            @RequestParam(required = false, name = "file") MultipartFile file
    ) {
        try {
            blogService.save(postDto, file);
            return "redirect:/blog";
        }catch (Exception e) {
            return "redirect:/errorsave";
        }
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
//        PostDto post = blogService.getById();

        return "article";
    }

}
