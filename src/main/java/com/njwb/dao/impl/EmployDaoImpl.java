package com.njwb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.EmployDao;
import com.njwb.dao.mapper.DeptMapper;
import com.njwb.dao.mapper.EmployMapper;

import com.njwb.entity.Dept;
import com.njwb.entity.Employ;
import com.njwb.exception.OAException;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;

public class EmployDaoImpl implements EmployDao{
	private Logger log = Logger.getLogger(EmployDaoImpl.class);
	
	

	public List<Employ> queryAllEpByPage(int pageSize, int pageNo)
			throws SQLException {
		String sql = "select t_employ_no,t_employ_name,t_employ_sex,t_employ_dept,t_create_time  from t_employ order by t_employ_no limit ?,?";
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		List<Employ> list = JdbcTemplate.executeQuery(sql, new EmployMapper(),
				((pageNo - 1) * pageSize), pageSize);
		return list;
	}


	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_employ order by t_employ_no";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}


	public List<Employ> queryAllEmploy() throws SQLException {
		String sql = "select t_employ_no,t_employ_name,t_employ_sex,t_employ_dept,t_create_time from t_employ";
		List<Employ> list = JdbcTemplate.executeQuery(sql, new EmployMapper(), null);
		return list;
	}


	public int deleteByNo(String employNo) throws SQLException, OAException {
		String sql = "delete from t_employ where t_employ_no = ?";
		// delete from t_dept where t_dept_no = null
		int count = JdbcTemplate.executeUpdate(sql, employNo);
		log.info("疯狂经过很久的狂热过count=" + count + ",deptNo:" + employNo);
		
		return count;
	}


	public void addEmploy(Employ employ) throws SQLException {
		String sql = "insert into t_employ(t_employ_no,t_employ_name,t_employ_sex,t_employ_dept,t_create_time) " + "values(?,?,?,?,?)";
		JdbcTemplate.executeUpdate(sql, employ.getEmployNo(), employ.getEmployName(), employ.getEmploySex(), employ.getEmployDeptName(),employ.getCreateTime());	
	}



	public Employ queryByDeptNo(String employNo) throws SQLException, OAException {
		String sql = "select t_employ_no,t_employ_name,t_employ_sex,t_employ_dept,t_create_time from t_employ where t_employ_no=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Employ) JdbcTemplate.executeOne(sql, new EmployMapper(), Integer.valueOf(employNo));
	}



	public void updateDept(Employ employ) throws SQLException, OAException {
		int count = 0;
		String sql = "update t_employ set t_employ_name=?, t_employ_sex=?, t_employ_dept=?,t_create_time=? where t_employ_no=?";
		
		count = JdbcTemplate.executeUpdate(sql, employ.getEmployName(), employ.getEmploySex(), employ.getEmployDeptName(),employ.getCreateTime(), employ.getEmployNo());
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
	}

}
