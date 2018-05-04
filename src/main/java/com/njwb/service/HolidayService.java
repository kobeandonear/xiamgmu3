package com.njwb.service;

import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;

public interface HolidayService {


	 public PageModel<Holiday> queryAllHdByPage(String pageNoStr, String holidayName) throws OAException;



	public int deleteByNo(String holidayNo) throws OAException;



	public Holiday queryByHolidayNo(String holidayNo) throws OAException;



	public void addHoliday(Holiday holiday) throws OAException;



	public void updateHoliday(Holiday holiday) throws OAException;






}
