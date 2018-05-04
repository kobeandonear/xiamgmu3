package com.njwb.service;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestServiceImplTest {
    @Test
    public void test1() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-config.xml"});
        TestService testService = (TestService) applicationContext.getBean("testService");
        testService.test();
    }
}
