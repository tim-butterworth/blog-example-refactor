package com.aberlin.blogjdbi;

import com.aberlin.blog.Comment;
import com.aberlin.blog.CommentRepository;
import com.aberlin.blog.PostRepository;
import org.skife.jdbi.v2.DBI;

import java.util.List;

public class JdbiCommentRepository implements CommentRepository {

    private final JdbiCommentDao commentDao;
    private final PostRepository postRepository;

    public JdbiCommentRepository(DBI dbi, PostRepository postRepository) {
        this.postRepository = postRepository;
        dbi.registerMapper(new CommentMapper(postRepository));

        this.commentDao = dbi.onDemand(JdbiCommentDao.class);
    }

    @Override
    public void save(Comment comment) {
        this.commentDao.save(comment);
    }

    @Override
    public List<Comment> findAllForPostId(Long postId) {
        return this.commentDao.findAllForPostId(postId);
    }
}
