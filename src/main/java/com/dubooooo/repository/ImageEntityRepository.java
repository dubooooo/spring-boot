package com.dubooooo.repository;

import com.dubooooo.entity.ImageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageEntityRepository extends BaseRepository<ImageEntity, Long> {

    int countByUrl(String url);

}
