package com.aberlin.blog;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public abstract class BlogApplicationFeatureTest {
    public PostRepository postRepository;
    public CommentRepository commentRepository;
    BlogApplication application;

    @Before
    public void before() {
        resetRepository();
        application = new BlogApplication(postRepository, commentRepository);
    }

    protected abstract void resetRepository();

    @Test
    public void itCanStoreAndRetrieveBlogPosts() {
        String title = "Clean Architecture";
        String contents = "The goal with the blog application is to have the application unaware of *how* the blog posts are stored.";
        String author = "Adam Berlin";
        application.createPost(title, contents, author, new TestPostSubscriber());

        List<PostData> expectedPosts = new ArrayList<>();

        PostData post = new PostData();
        post.put("title", title);
        post.put("contents", contents);
        post.put("author", author);
        expectedPosts.add(post);

        assertThat(application.getPosts().get(0).get("id"), notNullValue());
        assertThat(application.getPosts().get(0).get("title"), equalTo(title));
        assertThat(application.getPosts().get(0).get("contents"), equalTo(contents));
        assertThat(application.getPosts().get(0).get("author"), equalTo(author));
    }

    @Test
    public void aUserCanCommentOnABlogPost() {
        BlogApplication application = new BlogApplication(postRepository, commentRepository);

        String title = "Some title";
        String contents = "Some contents";
        String author = "Adam Berlin";
        application.createPost(title, contents, author, new TestPostSubscriber());

        Long postId = (Long) application.getPosts().get(0).get("id");

        assertThat(postId, notNullValue());
        application.commentOnPost(postId, "I think this post is... tldr.");
        application.commentOnPost(postId, "wtf mate");

        List<Map<String, Object>> comments = (List<Map<String, Object>>) application.getPosts().get(0).get("comments");

        assertThat(comments, notNullValue());
        assertThat(comments.size(), greaterThan(0));
        assertThat(comments.get(0).get("message"), equalTo("I think this post is... tldr."));
        assertThat(comments.get(1).get("message"), equalTo("wtf mate"));
    }
}
