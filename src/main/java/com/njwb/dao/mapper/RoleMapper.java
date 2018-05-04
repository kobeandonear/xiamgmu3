package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njwb.entity.Role;
import com.njwb.util.RowMapper;

public class RoleMapper implements RowMapper{

	public Object mapperObject(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("t_id"));
		role.setRoleName(rs.getString("t_role_name"));
		
		role.setCreateTime(rs.getTimestamp("t_create_time"));
		
		return role;
	}

}
