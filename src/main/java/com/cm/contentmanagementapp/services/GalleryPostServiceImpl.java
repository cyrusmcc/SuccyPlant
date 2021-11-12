package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.GalleryPost;
import com.cm.contentmanagementapp.repositories.GalleryPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryPostServiceImpl implements GalleryPostService {

    GalleryPostRepository gPRepo;

    @Autowired
    public GalleryPostServiceImpl( GalleryPostRepository gPRepo) {
        this.gPRepo = gPRepo;
    }

    @Override
    public GalleryPost findById(Long postId) {
        if (gPRepo.findById(postId).isPresent())
            return gPRepo.findById(postId).get();

        else return null;
    }

    @Override
    public List<GalleryPost> findAllByContentTags(Integer pageNum, Integer pageSize, List<ContentTag> tags) {

        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Slice<GalleryPost> sliceResult = gPRepo.findGalleryPostsByPostContentTagsIn(tags, paging);

        if (sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GalleryPost> findAllByAlphabetical(Integer pageNum, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Slice<GalleryPost> sliceResult = gPRepo.findAll(paging);

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
    public void save(GalleryPost post) {
        gPRepo.save(post);
    }
}
