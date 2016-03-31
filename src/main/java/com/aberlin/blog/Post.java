package com.aberlin.blog;

public class Post {
    private final Long id;
    private final String title;
    private final String contents;
    private final String author;

    public Post(String title, String contents, String author) {
        this(null, title, contents, author);
    }

    public Post(Long id, String title, String contents, String author) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }
}
