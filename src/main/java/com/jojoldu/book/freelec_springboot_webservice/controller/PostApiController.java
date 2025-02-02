package com.jojoldu.book.freelec_springboot_webservice.controller;

import com.jojoldu.book.freelec_springboot_webservice.dto.PostsResponseDto;
import com.jojoldu.book.freelec_springboot_webservice.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelec_springboot_webservice.dto.PostsUpdateRequestDto;
import com.jojoldu.book.freelec_springboot_webservice.service.posts.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postService.findById(id);
    }
}
