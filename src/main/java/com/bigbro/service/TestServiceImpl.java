package com.bigbro.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testService")
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl implements TestService {
    public void test() {
        System.out.print("spring!!!!!!!!");
    }
}
