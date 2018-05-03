package com.bigbro.action;


import com.bigbro.service.TestService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


public class TestAction {
    @Autowired
    TestService testService;
    public String execute(){
        System.out.print("strusts!!!!!!!!!");
        testService.test();
        return "success";
    }
}
