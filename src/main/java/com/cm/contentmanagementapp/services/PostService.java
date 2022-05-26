package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Post;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    void addTag(Post post, ContentTag tag);

    void save(Post post);

    boolean updatePostImg(Post post, String postType, MultipartFile imageFile);

}
