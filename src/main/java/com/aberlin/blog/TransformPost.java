package com.aberlin.blog;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class TransformPost {
    private Post post;
    private final List<Comment> comments;

    public TransformPost(Post post, List<Comment> comments) {
        this.post = post;
        this.comments = comments;
    }

    public PostData invoke() {
        PostData postData = new PostData();
        postData.put("id", post.getId());
        postData.put("title", post.getTitle());
        postData.put("contents", post.getContents());
        postData.put("author", post.getAuthor());
        postData.put("comments", commentsAsData());
        return postData;
    }

    private static class CommentData extends HashMap<String, Object> {
    }

    private static class TransformComment {
        private final Comment comment;

        public TransformComment(Comment comment) {
            this.comment = comment;
        }

        public CommentData invoke() {
            CommentData map = new CommentData();
            map.put("message", comment.getMessage());
            return map;
        }
    }

    private List<CommentData> commentsAsData() {
        return comments.stream().map(comment -> {
            return new TransformComment(comment).invoke();
        }).collect(Collectors.toList());
    }
}