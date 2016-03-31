package com.aberlin.blogspringapplication.blogjpa;

import com.aberlin.blog.Comment;
import com.aberlin.blog.CommentRepository;
import com.aberlin.blog.Post;
import com.aberlin.blog.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

public class JpaWrapperCommentRepository implements CommentRepository {
    private final JpaCommentRepository jpaCommentRepository;
    private final PostRepository postRepository;

    public JpaWrapperCommentRepository(JpaCommentRepository jpaCommentRepository, PostRepository postRepository) {
        this.jpaCommentRepository = jpaCommentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void save(Comment comment) {
        this.jpaCommentRepository.save(new CommentEntity(comment.getMessage(), comment.getPostId()));
    }

    @Override
    public List<Comment> findAllForPostId(Long postId) {
        return jpaCommentRepository.findAllByPostId(postId).stream().map(commentEntity -> {
            Post post = postRepository.find(postId);
            return new Comment(post, commentEntity.getMessage());
        }).collect(Collectors.toList());
    }
}
