package com.njwb.service;


import com.njwb.dao.UserDao;
import com.njwb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testService")
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl implements TestService {
    @Autowired
    UserDao userDao;
    public void test() {
  /*      User user = new User();
        user.setPassword("123123");
        user.setUsername("111111");

        userDao.addUser(user);*/

        System.out.print("spring!!!!!!!!");
    }
}
