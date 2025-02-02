package com.jojoldu.book.freelec_springboot_webservice.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity들이 이 클래스를 상속할 경우 필드들을 Column으로 인식하게 함
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate // 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 시간 자동 저장
    private LocalDateTime modifiedDate;


}
