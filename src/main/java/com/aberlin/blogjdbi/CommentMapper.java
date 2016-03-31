package com.aberlin.blogjdbi;

import com.aberlin.blog.Comment;
import com.aberlin.blog.Post;
import com.aberlin.blog.PostRepository;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements ResultSetMapper<Comment> {

    private final PostRepository postRepository;

    public CommentMapper(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Comment map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Long postId = r.getLong("post_id");
        Post post = postRepository.find(postId);
        return new Comment(post, r.getString("message"));
    }

}
