package com.cm.contentmanagementapp.services;


import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTags;
import com.cm.contentmanagementapp.models.Post;

public interface ContentTagService {

    void newTag(EnumTags value);

    boolean exists(EnumTags value);

    ContentTag findByValue(EnumTags value);

    void addPostToTag(Post post, ContentTag tag);

}
