package com.malgn.domain.content.repository;

import com.malgn.domain.content.entity.Content;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@NullMarked
public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findById(Long id);
    Page<Content> findAll(Pageable pageable);
}
