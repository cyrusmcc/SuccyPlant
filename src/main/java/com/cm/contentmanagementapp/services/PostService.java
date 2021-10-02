package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.BlogPost;
import com.cm.contentmanagementapp.models.Post;

import java.util.List;

public interface PostService {

    Post findById(Long postId);

    List<Post> findAllByPostListId(Long postListId);

    void saveBlog(BlogPost post);

}
