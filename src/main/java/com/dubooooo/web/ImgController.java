package com.dubooooo.web;

import com.dubooooo.common.Result;
import com.dubooooo.entity.ImageEntity;
import com.dubooooo.repository.ImageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    ImageEntityRepository imageEntityRepository;

    @RequestMapping("/get")
    public Result get(int page, int size) {
        Page<ImageEntity> all = imageEntityRepository.findAll(PageRequest.of(page, size));
        return new Result(all);
    }

}
