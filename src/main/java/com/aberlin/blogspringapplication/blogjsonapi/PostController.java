package com.aberlin.blogspringapplication.blogjsonapi;

import com.aberlin.blog.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
class PostController {

    private final ResponseBuilderFactory responseBuilderFactory;
    private final BlogApplication application;

    @Autowired
    PostController(ResponseBuilderFactory responseBuilderFactory, BlogApplication application) {
        this.responseBuilderFactory = responseBuilderFactory;
        this.application = application;
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/posts", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity create(
            @RequestParam String title,
            @RequestParam String contents,
            @RequestParam String author
    ) {
        return application.createPost(title, contents, author, responseBuilderFactory.getInstance()).getResponse();
    }

}
