package com.aberlin.blogspringapplication.blogjpa;

import com.aberlin.blogspringapplication.DemoApplication;
import com.aberlin.blog.BlogApplicationFeatureTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DemoApplication.class)
public class JpaBlogApplicationFeatureTest extends BlogApplicationFeatureTest {

    @Autowired
    JpaPostRepository jpaPostRepository;

    @Autowired
    JpaCommentRepository jpaCommentRepository;

    @Override
    protected void resetRepository() {
        postRepository = new JpaWrapperPostRepository(jpaPostRepository);
        commentRepository = new JpaWrapperCommentRepository(jpaCommentRepository, postRepository);
        jpaPostRepository.deleteAll();
    }
}
