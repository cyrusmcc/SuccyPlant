package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTagCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {

    boolean existsContentTagByValueAndCategory(String value, EnumTagCategory category);

    ContentTag findContentTagByValueAndCategory(String value, EnumTagCategory category);

    List<ContentTag> findContentTagsByCategory(EnumTagCategory category);

}
