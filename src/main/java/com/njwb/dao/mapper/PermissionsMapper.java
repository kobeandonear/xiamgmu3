package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njwb.entity.Menu;
import com.njwb.entity.Permissions;
import com.njwb.entity.Role;
import com.njwb.util.RowMapper;

public class PermissionsMapper  implements RowMapper{

	public Object mapperObject(ResultSet rs) throws SQLException {
		Permissions permissions = new Permissions();
		permissions.setId(rs.getInt("t_id"));
		permissions.setRoleId(rs.getInt("t_role_id"));
		permissions.setMenuId(rs.getInt("t_menu_id"));
		permissions.setCreateTime(rs.getTimestamp("t_create_time"));
		//设置meun信息	
		Menu menu=new Menu();
		menu.setMenuName(rs.getString("t_menu_name"));
		permissions.setMenu(menu);
		//设置role信息	
		Role role = new Role();
		role.setRoleName(rs.getString("t_role_name"));
		permissions.setRole(role);
		return permissions;
	}

}
