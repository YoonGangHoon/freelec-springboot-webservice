package com.jojoldu.book.freelec_springboot_webservice.domain.posts;

import com.jojoldu.book.freelec_springboot_webservice.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor // public Post(){} 와 같은 효과
@Entity // 테이블과 링크될 클래스임을 나타냄
public class Posts extends BaseTimeEntity {

    @Id // Primary Key를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    @Column(length = 500, nullable = false) // 문자열 길이를 500으로 설정
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // CONTENT 타입을 TEXT로 변경
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
