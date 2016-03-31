package com.aberlin.blog;

import java.util.List;

public interface PostSubscriber {

    void postCreated(Post any);

    void unknownAuthor();

    void postIsInvalid(List<ValidationError> errors);

    <T> T getResponse();
}
