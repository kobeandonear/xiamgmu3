package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Dept;
import com.njwb.exception.OAException;

public interface DeptDao {
	/**
	 * 查询所有的部门信息
	 * @return
	 */
	public List<Dept> queryAllDept() throws SQLException;
	public List<Dept> selectByDeptNo(String DeptNo) throws SQLException;
	/**
	 * 添加部门
	 * @param dept
	 */
	public void addDept(Dept dept) throws SQLException;
	
	public void deleteByNo(String deptNo) throws SQLException, OAException;
	public Dept queryByDeptNo(String deptNo) throws SQLException, OAException;
	public void updateDept(Dept dept) throws SQLException, OAException;
	/**
	 * 根据分页查询数据
	 * @param pageSize 每页显示条数
	 * @param pageNo 需要查询的页码   
	 * @return
	 * @throws SQLException
	 */
	public List<Dept> queryAllByPage(int pageSize, int pageNo) throws SQLException;
	/**
	 * 分页查询的搭档， 查询总数量
	 * @return
	 * @throws SQLException
	 */
	public int queryCount() throws SQLException, OAException;
	
	public List<Dept> selectByDeptName (String deptName)  throws SQLException;
}
