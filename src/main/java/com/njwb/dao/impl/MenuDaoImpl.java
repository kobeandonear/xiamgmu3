package com.njwb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.njwb.dao.MenuDao;
import com.njwb.dao.mapper.MenuMapper;

import com.njwb.entity.Menu;
import com.njwb.entity.Menu;
import com.njwb.exception.OAException;

import com.njwb.util.JdbcTemplate;

public class MenuDaoImpl implements MenuDao{

	public List<Menu> queryAllMenu() throws SQLException {
		String sql = "select t_id,t_menu_name,t_href_url,t_parent_id,t_create_time from t_menu";
		List<Menu> list = JdbcTemplate.executeQuery(sql, new MenuMapper(), null);
		return list;
	}

	public Menu queryAllByName(String menuName) throws SQLException, OAException {
		String sql = "select t_id,t_menu_name, t_href_url,t_parent_id,t_create_time from t_menu where t_menu_name=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Menu)JdbcTemplate.executeOne(sql, new MenuMapper(), menuName);
	}

}
