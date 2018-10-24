package com.dubooooo.repository;

import com.dubooooo.entity.Demo;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends BaseRepository<Demo, String> {
}
