package com.cm.contentmanagementapp.user;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    void save(User theUser);

    void deleteById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
