package com.jojoldu.book.freelec_springboot_webservice.dto;

import com.jojoldu.book.freelec_springboot_webservice.domain.posts.Posts;

import java.time.LocalDateTime;

public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts enetity){
        this.id = enetity.getId();
        this.title = enetity.getTitle();
        this.author = enetity.getAuthor();
        this.modifiedDate = enetity.getModifiedDate();
    }

}
