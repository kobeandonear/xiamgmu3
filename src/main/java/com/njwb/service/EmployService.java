package com.njwb.service;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Employ;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;


public interface EmployService {
	/**
	 * 根据分页查询数据
	 * @param pageSize 每页显示条数
	 * @param pageNo 需要查询的页码   
	 * @return PageModel 返回分页模型对象
	 * @throws SQLException
	 */
	public PageModel<Employ> queryAllEpByPage(String pageNoStr) throws OAException;
	/**
	 * 分页查询的搭档， 查询总数量
	 * @return
	 * @throws SQLException
	 */
	public int queryCount() throws OAException;
	
	/**
	 * 查询所有的员工信息
	 * @throws OAException 
	 */
	public PageModel<Employ> queryAllEmploy(String pageNoStr) throws OAException;
	public int deleteByNo(String employNo) throws OAException;
	public void addEmploy(Employ employ) throws OAException;
	public Employ queryByEmployNo(String employNo) throws OAException;
	public void updateEmploy(Employ employ) throws OAException;
	public List<Employ> queryAllEmploy();
}
