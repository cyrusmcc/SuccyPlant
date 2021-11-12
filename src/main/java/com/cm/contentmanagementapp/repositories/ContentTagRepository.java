package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.ContentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {

    boolean existsContentTagByValueAndCategory(String value, String category);

    ContentTag findContentTagByValueAndCategory(String value, String category);

    List<ContentTag> findContentTagsByCategory(String category);

    List<ContentTag> findContentTagsByCategoryAndValue(String category, String value);

}
