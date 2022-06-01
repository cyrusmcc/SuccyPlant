package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "content", columnDefinition = "TEXT")
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 3000, message = "{validation.name.size.too_long}")
    private String content;

    @OneToOne()
    private User author;


    private Timestamp timestamp = new Timestamp(new Date().getTime());

    private boolean deleted;

    private boolean flagged;

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
