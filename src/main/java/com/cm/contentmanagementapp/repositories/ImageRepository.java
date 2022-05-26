package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImgById(Long id);

}
