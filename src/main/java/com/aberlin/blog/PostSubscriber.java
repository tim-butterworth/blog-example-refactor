package com.aberlin.blog;

import java.util.List;

public interface PostSubscriber <T> {

    void postCreated(Post any);

    void unknownAuthor();

    void postIsInvalid(List<ValidationError> errors);

    T getResponse();
}
