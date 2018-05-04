package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Permissions;
import com.njwb.exception.OAException;

public interface PermissionsDao {

	public List<Permissions> queryAllUsByPage(int pageSize, int pageNo) throws SQLException;

	public int deleteByNo(String permissionsNo);

	public int queryCount() throws SQLException, OAException;

	public Permissions queryByDeptNo(int permissionsNo) throws SQLException, OAException;

	public void updatePermissions(Permissions permissions) throws SQLException, OAException;

	public void addPermissions(Permissions permissions) throws SQLException;

	public Permissions queryById(int roleId, int menuId) throws SQLException, OAException;

	public List<Permissions> queryAll() throws SQLException;

}
