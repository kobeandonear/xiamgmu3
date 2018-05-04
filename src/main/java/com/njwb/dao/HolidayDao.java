package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.exception.OAException;

public interface HolidayDao {

	public int queryCount() throws SQLException, OAException;

	List<Holiday> queryAllHdByPage(int pageSize, int pageNo,String holidayName)throws SQLException;

	public int deleteByNo(String holidayNo) throws SQLException, OAException;

	public Holiday queryByDeptNo(String holidayNo) throws SQLException, OAException;

	public void addHoliday(Holiday holiday) throws SQLException;

	public void updateHoliday(Holiday holiday) throws SQLException, OAException;

}
