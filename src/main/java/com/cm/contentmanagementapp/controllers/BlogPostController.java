package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.BlogPostRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.PostService;
import com.cm.contentmanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/blog")
public class BlogPostController {

    private PostService postService;

    private UserService userService;

    @Autowired
    public BlogPostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


    @PostMapping("/new-post")
    public ResponseEntity<?> newBlogPost(@Valid @RequestBody BlogPostRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            User user = userService.findByUsername(authentication.getName()).get();

            BlogPost post = new BlogPost();
            post.setAuthorUsername(user.getUsername());
            post.setBodyText(request.getBodyText());
            postService.saveBlog(post);

            return ResponseEntity.ok(new MessageResponse("Blog post created"));

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBlogPost(@PathVariable Long id) {

        if (postService.findById(id) != null) {
            return ResponseEntity.ok(new MessageResponse("exists"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));
    }



}
