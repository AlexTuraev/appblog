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
import org.springframework.web.multipart.MultipartFile;
import org.tasks.dto.CommentDto;
import org.tasks.dto.PostDto;
import org.tasks.service.BlogService;
import org.tasks.service.CommentService;

import java.util.List;

@MultipartConfig
@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;
    private final CommentService commentService;

    public BlogController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getAllPost(
            Model model,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber) {

        model = blogService.getAllPostModel(model, search, pageSize, pageNumber);

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
    public String getPost(@PathVariable(name = "id") long id, Model model) {
        PostDto post = blogService.getById(id);
        model.addAttribute("post", post);

        List<CommentDto> comments = commentService.findByPostId(post.getId());
        model.addAttribute("comments", comments);

        return "article";
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String deleteById(@PathVariable(name = "id") long id, Model model) {
        blogService.deleteById(id);

        return  "redirect:/blog";
    }

    @PostMapping(value = "/comment")
    public String addComment(@ModelAttribute CommentDto commentDto, Model model) {
        commentService.addComment(commentDto);

        return getPost(commentDto.getPostId(), model);
    }

    @PostMapping(value = "/{id}/like")
    public String addLike(@PathVariable(name = "id") long id, @RequestParam(name = "like") boolean like, Model model) {
        blogService.addLike(id, like);

        return "redirect:/blog/"+id;
    }

}
