package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.GalleryPost;

import java.util.List;

public interface GalleryPostService {

    GalleryPost findById(Long postId);

    boolean existsById(Long id);

    void save(GalleryPost post);

    List<GalleryPost> findAllByContentTag(Integer pageNum, Integer pageSize, ContentTag tag);

}
