package com.aberlin.blog;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostSubscriber {

    void postCreated(Post any);

    void unknownAuthor();

    void postIsInvalid(List<ValidationError> errors);

    ResponseEntity getResponse();
}
