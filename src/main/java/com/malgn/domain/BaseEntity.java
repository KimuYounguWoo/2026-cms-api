package com.malgn.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id()
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt = LocalDateTime.now();
}