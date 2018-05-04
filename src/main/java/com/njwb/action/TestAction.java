package com.njwb.action;


import com.njwb.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;


public class TestAction {
    @Autowired
    TestService testService;
    public String execute(){
        System.out.print("strusts!!!!!!!!!");
        testService.test();
        return "success";
    }
}
