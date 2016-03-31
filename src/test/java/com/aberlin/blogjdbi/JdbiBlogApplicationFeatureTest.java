package com.aberlin.blogjdbi;

import com.aberlin.blogspringapplication.DemoApplication;
import com.aberlin.blog.BlogApplicationFeatureTest;
import org.junit.runner.RunWith;
import org.skife.jdbi.v2.DBI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DemoApplication.class)
public class JdbiBlogApplicationFeatureTest extends BlogApplicationFeatureTest {

    @Autowired
    DataSource dataSource;

    DBI dbi;

    @Override
    protected void resetRepository() {
        initJdbi();
        setupTable();
        initRepository();
    }

    private void initRepository() {
        postRepository = new JdbiPostRepository(dbi);
        commentRepository = new JdbiCommentRepository(dbi, postRepository);
    }

    private void initJdbi() {
        dbi = new DBI(dataSource);
    }

    private void setupTable() {
        JdbiPostDao postDao = dbi.onDemand(JdbiPostDao.class);
        postDao.dropTable();
        postDao.createTable();

        JdbiCommentDao commentDao = dbi.onDemand(JdbiCommentDao.class);
        commentDao.dropTable();
        commentDao.createTable();
    }
}
