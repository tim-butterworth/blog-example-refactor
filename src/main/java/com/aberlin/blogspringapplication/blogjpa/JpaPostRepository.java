package com.aberlin.blogspringapplication.blogjpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
}
