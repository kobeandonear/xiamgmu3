package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.njwb.entity.Employ;
import com.njwb.util.RowMapper;

public class EmployMapper implements RowMapper{


	public Object mapperObject(ResultSet rs) throws SQLException {
		Employ employ= new Employ();
		employ.setEmployNo(rs.getInt("t_employ_no"));
		employ.setEmployName(rs.getString("t_employ_name"));
		employ.setEmploySex(rs.getString("t_employ_sex"));
		employ.setEmployDeptName(rs.getString("t_employ_dept"));
		employ.setCreateTime(rs.getString("t_create_time"));
	
		return employ;
	}

}
