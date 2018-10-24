package com.dubooooo.web;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private HttpServletResponse res;
    @Autowired
    private AnnotationConfigServletWebServerApplicationContext ac;

    @RequestMapping("handlerMapping")
    public void handlerMapping() throws Exception {
        System.out.println(handlerMapping);
        HandlerExecutionChain handler = handlerMapping.getHandler(req);
        System.out.println(handler);
        Arrays.stream(handler.getInterceptors()).forEach(e -> {
            System.out.println(e);
        });
    }

    @RequestMapping("beans")
    public void beans() {
        System.out.println(ac);
        Arrays.stream(ac.getBeanDefinitionNames()).forEach(beanName -> {
            System.out.println(beanName);
            System.out.println(ac.getBean(beanName));
        });
        System.out.println("--------------------------------");
        Arrays.stream(ac.getBeanNamesForType(FactoryBean.class)).forEach(beanName -> {
            System.out.println(beanName);
        });
        System.out.println(ac.getBean("&demoRepository"));

    }

}
