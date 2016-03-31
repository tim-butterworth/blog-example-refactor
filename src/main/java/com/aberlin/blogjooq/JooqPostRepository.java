package com.aberlin.blogjooq;

import com.aberlin.blog.Post;
import com.aberlin.blog.PostRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;

public class JooqPostRepository implements PostRepository {

    DSLContext context;
    PostTable posts;

    public JooqPostRepository(Connection connection) {
        context = DSL.using(connection, SQLDialect.SQLITE);
        posts = new PostTable("posts");
    }

    @Override
    public void createPost(Post post) {
        context.insertInto(posts)
                .columns(posts.fields())
                .values(null, post.getTitle(), post.getContents(), post.getAuthor())
                .execute();
    }

    @Override
    public List<Post> findAllPosts() {
        return context.select()
                .from(posts)
                .fetchInto(Post.class);
    }

    @Override
    public Post find(Long postId) {
        return context
                .select()
                .from(posts)
                .fetchOneInto(Post.class);
    }
}
