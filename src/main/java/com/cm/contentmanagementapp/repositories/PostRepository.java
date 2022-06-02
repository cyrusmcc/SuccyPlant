package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

    Post findPostByTitle(String title);

    List<Post> findAllByTitleContaining(String titleLike);

}
