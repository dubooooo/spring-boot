package com.dubooooo.test;

import com.dubooooo.web.TestController;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;

public class AnnotatedGenericBeanDefinitionTest01 {

    public static void main(String[] args) {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(TestController.class);
        System.out.println(beanDefinition);
        System.out.println(beanDefinition.getDependsOn());
    }

}
