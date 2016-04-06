package com.aberlin.blogspringapplication.blogjsonapi;

import com.aberlin.blog.Post;
import com.aberlin.blog.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.*;

public class ResponseBuilderObjectTest {

    ResponseBuilderObject responseBuilderObject;

    @Before
    public void setUp() throws Exception {
        responseBuilderObject = new ResponseBuilderObject();
    }

    @Test
    public void postCreated_returnsAPostCreatedResponse_onGetResponseCall() throws Exception {
        Post post = new Post("myTitle", "myContent", "myAuthor");
        responseBuilderObject.postCreated(post);

        ResponseEntity response = responseBuilderObject.getResponse();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(((PostWrapper)response.getBody()).getPost(), sameInstance(post));
    }

    @Test
    public void unknownAuthor_returnsABadRequestEmptyResponse_onGetResponseCall() throws Exception {
        responseBuilderObject.unknownAuthor();

        ResponseEntity response = responseBuilderObject.getResponse();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void postIsInvalid_returnsABadRequestResponse_onGetResponseCall() throws Exception {
        responseBuilderObject.postIsInvalid(Collections.singletonList(new ValidationError("Something was invalid")));

        ResponseEntity response = responseBuilderObject.getResponse();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

        List<String> errorList = ((Map<String, List<String>>) response.getBody()).get("errors");
        assertThat(errorList, hasSize(1));
        assertThat(errorList.get(0), is("Something was invalid"));
    }

}