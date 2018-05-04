package com.njwb.entity;

import java.util.Date;
/**
 * 部门实体类
 * @author Administrator
 *
 */
public class Dept {
	private String deptNo;
	private String deptName;
	private String deptLoc;
	private String deptManager;
	private Date createTime;
	//图片的地址
	private String imgUrl;
	/**
	 * 图片原始文件名称
	 */
	private String imgRealName;
	public Dept() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Dept(String deptName, String deptLoc, String deptManager, Date createTime, String imgUrl, String imgRealName) {
		super();
		this.deptName = deptName;
		this.deptLoc = deptLoc;
		this.deptManager = deptManager;
		this.createTime = createTime;
		this.imgUrl = imgUrl;
		this.imgRealName = imgRealName;
	}

	public Dept(String deptNo, String deptName, String deptLoc, String deptManager, String imgUrl, String imgRealName) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.deptLoc = deptLoc;
		this.deptManager = deptManager;
		this.imgUrl = imgUrl;
		this.imgRealName = imgRealName;
	}




	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgRealName() {
		return imgRealName;
	}

	public void setImgRealName(String imgRealName) {
		this.imgRealName = imgRealName;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptLoc() {
		return deptLoc;
	}
	public void setDeptLoc(String deptLoc) {
		this.deptLoc = deptLoc;
	}
	public String getDeptManager() {
		return deptManager;
	}
	public void setDeptManager(String deptManager) {
		this.deptManager = deptManager;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Dept [deptNo=" + deptNo + ", deptName=" + deptName
		+ ", deptLoc=" + deptLoc + ", deptManager=" + deptManager
		+ ", createTime=" + createTime + ", imgUrl=" + imgUrl
		+ ", imgRealName=" + imgRealName + "]";
	}
	
	
}
