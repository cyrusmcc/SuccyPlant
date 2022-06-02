package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="authUsername")
    private String authorUsername;

    @Column(name="type")
    private PostType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Image image;

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")

    )
    @OrderBy("category ASC")
    private Set<ContentTag> contentTags;

    @OneToOne(orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "post")
    private CommentBook commentBook = new CommentBook();

    public CommentBook getCommentBook() {
        return commentBook;
    }

    public void setCommentBook(CommentBook commentBook) {
        this.commentBook = commentBook;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_list_id")
    private PostList postList;


    public Post() {
        this.image = new Image();
        this.contentTags = new TreeSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public Post setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public PostType getPostType() {
        return type;
    }

    public Post setPostType(PostType type) {
        this.type = type;
        return this;
    }

    public Set<ContentTag> getTags() {
        return contentTags;
    }

    public Post setTags(Set<ContentTag> contentTags) {
        this.contentTags = contentTags;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public Post setImage(Image image) {
        this.image = image;
        return this;
    }

    public void addTag(ContentTag tag) {
        if (contentTags.contains(tag))
            throw new IllegalArgumentException();

        contentTags.add(tag);
    }

    public PostList getPostList() {
        return postList;
    }

    public Post setPostList(PostList postList) {
        this.postList = postList;
        return this;
    }
}
