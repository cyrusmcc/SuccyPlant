package com.cm.contentmanagementapp.controllers;


import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.payload.response.UserInfoResponse;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUserProfileInfo(@PathVariable String username) {

        if (!userService.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User does not exist"));
        }

        return ResponseEntity.ok(new UserInfoResponse(
                username,
                userService.findByUsername(username).get().getUserJoinDate()
        ));

    }

    @GetMapping("/get-image/{username}")
    @ResponseBody
    public ResponseEntity<?> getUserProfilePicture(@PathVariable String username) throws IOException {

        if (!userService.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User does not exist"));
        }

        User user = userService.findByUsername(username).get();
        Path filePath = Paths.get("uploads/profilePictures");

        if (fileService.load(user.getProfileImageUrl(), filePath) == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Profile picture failed to load."));
        }

        File file = fileService.load(user.getProfileImageUrl(), filePath).getFile();


        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                .body(Files.readAllBytes(file.toPath()));
    }
}
