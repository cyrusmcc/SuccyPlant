package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.EnumRole;
import com.cm.contentmanagementapp.models.Role;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.repositories.RoleRepository;
import com.cm.contentmanagementapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository, RoleRepository theRoleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = theUserRepository;
        this.roleRepository = theRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {

        Optional<User> result = userRepository.findById(id);

        User theUser;

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
    public User findByEmail(String email) {

        if (userRepository.findByEmail(email) != null) {

            return userRepository.findByEmail(email);

        } else {
            throw new UsernameNotFoundException("email " + email + " Not found.");
        }

    }

    @Override
    public void newUser(User user) {

        user.setId(0L);

        Role userRole = roleRepository.findByName(EnumRole.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setUserJoinDate(LocalDate.now());

        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String password) {

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

    }

    @Override
    public void deleteById(Long id) {
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
