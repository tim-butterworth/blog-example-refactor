package com.aberlin.blogspringapplication.blogjsonapi;

import com.aberlin.blog.BlogApplication;
import com.aberlin.blog.Post;
import com.aberlin.blog.PostSubscriber;
import com.aberlin.blog.ValidationError;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nitorcreations.junit.runners.NestedRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(NestedRunner.class)
public class PostControllerTest {

    MockMvc client;
    BlogApplication blogApplication;
    ResponseBuilderFactory responseBuilderFactory;
    ArgumentCaptor<PostSubscriber> observerArgument = ArgumentCaptor.forClass(PostSubscriber.class);
    private ResponseBuilderObject postSubscriber;

    @Before
    public void before() {
        blogApplication = mock(BlogApplication.class);
        responseBuilderFactory = mock(ResponseBuilderFactory.class);
        postSubscriber = mock(ResponseBuilderObject.class);

        when(responseBuilderFactory.getInstance()).thenReturn(postSubscriber);

        client = MockMvcBuilders.standaloneSetup(
                new PostController(responseBuilderFactory, blogApplication)
        ).build();
    }

    public class WhenCreatingAPostSucceeds {
        @Before
        public void before() {
            when(postSubscriber.getResponse()).thenReturn(new ResponseEntity<>(new PostWrapper(new Post(1000l, "foo", "bar", "baz")), HttpStatus.CREATED));
            when(blogApplication.createPost("some post name", "some post contents", "some valid author", postSubscriber)).thenReturn(postSubscriber);
        }

        @Test
        public void itReturnsA201Created() throws Exception {
            client.perform(
                    post("/posts")
                            .param("title", "some post name")
                            .param("contents", "some post contents")
                            .param("author", "some valid author")
            ).andExpect(status().isCreated());
        }

        @Test
        public void itReturnsThePostAsJson() throws Exception {
            ResultActions response = client.perform(
                    post("/posts")
                            .param("title", "some post name")
                            .param("contents", "some post contents")
                            .param("author", "some valid author")
            );

            String responseBody = response.andReturn().getResponse().getContentAsString();
            assertThat(responseBody, equalTo("{\"post\":{\"id\":1000,\"title\":\"foo\",\"contents\":\"bar\",\"author\":\"baz\"}}"));
        }
    }

    public class WhenCreatingAPostFailsBecauseOfAnInvalidAuthor {

        @Before
        public void before() {
            when(postSubscriber.getResponse()).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            when(blogApplication.createPost("some post name", "some post contents", "some invalid author", postSubscriber)).thenReturn(postSubscriber);
        }

        @Test
        public void createPostWithInvalidAuthor() throws Exception {
            client.perform(
                    post("/posts")
                            .param("title", "some post name")
                            .param("contents", "some post contents")
                            .param("author", "some invalid author")
            ).andExpect(status().isBadRequest());
        }

    }

    public class WhenCreatingAPostFailsDueToValidationErrors {
        @Before
        public void before() {
            Map<String, List<String>> errorMap = new HashMap<>();
            errorMap.put("errors", Collections.singletonList("The title is too long.  Titles can only be 3 characters."));
            when(postSubscriber.getResponse()).thenReturn(new ResponseEntity<>(errorMap,
                    HttpStatus.BAD_REQUEST)
            );
            when(blogApplication.createPost("some post name", "some post contents", "some invalid author", postSubscriber)).thenReturn(postSubscriber);
        }

        @Test
        public void itReturnsTheListOfErrorsToTheClient() throws Exception {
            client.perform(
                    post("/posts")
                            .param("title", "some post name")
                            .param("contents", "some post contents")
                            .param("author", "some invalid author")
            ).andDo(new ResultHandler() {
                @Override
                public void handle(MvcResult result) throws Exception {
                    result.getResponse().getContentAsString();
                }
            })
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"errors\":[\"The title is too long.  Titles can only be 3 characters.\"]}"));
        }
    }

    private PostSubscriber postObserver() {
        return observerArgument.getValue();
    }
}