package com.aberlin.blogspringapplication;

import com.aberlin.blog.BlogApplication;
import com.aberlin.blog.CommentRepository;
import com.aberlin.blog.PostRepository;
import com.aberlin.blogspringapplication.blogjpa.JpaCommentRepository;
import com.aberlin.blogspringapplication.blogjpa.JpaPostRepository;
import com.aberlin.blogspringapplication.blogjpa.JpaWrapperCommentRepository;
import com.aberlin.blogspringapplication.blogjpa.JpaWrapperPostRepository;
import com.aberlin.blogspringapplication.blogjsonapi.ResponseBuilderFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public BlogApplication blogApplication(PostRepository postRespository, CommentRepository commentRepository) {
		return new BlogApplication(postRespository, commentRepository);
	}

	@Bean
	public CommentRepository commentRepository(JpaCommentRepository jpaCommentRepository, JpaPostRepository jpaPostRepository) {
		return new JpaWrapperCommentRepository(jpaCommentRepository, postRepository(jpaPostRepository));
	}

	@Bean
	public PostRepository postRepository(JpaPostRepository jpaPostRepository) {
		return new JpaWrapperPostRepository(jpaPostRepository);
	}

	@Bean
	public ResponseBuilderFactory responseBuilderFactory() {
		return new ResponseBuilderFactory();
	}

}
