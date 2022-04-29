package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Plant;
import com.cm.contentmanagementapp.repositories.GalleryPostRepository;
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
public class GalleryPostServiceImpl implements GalleryPostService {

    GalleryPostRepository gPRepo;

    ContentTagService tagService;

    @Autowired
    public GalleryPostServiceImpl(GalleryPostRepository gPRepo, ContentTagService tagService) {
        this.gPRepo = gPRepo;
        this.tagService = tagService;
    }

    @Override
    public Plant findById(Long postId) {
        if (gPRepo.findById(postId).isPresent())
            return gPRepo.findById(postId).get();

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
            plants = gPRepo
                    .findAllByPostTitleContainingIgnoreCase(searchTerm, paging).getContent();
        }

        else if (tags.size() > 0 && searchTerm.length() < 3) {
            plants = gPRepo.findGalleryPostsByPostContentTags(tags, tags.size(), paging).getContent();
        }

        else if (tags.size() > 0 && searchTerm.length() > 3) {
            plants = gPRepo.findGalleryPostsByPostContentTags(tags, tags.size(), paging).getContent();
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

        Slice<Plant> sliceResult = gPRepo.findAll(paging);

        if (sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean existsById(Long id) {
        if (gPRepo.existsById(id)) return true;

        return false;
    }

    @Override
    public void save(Plant post) {
        if (gPRepo.existsGalleryPostByPostTitle(post.getPost().getTitle())) {
            return;
        }
        gPRepo.save(post);
    }
}
