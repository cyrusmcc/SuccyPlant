package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.PostComment;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    void addTag(Post post, ContentTag tag);

    void save(Post post);

    boolean updatePostImg(Post post, String postType, MultipartFile imageFile);

    void addComment(Post post, PostComment comment);

}
