package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;


import com.njwb.constant.ErrorCode;
import com.njwb.dao.AccountDao;
import com.njwb.dao.HolidayDao;

import com.njwb.entity.Employ;
import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.AccountService;
import com.njwb.service.HolidayService;
import com.njwb.system.SystemProterties;
import com.njwb.transaction.Transaction;

public class HolidayServiceImpl implements HolidayService{
	private Logger log = Logger.getLogger(HolidayServiceImpl.class);
	
	private HolidayDao holidayDao;
	private Transaction transaction;
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public void setHolidayDao(HolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}
	
	
	
	public PageModel<Holiday> queryAllHdByPage(String pageNoStr, String holidayName) throws OAException {
		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Holiday> pageModel = new PageModel<Holiday>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<Holiday> holidayList = null;
		try {
			//查总数,先设置总数量
			int count = holidayDao.queryCount();
			pageModel.setCnt(count);
			//在setPageNo,因为pageNo在set的时候，会判断总页数
			pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
			
			holidayList = holidayDao.queryAllHdByPage(pageSize, pageModel.getPageNo(),holidayName);
			pageModel.setDataList(holidayList);
			log.info("pageModel:"+pageModel.getDataList());
		} catch (SQLException e) {
			log.info("分页查询员工所有信息失败", e);
		}
		return pageModel;
	}

	
	public int deleteByNo(String holidayNo) throws OAException {
		log.info("进行请假员工删除，employNo：" + holidayNo);
		int count=0;
		transaction.begin();
		try {
			count=holidayDao.deleteByNo(holidayNo);
			System.out.println("11111111111111111");		
			transaction.commit();
			
		} catch (SQLException e) {
			log.info("删除请假员工失败", e);
			//transaction.rollBack();
			throw new OAException(ErrorCode.DEL_FAIL_Holiday, "数据库删除失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			log.info("删除请假员工失败22",e);
			throw e;
		}
		return count;
		
	}

	
	public Holiday queryByHolidayNo(String holidayNo) throws OAException {
		Holiday holiday = null;
		try {
			holiday = holidayDao.queryByDeptNo(holidayNo);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e) {
			throw e;
		}
		return holiday;
	}

	
	public void addHoliday(Holiday holiday) throws OAException {
		transaction.begin();
		
		try {
						
			holidayDao.addHoliday(holiday);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加请假员工失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE_Holiday, "数据库添加失败");
		}
	
	}

	
	public void updateHoliday(Holiday holiday) throws OAException {
		try{
			transaction.begin();
			holidayDao.updateHoliday(holiday);
			transaction.commit();

		} catch (SQLException e1) {
			log.info("修改员工失败", e1);
			transaction.rollBack();
			throw new OAException(ErrorCode.UPDATE_FAIL_Holiday, "修改请假员工失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			throw e;
		}
	}



}
