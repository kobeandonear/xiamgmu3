package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Employ;
import com.njwb.exception.OAException;

public interface EmployDao {

	
	/**
	 * 根据分页查询数据
	 * @param pageSize 每页显示条数
	 * @param pageNo 需要查询的页码   
	 * @return
	 * @throws SQLException
	 */
	public List<Employ> queryAllEpByPage(int pageSize, int pageNo) throws SQLException;
	/**
	 * 分页查询的搭档， 查询总数量
	 * @return
	 * @throws SQLException
	 * @throws OAException 
	 */
	public  int queryCount() throws SQLException, OAException;
	public List<Employ> queryAllEmploy() throws SQLException;
	public int deleteByNo(String employNo) throws SQLException, OAException;
	public void addEmploy(Employ employ) throws SQLException;
	public Employ queryByDeptNo(String employNo) throws SQLException, OAException;
	void updateDept(Employ employ) throws SQLException, OAException;
}
