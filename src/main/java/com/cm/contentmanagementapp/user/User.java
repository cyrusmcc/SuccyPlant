package com.cm.contentmanagementapp.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;


@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    private String role;

    private String username;

    private String password;

    private String email;

    private File profileImage;

    private LocalDate userJoinDate;

    // private CommentList userCommentList;

    // private FavoriteList userFavoriteList;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        role = "USER";
        userJoinDate = LocalDate.now();
    }

}
