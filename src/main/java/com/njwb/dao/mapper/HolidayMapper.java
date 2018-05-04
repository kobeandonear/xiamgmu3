package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


import com.njwb.dao.impl.EmployDaoImpl;
import com.njwb.entity.Holiday;
import com.njwb.util.RowMapper;

public class HolidayMapper implements RowMapper{
	private Logger log = Logger.getLogger(HolidayMapper.class);

	public Object mapperObject(ResultSet rs) throws SQLException {
		Holiday holiday = new Holiday();
		holiday.setHolidayNo(rs.getInt("t_holiday_no"));
		holiday.setHolidayUser(rs.getString("t_holiday_user"));
		holiday.setHolidayType(rs.getString("t_holiday_type"));
		holiday.setHolidayBz(rs.getString("t_holiday_bz"));
		holiday.setStartTime(rs.getTimestamp("t_start_time"));
		holiday.setEndTime(rs.getTimestamp("t_end_time"));
		holiday.setHolidayStatus(rs.getString("t_holiday_status"));
		holiday.setCreateTime(rs.getTimestamp("t_create_time"));
		log.info(rs.getShort("t_create_time"));
		return holiday;
	}
}
