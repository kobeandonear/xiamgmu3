package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.njwb.dao.UserDao;
import com.njwb.entity.User;
import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.entity.Menu;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.UserService;
import com.njwb.system.SystemProterties;
import com.njwb.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
	private Logger log = Logger.getLogger(UserServiceImpl.class);
	/**
	 * 使用对象工厂对它做赋值
	 * 1.需要提供set方法
	 * 2.bean.xml中需要配置依赖关系
	 */
	@Autowired
	private UserDao userDao;

	private Transaction transaction;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	
	
	public User queryOne(String userName, String pwd) {
		try{
			User user = userDao.queryOne(userName, pwd);
			return user;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public List<Menu> queryMenu(int roleID) {
		try{
			//查询所有的一级菜单
			List<Menu> menuList = userDao.queryLevelOne(roleID);
			//循环查找每个一级菜单的子菜单
			for (Menu menu : menuList) {
				//根据当前循环的一级菜单的ID，找到子菜单
				List<Menu> sonMenuList = userDao.queryMenuByParentID(menu.getMenuID(),roleID);
				//将子菜单塞到一级菜单中
				menu.setSonMenuList(sonMenuList);
			}
			
			return menuList;
		}catch (Exception e) {
			log.info("查询菜单出错",e);
			return null;
		}
	}
	
	public List<User> queryUserByRoleId(int roleID) throws SQLException {
		
		List<User> userList = userDao.queryUserByRoleId(roleID);
		
		return userList;
	}
	public PageModel<User> queryAllUsByPage(String pageNoStr) {
		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<User> pageModel = new PageModel<User>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<User> userList = null;
		//查总数,先设置总数量
		int count;
		try {
			count = userDao.queryCount();
			pageModel.setCnt(count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//在setPageNo,因为pageNo在set的时候，会判断总页数
		pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
		
		try {
			Integer pageNo = pageModel.getPageNo();
			Integer startIndex = (pageNo - 1) * pageSize;

			userList = userDao.queryAllUsByPage(pageSize, startIndex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageModel.setDataList(userList);
		log.info("pageModel:"+pageModel.getDataList());
		return pageModel;
	}
	/**
	 * 删除
	 * @throws OAException 
	 */
	public int deleteByNo(Integer userNo) throws OAException {
		log.info("进行账户删除，userNo：" + userNo);
		int count=0;
		transaction.begin();
		try {
			count=userDao.deleteByNo(userNo);
			System.out.println("11111111111111111");		
			transaction.commit();
			
		} catch (SQLException e) {
			log.info("删除账户失败", e);
			//transaction.rollBack();
			throw new OAException(ErrorCode.DEL_FAIL_User, "数据库删除失败");
		}
		return count;
		
	}

	public User queryByUserNo(Integer userNo) throws OAException {
		User user = null;
		try {
			user = userDao.queryByDeptNo(userNo);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e) {
			throw e;
		}
		return user;
	}
	/**
	 * 详情
	 */
	
	
	/**
	 * 增加
	 */
	public void addUser(User user) throws OAException {
		transaction.begin();
		
		try {
						
			userDao.addUser(user);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加报销员工失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE_User, "数据库添加失败");
		}
	
	}
	
	
	

	public void updateUser(User user) throws OAException {
		try{
			transaction.begin();
			userDao.updateUser(user);
			transaction.commit();

		} catch (SQLException e1) {
			log.info("修改报销员工失败", e1);
			transaction.rollBack();
			throw new OAException(ErrorCode.UPDATE_FAIL_User2, "修改密码失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			throw e;
		}
	}
}
