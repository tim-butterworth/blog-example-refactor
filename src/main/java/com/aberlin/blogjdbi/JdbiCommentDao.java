package com.aberlin.blogjdbi;

import com.aberlin.blog.Comment;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

interface JdbiCommentDao {

    @SqlUpdate("insert into comments (message, post_id) values (:comment.message, :comment.postId)")
    void save(@BindBean("comment") Comment comment);

    @SqlQuery("select * from comments where post_id = :postId")
    List<Comment> findAllForPostId(@Bind("postId") Long postId);

    @SqlUpdate("create table comments (id bigint auto_increment, message text, post_id bigint)")
    void createTable();

    @SqlUpdate("drop table if exists comments")
    void dropTable();
}
