package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.EnumTagCategory;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.repositories.ContentTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTagServiceImpl implements  ContentTagService{

    private ContentTagRepository tagRepo;

    @Autowired
    public ContentTagServiceImpl(ContentTagRepository contentTagRepository) {
        this.tagRepo = contentTagRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Override
    public void newTag(String value, EnumTagCategory category) {

        if (exists(value, category)) {
            log.info("Tag by value {} in category {} already exists", value, category);
            return;
        }

        ContentTag tag = new ContentTag(value, category);
        tagRepo.save(tag);
    }

    @Override
    public boolean exists(String value, EnumTagCategory category) {
        return tagRepo.existsContentTagByValueAndCategory(value, category.getText());
    }

    @Override
    public ContentTag findByCategoryAndValue(EnumTagCategory category, String value) {
        if (!exists(value, category)) {
            newTag(value, category);
        }

        return tagRepo.findContentTagByValueAndCategory(value, category.getText());
    }

    @Override
    public List<ContentTag> findAllByCategory(EnumTagCategory category) {
        return tagRepo.findContentTagsByCategory(category.getText());
    }

    @Override
    public List<ContentTag> findContentTagsByCategoryAndValue(EnumTagCategory category, String value) {
        return tagRepo.findContentTagsByCategoryAndValue(category.getText(), value);
    }

    @Override
    public void addPostToTag(Post post, ContentTag tag) {

        tag.addPost(post);
        tagRepo.save(tag);

    }
}
