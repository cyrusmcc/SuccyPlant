package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.GalleryPost;

import java.util.List;

public interface GalleryPostService {

    GalleryPost findById(Long postId);

    boolean existsById(Long id);

    void save(GalleryPost post);

    List<GalleryPost> findAllByContentTagsAndSearchTerm(Integer pageNum, Integer pageSize,
                                                        List<ContentTag> tags, String search);

    List<GalleryPost> findAllByAlphabetical(Integer pageNum, Integer pageSize);


}
