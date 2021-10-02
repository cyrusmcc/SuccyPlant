package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.models.PostList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostListRepository  extends JpaRepository<PostList, Long> {

    List<Post> findAllByPostListId(Long id);

}
