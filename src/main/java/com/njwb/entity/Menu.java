package com.njwb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单
 * @author Administrator
 *
 */
public class Menu implements Serializable{
	private int menuID;
	private String menuName;
	private String hrefUrl;
	private int parentID;
	private Date createTime;
	
	//如果是一级菜单，这个菜单还会有子菜单集合
	private List<Menu> sonMenuList;


	public List<Menu> getSonMenuList() {
		return sonMenuList;
	}

	public void setSonMenuList(List<Menu> sonMenuList) {
		this.sonMenuList = sonMenuList;
	}

	public int getMenuID() {
		return menuID;
	}

	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getHrefUrl() {
		return hrefUrl;
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	@Override
	public String toString() {
		return "Menu [menuID=" + menuID + ", menuName=" + menuName
				+ ", hrefUrl=" + hrefUrl + ", parentID=" + parentID
				+ ", createTime=" + createTime 
				+ "]";
	}
	
}
