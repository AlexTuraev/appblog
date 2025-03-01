package org.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home")
public class BlogController {

    @GetMapping
    @ResponseBody
    public String getAll() {
        return """
                <h1>Hello</h1>
                <h3>Blog</h3>
                """;
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

}
