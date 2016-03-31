package com.aberlin.blogjooq;

import com.aberlin.blog.Comment;
import com.aberlin.blog.CommentRepository;
import com.aberlin.blog.Post;
import com.aberlin.blog.PostRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class JooqCommentRepository implements CommentRepository {
    private final DSLContext context;
    private final CommentTable comments;
    private final PostRepository postRepository;

    public JooqCommentRepository(Connection connection, PostRepository postRepository) {
        this.postRepository = postRepository;
        context = DSL.using(connection, SQLDialect.SQLITE);
        comments = new CommentTable("comments");
    }

    @Override
    public void save(Comment comment) {
        context.insertInto(comments)
                .columns(comments.fields())
                .values(null, comment.getMessage(), comment.getPostId())
                .execute();
    }

    @Override
    public List<Comment> findAllForPostId(Long id) {
        Post post = postRepository.find(id);

        return context
                .select()
                .from(comments)
                .where(comments.postId.equal(id.intValue()))
                .fetch().stream().map(record -> {
                    return new Comment(
                            (Long) record.getValue("id"),
                            (String) record.getValue("message"),
                            post
                    );
                }).collect(Collectors.toList());
    }
}
