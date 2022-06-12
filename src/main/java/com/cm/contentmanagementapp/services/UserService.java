package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.PostComment;
import com.cm.contentmanagementapp.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    void newUser(User theUser);

    void save(User theUser);

    void updatePassword(User user, String password);

    boolean isValidPassword(User user, String password);

    void deleteById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    boolean updateProfilePicture(User user, MultipartFile file);

    PostComment findLastComment(User user);

}
