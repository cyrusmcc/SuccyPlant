package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.BlogPostRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.BlogPostService;
import com.cm.contentmanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    public BlogPostController(BlogPostService blogPostService, UserService userService) {
        this.blogPostService = blogPostService;
        this.userService = userService;
    }


    @PostMapping("/new-post")
    public ResponseEntity<?> newBlogPost(@Valid @ModelAttribute BlogPostRequest request) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            User user = userService.findByUsername(authentication.getName()).get();

            BlogPost post = new BlogPost();
            post.setPostTitle(request.getTitle());
            post.setPostAuthor(request.getAuthorUsername());
            post.setPostList(user.getPostList());

            // save blog text
            blogPostService.updateBlogTextFile(post.getBodyTextFileId(), request.getBodyText());

            // save blog images
            if (!request.getImage().isEmpty()) {

            }

            blogPostService.saveBlog(post);

            return ResponseEntity.ok(new MessageResponse("Blog post created"));

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));

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

}
