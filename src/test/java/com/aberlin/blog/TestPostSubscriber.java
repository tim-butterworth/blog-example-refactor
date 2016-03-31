package com.aberlin.blog;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class TestPostSubscriber implements PostSubscriber {
    Post createdPost;

    @Override
    public void postCreated(Post post) {
        createdPost = post;
    }

    @Override
    public void unknownAuthor() {
    }

    @Override
    public void postIsInvalid(List<ValidationError> errors) {
    }

    @Override
    public ResponseEntity getResponse() {
        return null;
    }

}
