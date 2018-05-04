package com.njwb.entity;

import java.util.Date;

public class Holiday {
	private int holidayNo;
	private String holidayUser;
	private String holidayType;
	private String holidayBz;
	private Date startTime;
	private Date endTime;
	private String holidayStatus;
	private Date createTime;
	
	
	public Holiday() {
		// TODO Auto-generated constructor stub
	}
	
	


	public Holiday( String holidayUser, String holidayType, String holidayBz, Date startTime, Date endTime, String holidayStatus, Date createTime) {
		super();
	//	this.holidayNo = holidayNo;
		this.holidayUser = holidayUser;
		this.holidayType = holidayType;
		this.holidayBz = holidayBz;
		this.startTime = startTime;
		this.endTime = endTime;
		this.holidayStatus = holidayStatus;
		this.createTime = createTime;
	}
	
	



	public Holiday(int holidayNo, String holidayUser, String holidayType, String holidayBz, Date startTime, Date endTime, String holidayStatus, Date createTime) {
		super();
		this.holidayNo = holidayNo;
		this.holidayUser = holidayUser;
		this.holidayType = holidayType;
		this.holidayBz = holidayBz;
		this.startTime = startTime;
		this.endTime = endTime;
		this.holidayStatus = holidayStatus;
		this.createTime = createTime;
	}




	public int getHolidayNo() {
		return holidayNo;
	}




	public void setHolidayNo(int holidayNo) {
		this.holidayNo = holidayNo;
	}




	public String getHolidayUser() {
		return holidayUser;
	}




	public void setHolidayUser(String holidayUser) {
		this.holidayUser = holidayUser;
	}




	public String getHolidayType() {
		return holidayType;
	}




	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}




	public String getHolidayBz() {
		return holidayBz;
	}




	public void setHolidayBz(String holidayBz) {
		this.holidayBz = holidayBz;
	}




	public Date getStartTime() {
		return startTime;
	}




	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}




	public Date getEndTime() {
		return endTime;
	}




	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}




	public String getHolidayStatus() {
		return holidayStatus;
	}




	public void setHolidayStatus(String holidayStatus) {
		this.holidayStatus = holidayStatus;
	}




	public Date getCreateTime() {
		return createTime;
	}




	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}




	@Override
	public String toString() {
		return "Holiday [holidayNo=" + holidayNo
		+ ", holidayUser=" + holidayUser + ", holidayType=" + holidayType
		+ ", holidayBz=" + holidayBz+ ",startTime=" + startTime + ", endTime=" + endTime + ", holidayStatus=" + holidayStatus  + ", createTime=" + createTime + "]";
	}
	
}
