package com.aberlin.blogjdbi;

import com.aberlin.blog.Post;
import com.aberlin.blog.PostRepository;
import org.skife.jdbi.v2.DBI;

import java.util.List;

public class JdbiPostRepository implements PostRepository {
    private final JdbiPostDao postDao;

    public JdbiPostRepository(DBI dbi) {
        this.postDao = dbi.onDemand(JdbiPostDao.class);
    }

    @Override
    public void createPost(Post post) {
        postDao.save(post);
    }

    @Override
    public List<Post> findAllPosts() {
        return postDao.findAll();
    }

    @Override
    public Post find(Long postId) {
        return postDao.findOne(postId);
    }
}
