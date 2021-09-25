package com.cm.contentmanagementapp.controllers;


import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.payload.response.UserInfoResponse;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private FileStorageService fileService;

    private UserService userService;

    @Autowired
    public UserController(FileStorageService fileStorageService, UserService userService) {
        this.fileService = fileStorageService;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> loadUser(@PathVariable String username) {

        if (!userService.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User does not exist"));
        }

        User user = userService.findByUsername(username).get();
        Path filePath = Paths.get("uploads/profilePictures");

        /*
        return ResponseEntity.ok(new UserInfoResponse(
                new ServletContextResource(filePath, user.getProfileImageId());
                username,
                user.getUserJoinDate()
        ));
        */
    }


}
