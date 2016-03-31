package com.aberlin.blogjdbi;

import com.aberlin.blog.Post;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements ResultSetMapper<Post> {
    @Override
    public Post map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Post(
            r.getLong("id"),
            r.getString("title"),
            r.getString("contents"),
            r.getString("author")
        );
    }
}
