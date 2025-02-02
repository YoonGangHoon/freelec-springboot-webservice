package com.jojoldu.book.freelec_springboot_webservice.service.posts;

import com.jojoldu.book.freelec_springboot_webservice.domain.posts.Posts;
import com.jojoldu.book.freelec_springboot_webservice.domain.posts.PostsRepository;
import com.jojoldu.book.freelec_springboot_webservice.dto.PostsResponseDto;
import com.jojoldu.book.freelec_springboot_webservice.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelec_springboot_webservice.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));

        posts.update(requestDto.getTitle(), requestDto.getContent()); // 리포지토레서 쿼리를 쏘지 않고 그대로 변경

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity); // entity를 직접 보낼 수 없기 때문에 DTO를 만들어서 리턴해 줌
    }
}
