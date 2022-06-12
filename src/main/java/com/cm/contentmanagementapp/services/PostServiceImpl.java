package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.NewCommentRequest;
import com.cm.contentmanagementapp.repositories.PostCommentRepository;
import com.cm.contentmanagementapp.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostCommentRepository commentRepository;

    private final UserService userService;

    private final FileStorageService fileService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostCommentRepository commentRepository,
                           FileStorageService fileService, UserService userService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.fileService = fileService;
        this.userService = userService;
    }

    @Override
    public void addTag(Post post, ContentTag tag) {

        Set<ContentTag> postTags = post.getTags();

        if (postTags.contains(tag)) {
            log.info("Post {} already contains tag {}", post.getTitle(), tag.getValue());
            throw new IllegalArgumentException();
        }

        postTags.add(tag);
        postRepository.save(post);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public boolean updatePostImg(Post post, String postType, MultipartFile imageFile) {
        String path = "uploads/post/" + postType;
        Path filePath = Paths.get(path);
        String imgname = "";

        // if image already exists, delete
        if (!post.getImage().getImgName().isEmpty()
                && !post.getImage().getImgPath().isEmpty()) {
            filePath = Paths.get(post.getImage().getImgPath());
            imgname = post.getImage().getImgName();
            fileService.deleteIfExists(filePath, post.getImage().getImgName());
        }

        // Set image path if none exists
        if (post.getImage().getImgPath().isEmpty()) {
            post.getImage().setImgPath(path);
        }

        // Set image name if none exists
        if (post.getImage().getImgName().isEmpty()) {
            String[] fileExtension = Objects
                    .requireNonNull(imageFile.getOriginalFilename())
                    .split("\\.");
            imgname = UUID.randomUUID().toString();
            imgname += "." + fileExtension[1];
            post.getImage().setImgName(imgname);
        }

        try {
            fileService.save(imageFile,filePath,imgname);
            save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addComment(Post post, String authorUsername, NewCommentRequest commentRequest) {
        PostComment comment = new PostComment();
        User user = userService.findByUsername(authorUsername).get();
        comment.setContent(commentRequest.getCommentContent());
        comment.setUser(user);

        if (commentRequest.getReplyToId() >= 0 && commentRequest.getReplyToId() != null) {
            PostComment parentComment =
                    commentRepository.findById(commentRequest.getReplyToId()).get();
            comment.setParentComment(parentComment);
        }
        //TODO: deal w/ comment images later
        /*
        if (commentRequest.getImage() != null) {
            Image image = new Image();
        }
        */

        comment.addCommentBook(user.getUserComments());
        comment.addCommentBook(post.getCommentBook());
        commentRepository.save(comment);

        user.addComment(comment);
        userService.save(user);

        post.addComment(comment);
        save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public boolean exists(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public List<PostComment> getPostCommentsByTimeDesc(Post post) {
        return commentRepository.findTop20ByCommentBooksContainingOrderByTimestampDesc(post.getCommentBook());
    }


}
