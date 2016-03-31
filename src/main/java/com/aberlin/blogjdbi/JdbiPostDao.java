package com.aberlin.blogjdbi;

import com.aberlin.blog.Post;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PostMapper.class)
interface JdbiPostDao {
    @SqlUpdate("insert into posts (title, contents, author) values (:post.title, :post.contents, :post.author)")
    void save(@BindBean("post") Post post);

    @SqlQuery("select * from posts")
    List<Post> findAll();

    @SqlQuery("select * from posts where id = :postId")
    Post findOne(@Bind("postId") Long postId);

    @SqlUpdate("create table posts (id bigint auto_increment, title text, contents text, author text)")
    void createTable();

    @SqlUpdate("drop table if exists posts")
    void dropTable();

}
