package com.njwb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.njwb.entity.Menu;
import com.njwb.service.MenuService;

public class MenuController {
	private Logger log = Logger.getLogger(MenuController.class);
	private MenuService menuService;
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	
	public void queryAllMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// //调用service
		// MenuService menuService = (MenuService) ApplicationContext
		// .getBean("menuService");
		List<Menu> menuList = menuService.queryAllMenu();
		// 数据传输
		
		log.info("menuList:"+menuList);
		String result = JSONArray.fromObject(menuList).toString();
		
		
		log.info("响应给前台，result:" + result);
		//设置响应的内容是什么格式，并且是什么编码格式
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
		// 页面不跳转，直接返回success
	}
	
}
