package com.aberlin.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlogApplication {

    private List<String> validAuthors = new ArrayList<>();
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public BlogApplication(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        validAuthors.add("Adam Berlin");
        validAuthors.add("someauthor");
    }

    public PostSubscriber createPost(String title, String contents, String author, PostSubscriber postSubscriber) {
        Post post = new Post(title, contents, author);

        List<ValidationError> validationErrors = validatePost(post);

        if ( ! validationErrors.isEmpty()) {
            postSubscriber.postIsInvalid(validationErrors);
            return postSubscriber;
        }

        if (knownAuthor(author)) {
            postRepository.createPost(post);
            postSubscriber.postCreated(getLatestPost());
        } else {
            postSubscriber.unknownAuthor();
        }
        return postSubscriber;
    }

    public List<PostData> getPosts() {
        List<Post> allPosts = this.postRepository.findAllPosts();

        return allPosts.stream().map(post -> transform(post))
                .collect(Collectors.toList());
    }

    public void commentOnPost(Long postId, String message) {
        Post post = findPostById(postId);
        this.commentRepository.save(new Comment(post, message));
    }

    private List<ValidationError> validatePost(Post post) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (post.getTitle().length() <= 3) {
            validationErrors.add(new ValidationError("Titles must be more than 3 characters long."));
        }

        return validationErrors;
    }

    private Post getLatestPost() {
        List<Post> allPosts = postRepository.findAllPosts();
        return allPosts.get(allPosts.size() - 1);
    }

    private Boolean knownAuthor(String author) {
        return validAuthors.contains(author);
    }

    private Post findPostById(Long postId) {
        return this.postRepository.findAllPosts().stream()
                .filter(post -> postId.equals(post.getId()))
                .findFirst()
                .orElse(null);
    }

    private PostData transform(Post post) {
        return new TransformPost(post, commentRepository.findAllForPostId(post.getId())).invoke();
    }
}
