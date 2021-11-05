package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {

    boolean existsContentTagByValue(EnumTags value);

    ContentTag findContentTagByValue(EnumTags value);

}
