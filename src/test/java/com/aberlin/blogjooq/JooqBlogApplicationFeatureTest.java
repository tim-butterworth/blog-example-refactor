package com.aberlin.blogjooq;

import com.aberlin.blogspringapplication.DemoApplication;
import com.aberlin.blog.BlogApplicationFeatureTest;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DemoApplication.class)
public class JooqBlogApplicationFeatureTest extends BlogApplicationFeatureTest {

    @Autowired
    DataSource dataSource;
    Connection connection;

    @Override
    protected void resetRepository() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DSLContext context = DSL.using(connection, SQLDialect.SQLITE);
        context.execute("drop table if exists comments");
        context.execute("create table comments (id integer auto_increment, message text, post_id integer)");
        context.execute("drop table if exists posts");
        context.execute("create table posts (id integer auto_increment, title text, contents text, author text)");

        postRepository = new JooqPostRepository(connection);
        commentRepository = new JooqCommentRepository(connection, postRepository);
    }
}
