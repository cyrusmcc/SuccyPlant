package com.cm.contentmanagementapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository, RoleRepository theRoleRepository) {
        this.userRepository = theUserRepository;
        this.roleRepository = theRoleRepository;
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
    public Optional<User> findByUsername(String username) {

        Optional<User> user = null;

        if (userRepository.findByUsername(username) != null) {

            user = userRepository.findByUsername(username);

        } else {
            throw new UsernameNotFoundException("User " + username + " Not found.");
        }

        return user;
    }

    @Override
    public void save(User user) {

        user.setId(0L);

        Role userRole = roleRepository.findByName(EnumRole.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setUserRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return userRepository.existsByEmail(email);
    }
}
