package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Post;

public interface PostService {

    void addTag(Post post, ContentTag tag);

    void save(Post post);

}
