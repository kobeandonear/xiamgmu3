package com.njwb.entity;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
	private int id; //用户编号
	private String roleName;//用户名
	private Date createTime;
	
	
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(int id, String roleName, Date createTime) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.createTime = createTime;
	}

	public Role( String roleName, Date createTime) {
		super();
		this.roleName = roleName;
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", createTime=" + createTime +"]";

	}
}
