package com.njwb.service;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Dept;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;

public interface DeptService {
	/**
	 * 查询所有的部门信息
	 * @return
	 */
	public List<Dept> queryAllDept();
	public void addDept(Dept de) throws OAException;
	public void deleteByNo(String deptNo) throws OAException;
	public List<Dept> selectByDeptNo(String DeptNo);
	/**
	 * 根据编号查询部门数据
	 * @param deptNo
	 * @return
	 */
	public Dept queryByDeptNo(String deptNo) throws OAException;
	/**
	 * 修改部门
	 * @param dept
	 * @throws OAException
	 */
	public void updateDept(Dept dept) throws OAException;
	
	/**
	 * 根据分页查询数据
	 * @param pageSize 每页显示条数
	 * @param pageNo 需要查询的页码   
	 * @return PageModel 返回分页模型对象
	 * @throws SQLException
	 */
	public PageModel<Dept> queryAllByPage(String pageNoStr) throws OAException;
	/**
	 * 分页查询的搭档， 查询总数量
	 * @return
	 * @throws SQLException
	 */
	public int queryCount() throws OAException;
	
	public List<Dept> selectByDeptName(String deptName);
}
