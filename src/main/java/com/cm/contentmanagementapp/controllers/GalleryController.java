package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.GalleryPost;
import com.cm.contentmanagementapp.services.GalleryPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private GalleryPostService galPostService;


    @Autowired
    public GalleryController(GalleryPostService galPostService) {
        this.galPostService = galPostService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getGalleryPosts(@RequestHeader(defaultValue = "0") Integer pageNum,
                                             @RequestHeader(defaultValue = "3") Integer pageSize) {

        List<GalleryPost> list = galPostService.findAllByAlphabetical(pageNum, pageSize);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/get/{tags}")
    public ResponseEntity<?> getGalleryPostsByTags(@RequestHeader(defaultValue = "0") Integer pageNum,
                                                   @RequestHeader(defaultValue = "3") Integer pageSize,
                                                   @PathVariable List<ContentTag> tags) {

        List<GalleryPost> list = galPostService.findAllByContentTags(pageNum, pageSize,tags);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);

    }

}
