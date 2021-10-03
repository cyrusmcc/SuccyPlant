package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.BlogPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    Slice<BlogPost> findByPostDate(String postDate, Pageable pageable);

}
