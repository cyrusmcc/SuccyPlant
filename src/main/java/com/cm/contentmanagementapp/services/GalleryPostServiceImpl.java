package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.GalleryPost;
import com.cm.contentmanagementapp.repositories.GalleryPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean existsById(Long id) {
        if (gPRepo.existsById(id)) return true;

        return false;
    }

    @Override
    public void save(GalleryPost post) {
        gPRepo.save(post);
    }
}
