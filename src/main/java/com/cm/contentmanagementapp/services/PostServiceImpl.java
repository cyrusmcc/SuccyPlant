package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private FileStorageService fileService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public PostServiceImpl(PostRepository postRepository, FileStorageService fileService) {
        this.postRepository = postRepository;
        this.fileService = fileService;
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
        String fileName = "";

        // if image already exists, delete
        if (!post.getImage().getfileName().isEmpty()
                && !post.getImage().getFilePath().isEmpty()) {
            filePath = Paths.get(post.getImage().getFilePath());
            fileName = post.getImage().getfileName();
            fileService.deleteIfExists(filePath, post.getImage().getfileName());
        }

        // Set image path if none exists
        if (post.getImage().getFilePath().isEmpty()) {
            post.getImage().setFilePath(path);
        }

        // Set image name if none exists
        if (post.getImage().getfileName().isEmpty()) {
            String[] fileExtension = Objects
                    .requireNonNull(imageFile.getOriginalFilename())
                    .split("\\.");
            fileName = UUID.randomUUID().toString();
            fileName += "." + fileExtension[1];
            post.getImage().setfileName(fileName);
        }

        try {
            fileService.save(imageFile,filePath,fileName);
            save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
