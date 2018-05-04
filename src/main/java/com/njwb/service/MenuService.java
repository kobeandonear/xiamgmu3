package com.njwb.service;

import java.util.List;

import com.njwb.entity.Menu;

public interface MenuService {

	public List<Menu> queryAllMenu();

	public Menu queryAllByName(String string);
	
	
}
