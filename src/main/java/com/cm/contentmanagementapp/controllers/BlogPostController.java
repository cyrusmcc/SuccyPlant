package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.BlogPostRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.BlogPostService;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogPostController {

    private BlogPostService blogPostService;

    private UserService userService;

    private FileStorageService fileService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public BlogPostController(BlogPostService blogPostService, UserService userService, FileStorageService fileService) {
        this.blogPostService = blogPostService;
        this.userService = userService;
        this.fileService = fileService;
    }


    @PostMapping("/new-post")
    public ResponseEntity<?> newBlogPost(@Valid @ModelAttribute BlogPostRequest request) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            User user = userService.findByUsername(authentication.getName()).get();

            BlogPost post = new BlogPost();
            post.setTitle(request.getTitle());
            post.setAuthor(request.getAuthorUsername());
            post.setPostList(user.getPostList());

            // save blog text
            blogPostService.updateBlogTextFile(post.getBodyTextFileId(), request.getBodyText());

            // save blog images
            if (!request.getImage().isEmpty()) {
                blogPostService.updateBlogImage(post, request.getImage());
            }

            blogPostService.saveBlog(post);

            return ResponseEntity.ok(new MessageResponse("Blog post created"));

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error encountered while uploading photo."));

    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getBlogPosts(@RequestHeader(defaultValue = "0") Integer pageNum,
                                          @RequestHeader(defaultValue = "3") Integer pageSize) {

        List<BlogPost> list = blogPostService.findAllBlogPosts(pageNum, pageSize);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBlogById(@Valid @PathVariable Long id) {

        if (blogPostService.findBlogPostById(id) != null) {
            return new ResponseEntity<>(blogPostService.findBlogPostById(id), HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));

    }

    @GetMapping("get-image/{id}")
    @ResponseBody
    public ResponseEntity<?> getBlogImage(@PathVariable Long id) {
        try {
            if (!blogPostService.existsById(id)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Blog does not exist"));
            }

            BlogPost post = blogPostService.findBlogPostById(id);
            Path filePath = Paths.get("uploads/blogs/blogImg");
            File file = fileService.load(post.getPost().getImage().getfileName(), filePath).getFile();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            log.info("Failed to load blog image: {}", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Failed to load blog image"));
        }
    }

    @GetMapping("get-text/{id}")
    @ResponseBody
    public ResponseEntity<?> getBlogText(@PathVariable Long id) {

        try {
            if (!blogPostService.existsById(id)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Blog does not exist"));
            }

            BlogPost post = blogPostService.findBlogPostById(id);
            Path filePath = Paths.get("uploads/blogs/blogText");

            File file = fileService.load(post.getBodyTextFileId(), filePath).getFile();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            log.info("Failed to load blog text: {}", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Failed to load blog text"));
        }
    }

}
