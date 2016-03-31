package com.aberlin.blog;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Comment {
    private final Long id;
    private final String message;
    private final Post post;

    public Comment(Post post, String message) {
        this.id = null;

        if(post == null) {
            throw new PostCannotBeNullException();
        }

        this.post = post;
        this.message = message;
    }

    public Comment(Long id, String message, Post post) {
        this.id = id;

        if(post == null) {
            throw new PostCannotBeNullException();
        }

        this.post = post;
        this.message = message;
    }

    public Boolean belongsTo(Long postId) {
        return postId.equals(getPostId());
    }

    public String getMessage() {
        return message;
    }

    public Long getPostId() {
        return this.post.getId();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private class PostCannotBeNullException extends RuntimeException {
    }
}
