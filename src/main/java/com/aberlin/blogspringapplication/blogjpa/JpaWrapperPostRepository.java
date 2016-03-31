package com.aberlin.blogspringapplication.blogjpa;

import com.aberlin.blog.Post;
import com.aberlin.blog.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

public class JpaWrapperPostRepository implements PostRepository {
    private JpaPostRepository jpaPostRepository;

    public JpaWrapperPostRepository(JpaPostRepository jpaPostRepository) {
        this.jpaPostRepository = jpaPostRepository;
    }

    @Override
    public void createPost(Post post) {
        PostEntity entity = new PostEntity();
        entity.setTitle(post.getTitle());
        entity.setContents(post.getContents());
        entity.setAuthor(post.getAuthor());

        this.jpaPostRepository.save(entity);
    }

    @Override
    public List<Post> findAllPosts() {
        return this.jpaPostRepository.findAll().stream().map(postEntity -> {
            return hydratePost(postEntity);
        }).collect(Collectors.toList());
    }

    @Override
    public Post find(Long postId) {
        return hydratePost(jpaPostRepository.findOne(postId));
    }

    private Post hydratePost(PostEntity postEntity) {
        return new Post(postEntity.getId(), postEntity.getTitle(), postEntity.getContents(), postEntity.getAuthor());
    }
}
