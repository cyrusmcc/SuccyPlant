package com.cm.contentmanagementapp.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    Optional<User> findByUsername(String username);

    void save(User theUser);

    void deleteById(long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
