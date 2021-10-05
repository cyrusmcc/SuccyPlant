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

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{


    private PostRepository postRepository;

    private BlogPostRepository blogPostRepository;

    private PostListRepository postListRepository;

    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);


    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostListRepository postListRepository,
                           BlogPostRepository blogPostRepository) {
        this.postRepository = postRepository;
        this.postListRepository = postListRepository;
        this.blogPostRepository = blogPostRepository;
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
    public List<BlogPost> findAllBlogPosts(Integer pageNum, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Slice<BlogPost> sliceResult = blogPostRepository.findAll(paging);

        if (sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<BlogPost>();
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

}
