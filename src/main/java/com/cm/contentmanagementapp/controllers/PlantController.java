package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTagCategory;
import com.cm.contentmanagementapp.models.Plant;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.ContentTagService;
import com.cm.contentmanagementapp.services.GalleryPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gallery")
public class PlantController {

    private GalleryPostService galPostService;

    private ContentTagService tagService;

    @Autowired
    public PlantController(GalleryPostService galPostService, ContentTagService tagService) {
        this.galPostService = galPostService;
        this.tagService = tagService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getGalleryPosts(@RequestHeader(defaultValue = "0") Integer pageNum,
                                             @RequestHeader(defaultValue = "5") Integer pageSize,
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

        List<Plant> posts = galPostService.findAllByContentTagsAndSearchTerm(pageNum, pageSize,
                tagList, searchTerm);

        for (Plant gp : posts) System.out.println(gp.getPost().getTitle());
        System.out.println(posts);

        return new ResponseEntity<>(posts, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/get/{tags}")
    public ResponseEntity<?> getGalleryPostsByTags(@RequestHeader(defaultValue = "0") Integer pageNum,
                                                   @RequestHeader(defaultValue = "3") Integer pageSize,
                                                   @PathVariable List<ContentTag> tags) {

        List<Plant> posts = galPostService.findAllByContentTagsAndSearchTerm(pageNum, pageSize, tags, "");

        return new ResponseEntity<>(posts, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/get-tags-by-category/{category}")
    public ResponseEntity<?> getTagsByCategory(@PathVariable String category) {

        if (tagService.findAllByCategory(EnumTagCategory.fromString(category)).size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No posts found for tag " + category));
        }

        List<String> tagValues = tagService
                .findAllByCategory(EnumTagCategory.fromString(category))
                .stream()
                .map(ContentTag::getValue)
                .collect(Collectors.toList());

        for (String s : tagValues) {
            System.out.println(s);
        }

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

        List<Plant> posts = galPostService.findAllByContentTagsAndSearchTerm(pageNum, pageSize, tags, "");

        return new ResponseEntity<>(posts, new HttpHeaders(), HttpStatus.OK);

    }

}
