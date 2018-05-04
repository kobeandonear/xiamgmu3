package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njwb.entity.Dept;
import com.njwb.util.RowMapper;

public class DeptMapper implements RowMapper{
	/**
	 * rs：查询结果集， 其实从本质上来讲，它和表没有关系，它只是你的查询的结果，如果你的结果中有重命名（别名），那么，rs中就只有别名
	 */
	public Object mapperObject(ResultSet rs) throws SQLException {
		Dept dept = new Dept();
		dept.setDeptNo(rs.getString("t_dept_no"));
		dept.setDeptName(rs.getString("t_dept_name"));
		dept.setDeptLoc(rs.getString("t_dept_loc"));
		dept.setDeptManager(rs.getString("t_dept_manager"));
		dept.setCreateTime(rs.getTimestamp("t_create_time"));
		dept.setImgUrl(rs.getString("t_img_url"));
		dept.setImgRealName(rs.getString("t_img_real_name"));
		return dept;
	}
}
