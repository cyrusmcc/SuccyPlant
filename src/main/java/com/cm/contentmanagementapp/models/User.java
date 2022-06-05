package com.cm.contentmanagementapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    private EnumRole role;

    @NotBlank
    @Column(name="username")
    private String username;

    @NotBlank
    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    private String profileImageUrl;

    private LocalDate userJoinDate;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_list_id")
    @JsonManagedReference
    private PostList postList;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Plant> userPlants = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Plant> plantWishList = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommentBook userComments = new CommentBook();


    public User() {
        this.role = EnumRole.ROLE_USER;
        postList = new PostList();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        userJoinDate = LocalDate.now();
        this.role = EnumRole.ROLE_USER;
        postList = new PostList();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        userJoinDate = LocalDate.now();
        this.role = EnumRole.ROLE_USER;
        postList = new PostList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public LocalDate getUserJoinDate() {
        return userJoinDate;
    }

    public void setUserJoinDate(LocalDate userJoinDate) {
        this.userJoinDate = userJoinDate;
    }

    public PostList getPostList() {
        return postList;
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
    }

    public void addPlantToUserPlants(Plant plant) {
        if (!userPlants.contains(plant)) userPlants.add(plant);
    }

    public boolean hasPlantInUserPlants(Plant plant) {
        return userPlants.contains(plant);
    }

    public CommentBook getUserComments() {
        return userComments;
    }

    public void setUserComments(CommentBook userComments) {
        this.userComments = userComments;
    }

    public boolean removePlantFromPlantList(Plant plant) {
        if (userPlants.contains(plant)) {
            userPlants.remove(plant);
            return true;
        }
        return false;
    }

    public Set<Plant> getUserPlants() {
        return userPlants;
    }

    public void addPlantToWishList(Plant plant) {
        plantWishList.add(plant);
    }

    public boolean hasPlantInWishList(Plant plant) {
        return plantWishList.contains(plant);
    }

    public boolean removePlantFromWishList(Plant plant) {
        if (plantWishList.contains(plant)) {
            plantWishList.remove(plant);
            return true;
        }
        return false;
    }

    public Set<Plant> getPlantWishList() {
        return plantWishList;
    }
}
