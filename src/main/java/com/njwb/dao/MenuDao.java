package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Menu;
import com.njwb.exception.OAException;

public interface MenuDao {

	public List<Menu> queryAllMenu() throws SQLException;

	public Menu queryAllByName(String menuName) throws SQLException, OAException;


}
