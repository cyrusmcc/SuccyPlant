package com.cm.contentmanagementapp.user;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    User findByUsername(String username);

    void save(User theUser);

    void deleteById(long id);

}
