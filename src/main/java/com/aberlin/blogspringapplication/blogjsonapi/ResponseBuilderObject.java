package com.aberlin.blogspringapplication.blogjsonapi;

import com.aberlin.blog.Post;
import com.aberlin.blog.PostSubscriber;
import com.aberlin.blog.ValidationError;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseBuilderObject implements PostSubscriber {
    private ResponseEntity responseEntity;

    @Override
    public void postCreated(Post post) {
        responseEntity = new ResponseEntity<>(new PostWrapper(post), HttpStatus.OK);
    }

    @Override
    public void unknownAuthor() {
        responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    public void postIsInvalid(List<ValidationError> errors) {
        responseEntity = new ResponseEntity<>(transformValidationErrorsToJson(errors), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity getResponse() {
        if(responseEntity!=null) return responseEntity;
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String transformValidationErrorsToJson(List<ValidationError> errors) {
        Map<String, Object> map = new HashMap<>();
        map.put("errors",  errors.stream().map(error -> error.message()).collect(Collectors.toList()));
        return new Gson().toJson(map);
    }

}
