package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.GalleryPost;

public interface GalleryPostService {

    GalleryPost findById(Long postId);

    boolean existsById(Long id);

    void save(GalleryPost post);

}
