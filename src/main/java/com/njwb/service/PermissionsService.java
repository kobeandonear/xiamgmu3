package com.njwb.service;


import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.PageModel;
import com.njwb.entity.Permissions;
import com.njwb.exception.OAException;

public interface PermissionsService {

	public PageModel<Permissions> queryAllPerByPage(String pageNoStr);

	public int deleteByNo(String permissionsNo) throws OAException;

	public Permissions queryByPermissionsNo(int permissionsNo) throws OAException;

	public void addPermissions(Permissions permissions) throws OAException;

	public void updatePermissions(Permissions permissions2) throws OAException;

	public Permissions queryAllById(int roleId, int menuId) throws OAException;

	public List<Permissions> queryAll() throws SQLException;

}
