package com.aberlin.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class InMemoryCommentRepository implements CommentRepository {
    private final List<Comment> comments;

    public InMemoryCommentRepository() {
        this.comments = new ArrayList<>();
    }

    @Override
    public void save(Comment comment) {
        comments.add(comment);
    }

    @Override
    public List<Comment> findAllForPostId(Long postId) {
        return comments.stream().filter(comment -> {
            return comment.belongsTo(postId);
        }).collect(Collectors.toList());
    }
}
