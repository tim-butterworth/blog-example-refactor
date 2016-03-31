package com.aberlin.blogspringapplication.blogjsonapi;

import com.aberlin.blog.Post;
import org.springframework.util.MultiValueMap;

public class PostWrapper {
    private Post post;

    public PostWrapper(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
