package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Plant;

import java.util.List;

public interface PlantService {

    Plant findById(Long plantId);

    boolean existsById(Long id);

    boolean existsByScienceName(String scienceName);

    void save(Plant plant);

    List<Plant> findAllByContentTagsAndSearchTerm(Integer pageNum, Integer pageSize,
                                                  List<ContentTag> tags, String search);

    List<Plant> findAllByAlphabetical(Integer pageNum, Integer pageSize);

    List<Plant> findAllRelated(Integer pageNum, Integer pageSize, Long plantId);
}
