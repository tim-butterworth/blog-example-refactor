package com.aberlin.blogspringapplication.blogjpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class CommentEntity {

    @Id
    @GeneratedValue
    private final Long id;

    private final String message;
    private final Long postId;

    public CommentEntity() {
        this(null, null);
    }

    public CommentEntity(String message, Long postId) {
        this.id = null;
        this.message = message;
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public Long getPostId() {
        return postId;
    }
}
