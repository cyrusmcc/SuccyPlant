package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.ContentTagService;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    private PlantService plantService;

    private ContentTagService tagService;

    private FileStorageService fileStorageService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public PlantController(PlantService plantService, ContentTagService tagService,
                           FileStorageService fileStorageService) {
        this.plantService = plantService;
        this.tagService = tagService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/get-by-id/{plantId}")
    public ResponseEntity<?> getPlantById(@PathVariable Long plantId) {

        Plant plant = plantService.findById(plantId);

        if (plant != null) {
            return new ResponseEntity<>(plant, new HttpHeaders(), HttpStatus.OK);
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("No plant with ID " + plantId + " found") );
    }

    /*
     * Returns a list of plants related to plant w/ id equal to plantId,
     *  where related plants share genus, if no common genus plants found,
     *  returns plants related by type (house plant, succ, cactus)
     * */
    @GetMapping("/get-related/{plantId}")
    public ResponseEntity<?> getRelatedPlants(@RequestHeader(defaultValue = "0") Integer pageNum,
                                              @RequestHeader(defaultValue = "6") Integer pageSize,
                                              @PathVariable Long plantId) {

        try {
            List<Plant> plants = plantService.findAllRelated(pageNum, pageSize,
                    plantId);

            return new ResponseEntity<>(plants, new HttpHeaders(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error encountered while fetching related plants"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getGalleryplants(@RequestHeader(defaultValue = "0") Integer pageNum,
                                             @RequestHeader(defaultValue = "18") Integer pageSize,
                                             @RequestParam(required = false) String searchTerm,
                                             @RequestParam(required = false) String tags) {

        List<ContentTag> tagList = new ArrayList<>();

        if (tags != null && !tags.isEmpty()) {
            List<String> tagCategories = List.of(tags.split("&"));
            tagList = new ArrayList<>();

            for(String s : tagCategories) {
                String[] split = s.split("=");

                for (String t : split[1].split(",")) {
                    tagList.add(tagService.findByCategoryAndValue(EnumTagCategory.fromString(split[0]), t));
                }
            }
        }

        List<Plant> plants = plantService.findAllByContentTagsAndSearchTerm(pageNum, pageSize,
                tagList, searchTerm);

        return new ResponseEntity<>(plants, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/get/{tags}")
    public ResponseEntity<?> getGalleryplantsByTags(@RequestHeader(defaultValue = "0") Integer pageNum,
                                                   @RequestHeader(defaultValue = "3") Integer pageSize,
                                                   @PathVariable List<ContentTag> tags) {

        List<Plant> plants = plantService.findAllByContentTagsAndSearchTerm(pageNum, pageSize, tags, "");

        return new ResponseEntity<>(plants, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/get-tags-by-category/{category}")
    public ResponseEntity<?> getTagsByCategory(@PathVariable String category) {

        if (tagService.findAllByCategory(EnumTagCategory.fromString(category)).size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No plants found for tag " + category));
        }

        List<String> tagValues = tagService
                .findAllByCategory(EnumTagCategory.fromString(category))
                .stream()
                .map(ContentTag::getValue)
                .collect(Collectors.toList());

        return new ResponseEntity<>(tagValues, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping({"/plants", "/plants/"})
    public ResponseEntity<?> getByMapOfCategoryAndTags(@RequestHeader(defaultValue = "0") Integer pageNum,
                                                       @RequestHeader(defaultValue = "3") Integer pageSize,
                                                       @RequestParam Map<String, String> allParams) {

        Set<String> keys = allParams.keySet();
        List<ContentTag> tags = new ArrayList<>();

        for (String category : keys) {

            try {

                List<String> values = new ArrayList<>();
                values.addAll(Arrays.asList(allParams.get(category).split(",")));

                for (String value : values) {

                    value = value.replace("+", " ");
                    if (tagService.exists(value, EnumTagCategory.fromString(category))) {
                        tags.add(tagService.findByCategoryAndValue(EnumTagCategory.fromString(category), value));
                    }

                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Attempted to search by improper tag value"));
            }
        }

        List<Plant> plants = plantService.findAllByContentTagsAndSearchTerm(pageNum, pageSize, tags, "");

        return new ResponseEntity<>(plants, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("get-image/{id}")
    @ResponseBody
    public ResponseEntity<?> getPlantImageById(@PathVariable Long id) {
        try {
            if (!plantService.existsById(id)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Plant does not exist"));
            }

            Plant plant = plantService.findById(id);
            Image plantImg = plant.getPost().getImage();
            Path imgPath = Paths.get(plantImg.getFilePath());
            File file = fileStorageService.load(plantImg.getfileName(), imgPath).getFile();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            log.info("Failed to load blog image: {}", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Failed to load blog image"));
        }
    }

}
