package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.PostComment;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.NewCommentRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.PostService;
import com.cm.contentmanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public CommentController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/get-comments")
    public ResponseEntity<?> getPostComments(@Valid @PathVariable Long postId) {
        if (!postService.exists(postId)) {
            return ResponseEntity.badRequest()
                    .body("No post found with ID" + postId + " found.");
        }

        Post post = postService.findById(postId);
        List<PostComment> comments = post.getCommentBook().getComments();

        return new ResponseEntity<>(comments, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/new-comment")
    public ResponseEntity<?> newPostComment(@Valid @ModelAttribute NewCommentRequest commentRequest,
                                            Principal principal) {

        if (!postService.exists(commentRequest.getPostId())) {
            return ResponseEntity.badRequest()
                    .body("No post found with ID" + commentRequest.getPostId() + " found.");
        }

        if (principal == null) {
            return ResponseEntity.badRequest()
                    .body("Invalid request, try re-logging and submitting comment again.");
        }

        // Use principal to retrieve username instead of including as param in commentRequest
        // so api cannot be provided different user's username
        User user = userService.findByUsername(principal.getName()).get();
        Post post = postService.findById(commentRequest.getPostId());

        postService.addComment(post, user, commentRequest);

        return ResponseEntity.ok(new MessageResponse("Comment posted successfully."));
    }
}
