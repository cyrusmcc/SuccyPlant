package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotBlank
    @Column(name="username")
    private String username;

    @NotBlank
    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    private File profileImage;

    private LocalDate userJoinDate;

    // private CommentList userCommentList;

    // private FavoriteList userFavoriteList;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        userJoinDate = LocalDate.now();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        userJoinDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public File getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }

    public LocalDate getUserJoinDate() {
        return userJoinDate;
    }

    public void setUserJoinDate(LocalDate userJoinDate) {
        this.userJoinDate = userJoinDate;
    }
}
