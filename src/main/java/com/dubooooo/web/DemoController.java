package com.dubooooo.web;

import com.dubooooo.entity.Demo;
import com.dubooooo.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoRepository demoRepository;

    @RequestMapping("/test01")
    public void test01() {
        Demo demo = new Demo();
        demo.age = "18";
        demo.name = "zs";
        demoRepository.save(demo);
    }

    @RequestMapping("/test02")
    public void test02() {
        Demo demo = new Demo();
        demo.age = "18";
        demo.name = "zs";
        demoRepository.findAll(new Specification<Demo>() {
            @Override
            public Predicate toPredicate(Root<Demo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        });
    }

}
