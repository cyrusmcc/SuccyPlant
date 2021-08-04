package com.cm.contentmanagementapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository) {
        userRepository = theUserRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {

        Optional<User> result = userRepository.findById(id);

        User theUser = null;

        if (result.isPresent()) {

            theUser = result.get();

        } else {
            throw new RuntimeException("User " + id + " not found");
        }

        return theUser;
    }

    @Override
    public User findByUsername(String username) {

        User user = null;

        if (userRepository.findByUsername(username) != null) {

            user = userRepository.findByUsername(username);

        } else {
            throw new UsernameNotFoundException("User " + username + " Not found.");
        }

        return user;
    }

    @Override
    public void save(User user) {

        user.setId(0);

        String password = user.getPassword();

        String encryptedPwd = passwordEncoder.encode(password);

        user.setPassword(encryptedPwd);

        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
