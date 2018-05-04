package com.njwb.entity;

import java.util.Date;

public class Employ {
	private int employNo;
	private String employName;
	private String employSex;
	private String employDeptName;
	private String createTime;
	private Dept dept;
	
	public Dept getDept() {
		return dept;
	}
	public Employ() {
		// TODO Auto-generated constructor stub
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}


	public Employ(String employName, String employSex, String employDeptName, String createTime) {
		super();
		this.employName = employName;
		this.employSex = employSex;
		this.employDeptName = employDeptName;
		this.createTime = createTime;
		this.dept = dept;
	}

	public Employ(int employNo, String employName, String employSex, String employDeptName, String createTime) {
		super();
		this.employNo = employNo;
		this.employName = employName;
		this.employSex = employSex;
		this.employDeptName = employDeptName;
		this.createTime = createTime;
	}




	public int getEmployNo() {
		return employNo;
	}
	public void setEmployNo(int employNo) {
		this.employNo = employNo;
	}
	public String getEmployName() {
		return employName;
	}
	public void setEmployName(String employName) {
		this.employName = employName;
	}
	public String getEmploySex() {
		return employSex;
	}
	public void setEmploySex(String employSex) {
		this.employSex = employSex;
	}
	public String getEmployDeptName() {
		return employDeptName;
	}
	public void setEmployDeptName(String employDeptName) {
		this.employDeptName = employDeptName;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Employ [employNo=" + employNo + ", employName=" + employName
		+ ", employSex=" +employSex + ", employDeptName=" + employDeptName
		+ ", createTime=" + createTime + "]";
	}
}
