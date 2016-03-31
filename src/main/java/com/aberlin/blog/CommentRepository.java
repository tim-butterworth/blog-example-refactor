package com.aberlin.blog;

import java.util.List;

public interface CommentRepository {
    void save(Comment comment);

    List<Comment> findAllForPostId(Long id);
}
