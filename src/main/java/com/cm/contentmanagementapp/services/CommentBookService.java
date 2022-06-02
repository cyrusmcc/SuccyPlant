package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.PostComment;

public interface CommentBookService {

    void newComment(Post post, PostComment parentComment, String content);

    //CommentB
}
