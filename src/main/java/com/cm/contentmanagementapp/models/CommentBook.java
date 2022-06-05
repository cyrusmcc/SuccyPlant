package com.cm.contentmanagementapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CommentBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_book_id")
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "commentBook",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "comments")
    @JsonIgnore
    private List<PostComment> comments = new ArrayList<>();

    @Column(name = "num_comments")
    private Long numComments = 0L;

    public CommentBook() {
    }

    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setCommentBook(this);
        numComments = (long) comments.size();
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setCommentBook(null);
        numComments = (long) comments.size();
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
        this.id = post.getId();
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public Long getNumComments() {
        return numComments;
    }

    public void setNumComments(Long numComments) {
        this.numComments = numComments;
    }
}
