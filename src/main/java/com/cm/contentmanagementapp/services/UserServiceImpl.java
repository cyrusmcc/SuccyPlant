package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    FileStorageService fileService;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository,
                           PasswordEncoder passwordEncoder, FileStorageService fileService) {
        this.userRepository = theUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
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
    public boolean existsById(Long id) {
        if (userRepository.existsById(id)) return true;
        else return false;
    }

    @Override
    public boolean updateProfilePicture(User user, MultipartFile file) {

        Path filePath = Paths.get("uploads/profilePictures");

        if (user.getProfileImageUrl() != null) {
            String oldFilePath = user.getProfileImageUrl();
            fileService.deleteIfExists(filePath, oldFilePath);
        }

        try {
            String[] fileExtension = file.getOriginalFilename().split("\\.");
            String fileId = UUID.randomUUID().toString();
            fileId += "." + fileExtension[1];

            user.setProfileImageUrl(fileId);
            save(user);
            fileService.save(file, filePath, fileId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

        user.setUserJoinDate(LocalDate.now());
        user.setProfileImageUrl("default-user.png");

        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String password) {

        String encodedPass = passwordEncoder.encode(password);
        user.setPassword(encodedPass);
        userRepository.save(user);

    }

    @Override
    public boolean isValidPassword(User user, String password) {

        if (passwordEncoder.matches(password, user.getPassword()))
            return true;

        return false;
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
