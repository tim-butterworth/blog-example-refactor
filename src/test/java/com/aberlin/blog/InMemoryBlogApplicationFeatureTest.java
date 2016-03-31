package com.aberlin.blog;

public class InMemoryBlogApplicationFeatureTest extends BlogApplicationFeatureTest {

    @Override
    protected void resetRepository() {
        postRepository = new InMemoryPostRepository();
        commentRepository = new InMemoryCommentRepository();
    }

}
