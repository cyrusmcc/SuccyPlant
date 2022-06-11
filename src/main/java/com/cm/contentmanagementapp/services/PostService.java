package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.PostComment;
import com.cm.contentmanagementapp.models.User;
import com.cm.contentmanagementapp.payload.request.NewCommentRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    void addTag(Post post, ContentTag tag);

    void save(Post post);

    boolean updatePostImg(Post post, String postType, MultipartFile imageFile);

    void addComment(Post post, PostComment comment);

    void addComment(Post post, User user, NewCommentRequest commentRequest);

    Post findById(Long id);

    boolean exists(Long id);

}
