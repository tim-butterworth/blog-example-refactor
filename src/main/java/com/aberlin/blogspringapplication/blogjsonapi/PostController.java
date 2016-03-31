package com.aberlin.blogspringapplication.blogjsonapi;

import com.aberlin.blog.BlogApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

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
