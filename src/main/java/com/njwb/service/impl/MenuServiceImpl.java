package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.dao.MenuDao;
import com.njwb.entity.Menu;

import com.njwb.exception.OAException;
import com.njwb.service.MenuService;
import com.njwb.transaction.Transaction;

public class MenuServiceImpl implements MenuService{
private Logger log = Logger.getLogger(MenuServiceImpl.class);
	
	private MenuDao menuDao;
	private Transaction transaction;
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public List<Menu> queryAllMenu() {
		List<Menu> menuList = null;
		try {
			menuList = menuDao.queryAllMenu();
		} catch (SQLException e) {
			log.info("查询部门所有信息失败", e);
		}
		return menuList;
	}

	public Menu queryAllByName(String menuName) {
		Menu menu = null;
		try {
			menu = menuDao.queryAllByName(menuName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menu;
	}
	
	
	
	
	
	
}
