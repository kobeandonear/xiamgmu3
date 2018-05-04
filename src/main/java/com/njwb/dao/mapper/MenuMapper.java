package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njwb.entity.Menu;
import com.njwb.util.RowMapper;

public class MenuMapper implements RowMapper{
	/**
	 * rs：查询结果集， 其实从本质上来讲，它和表没有关系，它只是你的查询的结果，如果你的结果中有重命名（别名），那么，rs中就只有别名
	 */
	public Object mapperObject(ResultSet rs) throws SQLException {
		Menu menu = new Menu();
		menu.setMenuID(rs.getInt("t_id"));
		menu.setMenuName(rs.getString("t_menu_name"));
		menu.setHrefUrl(rs.getString("t_href_url"));
		menu.setParentID(rs.getInt("t_parent_id"));
		menu.setCreateTime(rs.getTimestamp("t_create_time"));
		return menu;
	}
}
