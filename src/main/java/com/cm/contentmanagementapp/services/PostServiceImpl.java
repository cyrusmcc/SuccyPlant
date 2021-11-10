package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
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
}
