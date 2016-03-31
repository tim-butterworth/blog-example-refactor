package com.aberlin.blog;

import java.util.ArrayList;
import java.util.List;

class InMemoryPostRepository implements PostRepository {
    private final List<Post> posts;
    private Long previousId = 1L;

    public InMemoryPostRepository() {
        this.posts = new ArrayList<>();
    }

    @Override
    public void createPost(Post post) {
        this.posts.add(new Post(nextId(), post.getTitle(), post.getContents(), post.getAuthor()));
    }

    @Override
    public List<Post> findAllPosts() {
        return posts;
    }

    @Override
    public Post find(Long postId) {
        return findAllPosts()
                .stream()
                .filter(post -> postId.equals(post.getId()))
                .findFirst()
                .orElse(null);
    }

    private Long nextId() {
        return previousId++;
    }
}
