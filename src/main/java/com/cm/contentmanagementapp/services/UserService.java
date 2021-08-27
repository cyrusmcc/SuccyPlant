package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    Optional<User> findByUsername(String username);

    void save(User theUser);

    void deleteById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
