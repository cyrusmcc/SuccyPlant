package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Plant;

import java.util.List;

public interface GalleryPostService {

    Plant findById(Long postId);

    boolean existsById(Long id);

    void save(Plant post);

    List<Plant> findAllByContentTagsAndSearchTerm(Integer pageNum, Integer pageSize,
                                                  List<ContentTag> tags, String search);

    List<Plant> findAllByAlphabetical(Integer pageNum, Integer pageSize);


}
