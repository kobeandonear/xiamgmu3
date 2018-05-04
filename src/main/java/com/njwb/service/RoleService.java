package com.njwb.service;

import java.util.List;

import com.njwb.entity.PageModel;
import com.njwb.entity.Role;
import com.njwb.exception.OAException;

public interface RoleService {
	public Role queryAllByName(String roleName);

	public PageModel<Role> queryAllRoByPage(String pageNoStr) throws OAException;

	public int deleteByNo(String roleNo) throws OAException;

	public void addRole(Role role) throws OAException;

	public void updateRole(Role role) throws OAException;

	public Role queryByRoleNo(String roleNo) throws OAException;

	public List<Role> queryAllRole();
	
}
