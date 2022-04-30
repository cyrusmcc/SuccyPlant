package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.Plant;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.AddPlantToUserList;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.payload.response.UserInfoResponse;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.PlantService;
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
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private FileStorageService fileService;

    private UserService userService;

    private PlantService plantService;

    @Autowired
    public UserController(FileStorageService fileStorageService, UserService userService,
                          PlantService plantService) {
        this.fileService = fileStorageService;
        this.userService = userService;
        this.plantService = plantService;
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

    @GetMapping("/user-plants")
    public ResponseEntity<?> getUserPlantList(@RequestParam String username,
                                           @RequestParam String listType) {
        if (!userService.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User does not exist"));
        }

        if (listType.equals("userPlants")) {
            for (Plant p : userService.findByUsername(username).get().getUserPlants())
                System.out.println(p.getPost().getTitle());
            System.out.println(userService.findByUsername(username).get().getUserPlants());
            return ResponseEntity.ok(userService.findByUsername(username).get().getUserPlants());
        }

        if (listType.equals("wishList"))
            return ResponseEntity.ok(userService.findByUsername(username).get().getPlantWishList());

        else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Invalid request"));
    }

    @GetMapping("/has-plant-in-list")
    public ResponseEntity<?> hasPlantInList(@RequestParam Long userId,
                                            @RequestParam Long plantId,
                                            @RequestParam String listType) {

        if (!userService.existsById(userId) || !plantService.existsById(plantId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Invalid request"));
        }

        User user = userService.findById(userId);
        Plant plant = plantService.findById(plantId);
        boolean inList = false;

        if (listType.equals("userPlants")) {
            if (user.hasPlantInUserPlants(plant)) inList = true;
        }
        else if (listType.equals("wishList")) {
            if (user.hasPlantInWishList(plant)) inList = true;
        }

        return ResponseEntity.ok(inList);
    }

    @PostMapping("/add-plant-to-list")
    public ResponseEntity<?> addPlantToUserPlantList(@RequestBody AddPlantToUserList request) {

        if (!userService.existsById(request.getUserId()) || !plantService.existsById(request.getPlantId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Invalid request"));
        }

        try {
            User user = userService.findById(request.getUserId());

            if (request.getListType().equals("userPlants")) {
                user.addPlantToUserPlants(plantService.findById(request.getPlantId()));

            }
            else if (request.getListType().equals("wishList")) {
                user.addPlantToWishList(plantService.findById(request.getPlantId()));
            }

            userService.save(user);
            return ResponseEntity.ok(new MessageResponse("Plant added to " + request.getListType()));

        } catch(Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error encountered while adding plant to " + request.getListType()));
        }
    }

    @PostMapping("/remove-plant-from-list")
    public ResponseEntity<?> removePlantFromList(@RequestParam Long userId,
                                                 @RequestParam Long plantId,
                                                 @RequestParam String listType) {

        if (!userService.existsById(userId) || !plantService.existsById(plantId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Invalid request"));
        }

        try {

            User user = userService.findById(userId);
            Plant plant = plantService.findById(plantId);
            boolean inList = true;

            if (listType.equals("userPlants")) {

                if (!user.hasPlantInUserPlants(plant)) inList = false;

                user.removePlantFromPlantList(plantService.findById(plantId));

            } else if (listType.equals("wishList")) {

                if (!user.hasPlantInWishList(plant)) inList = false;

                user.removePlantFromWishList(plantService.findById(plantId));
            }

            if (!inList) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Plant not found in " + listType));
            }

            userService.save(user);
            return ResponseEntity.ok(new MessageResponse("Plant removed from " + listType));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error encountered while removing plant from " + listType));
        }
    }
}
