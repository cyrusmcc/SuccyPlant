package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

}
