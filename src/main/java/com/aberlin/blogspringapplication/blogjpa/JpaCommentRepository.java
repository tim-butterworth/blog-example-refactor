package com.aberlin.blogspringapplication.blogjpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPostId(Long postId);
}
