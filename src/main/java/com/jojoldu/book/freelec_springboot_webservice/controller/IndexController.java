package com.jojoldu.book.freelec_springboot_webservice.controller;

import com.jojoldu.book.freelec_springboot_webservice.config.auth.LoginUser;
import com.jojoldu.book.freelec_springboot_webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.freelec_springboot_webservice.dto.PostsResponseDto;
import com.jojoldu.book.freelec_springboot_webservice.service.posts.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;

    @GetMapping
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto postsResponseDto = postService.findById(id);
        model.addAttribute("post", postsResponseDto);

        return "posts-update";
    }
}
