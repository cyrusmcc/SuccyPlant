package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.PostComment;
import com.cm.contentmanagementapp.repositories.CommentBookRepository;
import com.cm.contentmanagementapp.repositories.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentBookServiceImpl implements CommentBookService {

    private PostCommentRepository commentRepository;

    private CommentBookRepository commentBookRepository;

    @Autowired
    public CommentBookServiceImpl(PostCommentRepository commentRepository, CommentBookRepository commentBookRepository) {
        this.commentRepository = commentRepository;
        this.commentBookRepository = commentBookRepository;
    }

    @Override
    public void newComment(Post post, PostComment parentComment, String content) {
        PostComment comment = new PostComment(parentComment, content);
        post.getCommentBook().addComment(comment);
        commentRepository.save(comment);
    }

}
