package com.aberlin.blog;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BlogApplicationTest {
    PostRepository postRepository = mock(PostRepository.class);
    CommentRepository commentRepository = mock(CommentRepository.class);

    BlogApplication blog = new BlogApplication(postRepository, commentRepository);

    @Test
    public void itValidatesTheAuthorOfThePost() {
        BlogApplication blog = new BlogApplication(new InMemoryPostRepository(), new InMemoryCommentRepository());
        PostSubscriber adamPostSubscriber = mock(PostSubscriber.class);
        blog.createPost("some title", "some contents", "Adam Berlin", adamPostSubscriber);
        verify(adamPostSubscriber).postCreated(any(Post.class));

        PostSubscriber johnPostSubscriber = mock(PostSubscriber.class);
        blog.createPost("some title", "some contents", "John Berlin", johnPostSubscriber);
        verify(johnPostSubscriber).unknownAuthor();
    }

    @Test
    public void itEmitsAPostCreatedEventWithThePost() {
        BlogApplication blog = new BlogApplication(new InMemoryPostRepository(), new InMemoryCommentRepository());
        TestPostSubscriber postObserver = new TestPostSubscriber();
        blog.createPost("some title", "some contents", "Adam Berlin", postObserver);

        assertThat(postObserver.createdPost, notNullValue());
        assertThat(postObserver.createdPost.getId(), notNullValue());
        assertThat(postObserver.createdPost.getTitle(), equalTo("some title"));
        assertThat(postObserver.createdPost.getContents(), equalTo("some contents"));
        assertThat(postObserver.createdPost.getAuthor(), equalTo("Adam Berlin"));
    }

    @Test
    public void itTransformsPostsIntoPostData() {
        List<Post> fakePosts = new ListOfPosts();
        fakePosts.add(new Post("foo", "bar", "baz"));

        when(postRepository.findAllPosts()).thenReturn(fakePosts);

        blog.createPost("some title", "some contents", "some author", new TestPostSubscriber());

        List<PostData> posts = blog.getPosts();
        assertThat(posts.get(0).get("title"), equalTo("foo"));
        assertThat(posts.get(0).get("contents"), equalTo("bar"));
        assertThat(posts.get(0).get("author"), equalTo("baz"));
    }

    @Test
    public void itValidatesTheTitleIsMoreThan3Characters() {
        TestPostSubscriber postObserver = mock(TestPostSubscriber.class);

        blog.createPost("HI", "some contents", "some author", postObserver);

        verify(postObserver).postIsInvalid(eq(Arrays.asList(new ValidationError("Titles must be more than 3 characters long."))));
        verifyNoMoreInteractions(postObserver);
    }
}