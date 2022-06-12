package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.CommentBook;
import com.cm.contentmanagementapp.models.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByCommentBooksContaining(CommentBook commentBook);
    List<PostComment> findAllByParentCommentAndDepth(PostComment parentComment, int depth);
    List<PostComment> findAllByParentComment(PostComment parentComment);

}
