package com.aberlin.blog;

import java.util.List;

public interface PostRepository {
    void createPost(Post post);

    List<Post> findAllPosts();

    Post find(Long postId);
}
