package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.BlogPostRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.PostService;
import com.cm.contentmanagementapp.services.UserService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
            post.getPost().setAuthorUsername(user.getUsername());
            post.setBodyText(request.getBodyText());
            post.getPost().setPostList(user.getPostList());
            postService.saveBlog(post);

            return ResponseEntity.ok(new MessageResponse("Blog post created"));

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));

    }

    @GetMapping("/get-all")
    //TODO fix ddos thing
    public ResponseEntity<?> getBlogPosts(@RequestHeader(defaultValue = "0") Integer pageNum,
                                          @RequestHeader(defaultValue = "3") Integer pageSize) {

        List<BlogPost> list = postService.findAllBlogPosts(pageNum, pageSize);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBlogById(@Valid @PathVariable Long id) {

        if (postService.findBlogPostById(id) != null) {
            return new ResponseEntity<>(postService.findBlogPostById(id), HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));

    }

}
