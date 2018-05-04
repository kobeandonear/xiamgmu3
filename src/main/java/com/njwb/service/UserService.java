package com.njwb.service;


import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Menu;
import com.njwb.entity.PageModel;
import com.njwb.entity.User;
import com.njwb.exception.OAException;

public interface UserService {
	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public User queryOne(String userName, String pwd);
	
	
//	public List<Menu> queryMenu(int roleID);


	public PageModel<User> queryAllUsByPage(String pageNoStr);

	public List<User> queryUserByRoleId(int roleID) throws SQLException;

	public void addUser(User user) throws OAException;


	public int deleteByNo(Integer userNo) throws OAException;

	public User queryByUserNo(Integer userNo) throws OAException;

	public void updateUser(User user) throws OAException;


	public List<Menu> queryMenu(int roleId);
}
