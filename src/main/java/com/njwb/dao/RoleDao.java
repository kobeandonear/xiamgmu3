package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Role;
import com.njwb.exception.OAException;

public interface RoleDao {

	public Role queryAllByName(String roleName) throws SQLException, OAException;

	public Role queryByDeptNo(String roleNo) throws SQLException, OAException;

	public int deleteByNo(String roleNo) throws SQLException, OAException;

	public List<Role> queryAllRoByPage(int pageSize, int pageNo) throws SQLException;

	public int queryCount() throws SQLException, OAException;

	public void addRole(Role role) throws SQLException;

	public void updateRole(Role role) throws SQLException, OAException;

	public List<Role> queryAllRole() throws SQLException;
	
}
