package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Plant;
import com.cm.contentmanagementapp.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

    PlantRepository plantRepo;

    ContentTagService tagService;

    @Autowired
    public PlantServiceImpl(PlantRepository plantRepo, ContentTagService tagService) {
        this.plantRepo = plantRepo;
        this.tagService = tagService;
    }

    @Override
    public Plant findById(Long plantId) {
        if (plantRepo.findById(plantId).isPresent())
            return plantRepo.findById(plantId).get();

        else return null;
    }

    @Override
    public List<Plant> findAllByContentTagsAndSearchTerm(Integer pageNum, Integer pageSize,
                                                         List<ContentTag> tags, String searchTerm) {

        for (ContentTag t : tags) System.out.println(t.getCategory());
        if (tags.size() == 0 && (searchTerm == null || searchTerm.length() < 3)) {
            return findAllByAlphabetical(pageNum, pageSize);
        }

        Pageable paging = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Plant> plants = new ArrayList<>();

        if (tags.size() == 0 && searchTerm.length() > 3) {
            System.out.println(searchTerm);
            plants = plantRepo
                    .findAllByPostTitleContainingIgnoreCase(searchTerm, paging).getContent();
        }

        else if (tags.size() > 0 && searchTerm.length() < 3) {
            plants = plantRepo.findGalleryPostsByPostContentTags(tags, tags.size(), paging).getContent();
        }

        else if (tags.size() > 0 && searchTerm.length() > 3) {
            plants = plantRepo.findGalleryPostsByPostContentTags(tags, tags.size(), paging).getContent();
            plants =
                    plants
                            .stream()
                            .filter(p -> p.getPost().getTitle().contains(searchTerm))
                            .collect(Collectors.toList());
        }

        return plants;
    }

    @Override
    public List<Plant> findAllByAlphabetical(Integer pageNum, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Slice<Plant> sliceResult = plantRepo.findAll(paging);

        if (sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Plant> findAllRelated(Integer pageNum, Integer pageSize, Long plantId) {
        Plant plant = findById(plantId);
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        // Split genus from plant's scientific denotation
        String[] scientificName = plant.getScientificName().split(" ");
        String plantGenus = scientificName[0];
        // list of plants to ignore (by id_ in repo query
        List<Long> plantIds = new ArrayList<>();
        plantIds.add(plant.getId());
        Slice<Plant> sliceResult = plantRepo
                .findAllByPostTitleContainingIgnoreCaseAndIdNotIn(plantGenus, paging, plantIds);

        if (!sliceResult.hasContent()) {
            sliceResult = plantRepo.findPlantsByTypeAndIdNotIn(plant.getType(), paging, plantIds);
        }

        if (sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return plantRepo.existsById(id);
    }

    @Override
    public boolean existsByScienceName(String scienceName) {
        return plantRepo.existsByScientificName(scienceName);
    }

    @Override
    public void save(Plant plant) {
        if (plantRepo.existsGalleryPostByPostTitle(plant.getPost().getTitle())) {
            return;
        }
        plantRepo.save(plant);
    }
}
