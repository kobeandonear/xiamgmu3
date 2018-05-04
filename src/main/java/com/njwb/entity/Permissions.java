package com.njwb.entity;

import java.util.Date;

public class Permissions {
	private int id;
	private int roleId;
	private int menuId;
	private Date createTime;
	private Role role;
	private Menu menu;
	public Permissions() {
		// TODO Auto-generated constructor stub
	}
	
	public Permissions(int roleId, int menuId, Date createTime, Role role, Menu menu) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
		this.createTime = createTime;
		this.role = role;
		this.menu = menu;
	}

	public Permissions(int id, int roleId,  int menuId,
			Date createTime, Role role, Menu menu) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.menuId = menuId;
		this.createTime = createTime;
		this.role = role;
		this.menu = menu;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public String toString() {
		return "Permissions [menuId=" + menuId + "id="+id+", roleId=" + roleId
				+ ", createTime=" + createTime + ", role=" + role
				+ ", menu=" + menu 
				+ "]";
	}
}
