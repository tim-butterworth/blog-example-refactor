package com.aberlin.blog;

import com.aberlin.blogspringapplication.blogjsonapi.ResponseBuilderObject;

public class ResponseBuilderFactory {
    public ResponseBuilderObject getInstance() {
        return new ResponseBuilderObject();
    }
}