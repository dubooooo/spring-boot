package com.dubooooo.repository;

import com.dubooooo.entity.SourceEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceEntityRepository extends BaseRepository<SourceEntity, Long> {

    int countByUrl(String url);

}
