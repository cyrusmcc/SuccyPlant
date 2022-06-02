package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @OneToOne
    @Column(name="parent_comment_id")
    private PostComment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommentBook commentBook;

    @Column(name = "content", columnDefinition = "TEXT")
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 3000, message = "{validation.name.size.too_long}")
    private String content;

    @OneToOne()
    private User author;

    @Column(name="time_stamp")
    private Timestamp timestamp = new Timestamp(new Date().getTime());

    @Column(name="deleted")
    private boolean deleted;

    @Column(name="flagged")
    private boolean flagged;

    @Column(name="depth")
    private int depth;

    public PostComment() {
    }

    public PostComment(PostComment parentComment) {
        this.parentComment = parentComment;
        depth = findDepth();
    }

    public int findDepth() {
        int depth = 0;
        PostComment currComment = this;

        while (currComment.parentComment != null) {
            currComment = parentComment;
            depth++;
        }

        return depth;
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

    public PostComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(PostComment parentComment) {
        this.parentComment = parentComment;
    }
}
