package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @JoinColumn(name="parent_comment_id")
    @ManyToOne()
    private PostComment parentComment;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<CommentBook> commentBooks = new HashSet<>();

    @Column(name = "content", columnDefinition = "TEXT")
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 3000, message = "{validation.name.size.too_long}")
    private String content;

    @JoinColumn(name="user_id")
    @ManyToOne()
    private User user;

    @Column(name="time_stamp")
    private Timestamp timestamp = new Timestamp(new Date().getTime());

    @Column(name="deleted")
    private boolean deleted;

    @Column(name="flagged")
    private boolean flagged;

    @Column(name="depth")
    private int depth;

    @Column(name="score")
    private int score;

    //@Column(name="image")
    //private Image image;

    public PostComment(PostComment parentComment, String content) {
        this.parentComment = parentComment;
        this.content = content;
        depth = findDepth();
    }

    public PostComment() {

    }

    public int findDepth() {
        int depth = 0;
        PostComment currComment = this;

        while (currComment.parentComment != null) {
            currComment = currComment.parentComment;
            depth++;
        }

        return depth;
    }

    public void incrScore() {
        this.score++;
    }

    public void decScore() {
        this.score--;
    }

    /*
    public CommentBook getCommentBook() {
        return commentBook;
    }

    public void setCommentBook(CommentBook commentBook) {
        this.commentBook = commentBook;
    }
*/
    public Long getCommentId() {
        return commentId;
    }

    public PostComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(PostComment parentComment) {
        this.parentComment = parentComment;
        setDepth(findDepth());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addCommentBook(CommentBook commentBook) {
        this.commentBooks.add(commentBook);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
     */
}