package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTags;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.repositories.ContentTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ContentTagServiceImpl implements  ContentTagService{

    private ContentTagRepository tagRepo;

    @Autowired
    public ContentTagServiceImpl(ContentTagRepository contentTagRepository) {
        this.tagRepo = contentTagRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Override
    public void newTag(EnumTags value) {

        if (exists(value)) {
            log.info("Tag by value {} already exists", value);
            throw new IllegalArgumentException();
        }

        ContentTag tag = new ContentTag(value);
        tagRepo.save(tag);

    }

    @Override
    public boolean exists(EnumTags value) {
        return tagRepo.existsContentTagByValue(value);
    }

    @Override
    public ContentTag findByValue(EnumTags value) {

        if (!exists(value)) {
            newTag(value);
        }

        return tagRepo.findContentTagByValue(value);
    }

    @Override
    public void addPostToTag(Post post, ContentTag tag) {

        tag.addPost(post);
        tagRepo.save(tag);

    }
}
