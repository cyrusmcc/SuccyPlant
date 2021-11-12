package com.cm.contentmanagementapp.services;


import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTagCategory;
import com.cm.contentmanagementapp.models.Post;

import java.util.List;

public interface ContentTagService {

    void newTag(String value, EnumTagCategory category);

    boolean exists(String value, EnumTagCategory category);

    ContentTag findByValueAndCategory(String value, EnumTagCategory category);

    List<ContentTag> findAllByCategory(EnumTagCategory category);

    List<ContentTag> findContentTagsByCategoryAndValue(EnumTagCategory category, String value);

    void addPostToTag(Post post, ContentTag tag);

}
