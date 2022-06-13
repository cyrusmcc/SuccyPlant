package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.CommentBook;
import com.cm.contentmanagementapp.models.PostComment;
import com.cm.contentmanagementapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByCommentBooksContaining(CommentBook commentBook);
    List<PostComment> findTop20ByCommentBooksContainingOrderByTimestampAsc(CommentBook commentBook);
    List<PostComment> findAllByParentCommentAndDepth(PostComment parentComment, int depth);
    List<PostComment> findAllByParentComment(PostComment parentComment);
    PostComment findTopByUserOrderByTimestampDesc(User user);
}
