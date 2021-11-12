package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTagCategory;
import com.cm.contentmanagementapp.models.GalleryPost;
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
public class GalleryController {

    private GalleryPostService galPostService;

    private ContentTagService tagService;


    @Autowired
    public GalleryController(GalleryPostService galPostService, ContentTagService tagService) {
        this.galPostService = galPostService;
        this.tagService = tagService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getGalleryPosts(@RequestHeader(defaultValue = "0") Integer pageNum,
                                             @RequestHeader(defaultValue = "3") Integer pageSize) {

        List<GalleryPost> posts = galPostService.findAllByAlphabetical(pageNum, pageSize);

        return new ResponseEntity<>(posts, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/get/{tags}")
    public ResponseEntity<?> getGalleryPostsByTags(@RequestHeader(defaultValue = "0") Integer pageNum,
                                                   @RequestHeader(defaultValue = "3") Integer pageSize,
                                                   @PathVariable List<ContentTag> tags) {

        List<GalleryPost> posts = galPostService.findAllByContentTags(pageNum, pageSize, tags);

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
                    System.out.println(value);

                    if (tagService.exists(value, EnumTagCategory.fromString(category))) {
                        tags.add(tagService.findByValueAndCategory(value, EnumTagCategory.fromString(category)));
                    }

                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Attempted to search by improper tag value"));
            }
        }

        List<GalleryPost> posts = galPostService.findAllByContentTags(pageNum, pageSize, tags);

        return new ResponseEntity<>(posts, new HttpHeaders(), HttpStatus.OK);

    }

}
