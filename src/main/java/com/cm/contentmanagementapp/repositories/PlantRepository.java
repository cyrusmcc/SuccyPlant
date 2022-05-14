package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.ContentTag;
import com.cm.contentmanagementapp.models.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    Page<Plant> findPlantsByPostContentTagsIn(List<ContentTag> tags, Pageable paging);

    Page<Plant> findAllByPostTitleContainingIgnoreCase(String postTitleLike, Pageable pageable);

    @Query("select i from Plant i join i.post.contentTags t " +
            "where t in :tags group by i.id having count(i.id) = :tagCount")
    Page<Plant> findGalleryPostsByPostContentTags(@Param("tags") List<ContentTag> tags,
                                                  @Param("tagCount") long tagCount, Pageable paging);

    List<Plant> findPlantsByIdInAndPostContentTagsIn(List<Long> ids, List<ContentTag> tags);

    boolean existsGalleryPostByPostTitle(String title);

    boolean existsByScientificName(String scienceName);

}
