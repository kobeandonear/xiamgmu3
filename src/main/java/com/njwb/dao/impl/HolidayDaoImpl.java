package com.njwb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.AccountDao;
import com.njwb.dao.HolidayDao;
import com.njwb.dao.mapper.EmployMapper;
import com.njwb.dao.mapper.AccountMapper;
import com.njwb.dao.mapper.HolidayMapper;
import com.njwb.entity.Employ;
import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.exception.OAException;
import com.njwb.util.DateUtil;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;

public class HolidayDaoImpl implements HolidayDao{
	private Logger log = Logger.getLogger(HolidayDaoImpl.class);

	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_holiday order by t_holiday_no";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}

	
	public List<Holiday> queryAllHdByPage(int pageSize, int pageNo,String holidayName) throws SQLException {
		List<Holiday> list ;
		if(!holidayName.equals("")){
			String sql = "select t_holiday_no,t_holiday_user,t_holiday_type,t_holiday_bz,t_start_time,t_end_time,t_holiday_status,t_create_time  from t_holiday where t_holiday_user=? order by t_holiday_no limit ?,?";
			 list = JdbcTemplate.executeQuery(sql, new HolidayMapper(),holidayName,
					((pageNo - 1) * pageSize), pageSize);
		}else{
			String sql = "select t_holiday_no,t_holiday_user,t_holiday_type,t_holiday_bz,t_start_time,t_end_time,t_holiday_status,t_create_time  from t_holiday order by t_holiday_no limit ?,?";
			 list = JdbcTemplate.executeQuery(sql, new HolidayMapper(),
					((pageNo - 1) * pageSize), pageSize);
		}
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		
		return list;
	}


	public int deleteByNo(String holidayNo) throws SQLException,OAException {
		String sql = "delete from t_holiday where t_holiday_no = ?";
		// delete from t_dept where t_dept_no = null
		int count = JdbcTemplate.executeUpdate(sql, holidayNo);
		log.info("疯狂经过很久的狂热过count=" + count + ",holidayNo:" + holidayNo);
		
		return count;
	}


	public Holiday queryByDeptNo(String holidayNo) throws SQLException, OAException {
		String sql = "select t_holiday_no,t_holiday_user,t_holiday_type,t_holiday_bz,t_start_time,t_end_time,t_holiday_status,t_create_time from t_holiday where t_holiday_no=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Holiday)JdbcTemplate.executeOne(sql, new HolidayMapper(), holidayNo);

	}


	public void addHoliday(Holiday holiday) throws SQLException {
		String sql = "insert into t_holiday(t_holiday_no,t_holiday_user,t_holiday_type,t_holiday_bz,t_start_time,t_end_time,t_holiday_status,t_create_time) " + " values(?,?,?,?,?,?,?,?)";
		log.info(holiday);
		JdbcTemplate.executeUpdate(sql, holiday.getHolidayNo(),holiday.getHolidayUser(), holiday.getHolidayType(), holiday.getHolidayBz(), holiday.getStartTime(),holiday.getEndTime(),holiday.getHolidayStatus(),holiday.getCreateTime());	
		log.info(holiday);
	}


	public void updateHoliday(Holiday holiday) throws SQLException, OAException {
		int count = 0;
		String sql = "update t_holiday set t_holiday_user=?, t_holiday_type=?,t_holiday_bz=?,t_start_time=?,t_end_time=?,t_holiday_status=?,t_create_time=? where t_holiday_no=?";
		
		count = JdbcTemplate.executeUpdate(sql, holiday.getHolidayUser(), holiday.getHolidayType(), holiday.getHolidayBz(),holiday.getStartTime(),holiday.getEndTime(),holiday.getHolidayStatus(),holiday.getCreateTime(), holiday.getHolidayNo());
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL_Holiday, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
		
	}


	

}
