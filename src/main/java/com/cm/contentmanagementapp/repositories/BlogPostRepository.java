package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
