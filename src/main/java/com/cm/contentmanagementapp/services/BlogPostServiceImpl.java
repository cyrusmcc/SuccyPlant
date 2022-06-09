package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.SettingsController;
import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.repositories.BlogPostRepository;
import com.cm.contentmanagementapp.repositories.PostListRepository;
import com.cm.contentmanagementapp.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BlogPostServiceImpl implements BlogPostService {


    private PostRepository postRepository;

    private BlogPostRepository blogPostRepository;

    private FileStorageService fileService;

    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);


    @Autowired
    public BlogPostServiceImpl(PostRepository postRepository, PostListRepository postListRepository,
                               BlogPostRepository blogPostRepository, FileStorageService fileStorageService) {
        this.postRepository = postRepository;
        this.blogPostRepository = blogPostRepository;
        this.fileService = fileStorageService;
    }

    @Override
    public Post findById(Long postId) {

        if(postRepository.existsById(postId))
            return postRepository.findById(postId).get();
        else {
            log.info("Can not find blog by id: {}", postId);
            return null;
        }
    }

    @Override
    public boolean existsById(Long id) {
        if (blogPostRepository.existsById(id))
            return true;
        return false;
    }

    @Override
    public List<BlogPost> findAllBlogPosts(Integer pageNum, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Slice<BlogPost> sliceResult = blogPostRepository.findAll(paging);

        if (sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public BlogPost findBlogPostById(Long id) {
        return blogPostRepository.findById(id).get();
    }

    @Override
    public void saveBlog(BlogPost post) {
        blogPostRepository.save(post);
    }

    @Override
    public boolean updateBlogTextFile(String fileId, String bodyText) {

        try {

            Path filePath = Paths.get("uploads/blogs/blogText");
            fileService.saveString(bodyText, filePath, fileId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBlogImage(BlogPost blogPost, MultipartFile file) {
        Path filePath = Paths.get("uploads/blogs/blogImg");

        if (blogPost.getPost().getImage().getImgName() != null
                && !blogPost.getPost().getImage().getImgName().isEmpty()) {
            fileService.deleteIfExists(filePath, blogPost.getPost().getImage().getImgName());
        }

        try {
            String[] fileExtension = Objects
                    .requireNonNull(file.getOriginalFilename())
                    .split("\\.");
            String fileId = UUID.randomUUID().toString();
            fileId += "." + fileExtension[1];

            blogPost.setPostImageId(fileId);
            fileService.save(file,filePath,fileId);
            saveBlog(blogPost);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
