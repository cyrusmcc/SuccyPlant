package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.CommentBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentBookRepository extends JpaRepository<CommentBook, Long> {

}
