package com.cm.contentmanagementapp.user;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name="role")
    private String role;

    @NotNull
    @Column(name="username")
    private String username;

    @NotNull
    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    private File profileImage;

    private LocalDate userJoinDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = ("role_id")))
    private Set<Role> userRoles = new HashSet<>();

    // private CommentList userCommentList;

    // private FavoriteList userFavoriteList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        userJoinDate = LocalDate.now();
    }

}
