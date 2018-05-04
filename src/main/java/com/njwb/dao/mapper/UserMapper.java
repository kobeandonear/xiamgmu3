package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njwb.entity.Employ;
import com.njwb.entity.Role;
import com.njwb.entity.User;
import com.njwb.util.RowMapper;

public class UserMapper implements RowMapper{
	public Object mapperObject(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("t_id"));
		user.setUserName(rs.getString("t_user_account"));
		user.setPwd(rs.getString("t_user_pwd"));
		user.setCreateTime(rs.getTimestamp("t_create_time"));
		user.setRoleId(rs.getInt("t_role_id"));
		user.setEmpNo(rs.getString("t_emp_no"));
		user.setUserStatus(rs.getString("t_user_status"));
		//设置employ信息
		Employ employ = new Employ();
		
		employ.setEmployName(rs.getString("t_employ_name"));
		user.setEmploy(employ);
		
		//设置role信息	
		Role role = new Role();
		role.setRoleName(rs.getString("t_role_name"));
		user.setRole(role);
		return user;
	}
}
