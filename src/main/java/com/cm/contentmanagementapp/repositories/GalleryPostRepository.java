package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.GalleryPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryPostRepository extends JpaRepository<GalleryPost, Long> {

    Slice<GalleryPost> findGalleryPostsByPostContentTagsIn(List<ContentTag> tags, Pageable paging);

}