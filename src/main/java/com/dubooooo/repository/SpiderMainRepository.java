package com.dubooooo.repository;

import com.dubooooo.entity.SpiderMainEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpiderMainRepository extends BaseRepository<SpiderMainEntity, Long> {

    SpiderMainEntity findFirstByStatus(String status);

    List<SpiderMainEntity> findByTitleIsNullAndHtmlIsNotNull(Pageable pageable);

    int countByUrl(String url);

}
