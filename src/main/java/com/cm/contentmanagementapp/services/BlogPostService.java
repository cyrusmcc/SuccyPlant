package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogPostService {

    Post findById(Long postId);

    boolean existsById(Long id);

    List<BlogPost> findAllBlogPosts(Integer pageNum, Integer pageSize);

    BlogPost findBlogPostById(Long id);

    void saveBlog(BlogPost post);

    boolean updateBlogTextFile(String fileId, String bodyText);

    boolean updateBlogImage(BlogPost blogPost, MultipartFile multipartFile);

}
