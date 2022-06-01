package com.cm.contentmanagementapp.models;

import javax.persistence.*;

@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name="parent_comment_id")
    private Long parentCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommentBook commentBook;

    public PostComment() {
    }

    public PostComment(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public CommentBook getCommentBook() {
        return commentBook;
    }

    public void setCommentBook(CommentBook commentBook) {
        this.commentBook = commentBook;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
