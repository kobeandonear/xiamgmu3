package com.njwb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.entity.Menu;
import com.njwb.entity.Permissions;
import com.njwb.entity.PageModel;
import com.njwb.entity.Permissions;
import com.njwb.entity.Role;
import com.njwb.exception.OAException;
import com.njwb.service.MenuService;
import com.njwb.service.PermissionsService;
import com.njwb.service.RoleService;
import com.njwb.system.SystemProterties;
import com.njwb.util.StringUtil;
import org.apache.struts2.ServletActionContext;

public class PermissionsController {
	private Logger log = Logger.getLogger(PermissionsController.class);
	private PermissionsService permissionsService;
	private RoleService roleService;
	private MenuService menuService;
	
	public void setPermissionsService(PermissionsService permissionsService) {
		this.permissionsService = permissionsService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	
	public String queryAllPerByPage() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String pageNoStr = req.getParameter("pageNo");
		
		PageModel<Permissions> pageModel = null;
		pageModel = permissionsService.queryAllPerByPage(pageNoStr);
		
		
		req.setAttribute("pageMode", pageModel);
		log.info(pageModel.getDataList());
		//可以在登录的时候，丢到session中
		req.setAttribute("tomcatPath", SystemProterties.getValue("tomcatPath"));
		log.info("路径："+SystemProterties.getValue("tomcatPath"));
		// 页面不跳转，直接返回success
		return "success";
	}
/*	*//**
	 * 删除权限信息
	 * @throws IOException 
	 */
	public void delPerm() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		// 1.取参数
		String id = request.getParameter("id");
		
		log.info("删除id:"+id);
		
		String result = "success";
	
			// TODO 参数合法性校验
			int count = 0;
			// 2.调用service删除
			
			String resultCode = ErrorCode.SUCCESS_Permissions;
			try {
				count = permissionsService.deleteByNo(id);
				// 3.页面跳转,result。jsp页面提示操作成功
			} catch (OAException e) {
				result = "error";
				// 失败，跳转到result
				resultCode = e.getErrorCode();
			}
			if (count != 1) {
				result = "error";
			}else{
				result = "success";
			}
		
	
		//结果返回给result
		log.info("响应给前台，result:" + result);
		//如果不设置，那么浏览器接收到请求，会将结果作为xml解析，这个时候解析会出错，浏览器控制台会报语法错误，但是并不影响功能
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);

	}
	
	
	
/*	
	*//**
	 * 增加
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 */
	public String permAdd() throws UnsupportedEncodingException, FileUploadException, Exception {
		//首先判断是否为二进制提交
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Permissions);
			return "success";
		}
		Map<String, String> paramMap = parseParameter(request);
		//提取出公共方法
		
		
		//参数校验
		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		
		if (StringUtil.isEmpty(paramMap.get("roleName")) 	||StringUtil.isEmpty(paramMap.get("menuName"))) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			log.info("编码，名称都不能为空");
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Permissions);
			return "error";
		}
		Date date = new Date();
	
		
		log.info("roleName="+paramMap.get("roleName"));		
		Role role=roleService.queryAllByName(paramMap.get("roleName"));
		Menu menu=menuService.queryAllByName(paramMap.get("menuName"));
		log.info(role.toString());
		log.info(role.toString());
		
		int roleId = role.getId();
		
		int menuId = menu.getMenuID();
		//Integer.valueOf(paramMap.get("PermissionsNo"))
		Permissions permissions = new Permissions(roleId,menuId,date,role,menu);
		
			Permissions permissions1 = permissionsService.queryAllById(roleId,menuId);
			log.info(permissions1);
			if(permissions1==null){
				try {
					permissionsService.addPermissions(permissions);
					// 3.页面跳转（成功和失败）
					// 成功到dept.jsp
					request.setAttribute("resultCode", ErrorCode.ADD_SUCCESS);
					return "success";
				} catch (OAException e1) {
					// 失败，跳转到result
					request.setAttribute("resultCode", e1.getErrorCode());
					return "error";
				}	
			}
		
		request.setAttribute("resultCode",ErrorCode.ADD_FAIL_PER1);
		return "error";		
	}
	/**
	 * 添加，修改，需要上传文件，此处做参数解析作用，以及文件上传
	 * @param request
	 * @return
	 * @throws FileUploadException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private Map<String, String> parseParameter(HttpServletRequest request)
			throws FileUploadException, UnsupportedEncodingException, Exception {
		//是二进制提交，取参数，和上传图片
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		//拿到所有的控件对象集合
		List<FileItem> fileItemList = fileUpload.parseRequest(request);
		//定义放置参数的map
		Map<String, String> paramMap = new HashMap<String, String>();
		for (FileItem fileItem : fileItemList) {
			if(fileItem.isFormField())
			{
				//普通文本域
				//拿普通文本域的值
				String value = fileItem.getString();
				//如果为空，直接空串，不为空，进行转码
				value = StringUtil.isEmpty(value)? "" : new String(value.getBytes("iso-8859-1"),"utf-8");
				//将参数放入容器
				paramMap.put(fileItem.getFieldName(), value);
			}
			
		}
		return paramMap;
	}
	
	/**
	 * 查询账户详情
	 * @return
	 */
	
	public String permDetail() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpSession session = req.getSession();
		
		int permissionsNo = Integer.valueOf(req.getParameter("permId"));
		Permissions permissions;
		try {
			permissions = permissionsService.queryByPermissionsNo(permissionsNo);
		} catch (OAException e) {
		
			e.printStackTrace();
			session.setAttribute("resultCode", e.getErrorCode());
			// req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
			return "error";
		}
		session.setAttribute("permissions", permissions);
		
		List<Menu> menuList =menuService.queryAllMenu();
		List<Role> roleList = roleService.queryAllRole();
		
		
		session.setAttribute("menuList", menuList);
		session.setAttribute("roleList", roleList);
		
		log.info(req.getAttribute("permissions"));
		// 页面跳转，跳转详情页，或者是修改页
		// 来源标识
		String sourceType = req.getParameter("sourceType");
		// 默认跳转详情页，如果来源为修改，则跳转到修改页
		// String url = "/njwb/dept/deptDetail.jsp";
		String resultStr = "detail";
		if ("edit".equals(sourceType)) {
			// url = "/njwb/dept/deptEdit.jsp";
			resultStr = "modify";
		}

		// req.getRequestDispatcher(url).forward(req, resp);
		return resultStr;
	}
	
	
	
	
	
	/*
	*//**
	 * 编辑
	 * @param request
	 * @param resp
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileUploadException
	 * @throws Exception
	 */
	public String editPerm() throws UnsupportedEncodingException, FileUploadException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Permissions);
			return "success";
		}
			
			//提取出公共方法
			Map<String, String> paramMap = parseParameter(request);
			//取参数，从参数容器中取
			String roleName = paramMap.get("roleName");
			String menuName = paramMap.get("menuName");
		
			log.info("roleName="+roleName);
			
			// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
			// 调用service之前，需要保证参数的准确性
			if (StringUtil.isEmpty(menuName) || StringUtil.isEmpty(roleName)) {
				// 跳转到错误页面，提示参数非法
				// 错误码1002，需要把错误码带到result.jsp页面
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Permissions3);
				// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
				// response);
				return "result";
			}
			Permissions permissions1=(Permissions) request.getSession().getAttribute("permissions");
			Permissions permissions = permissionsService.queryByPermissionsNo(permissions1.getId());
			Role role=roleService.queryAllByName(roleName);
			Menu menu = menuService.queryAllByName(menuName);
			
			if(permissions == null||role == null){
				log.info("为空不能修改");
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Permissions3);
				return "result";
			}
			log.info(permissions.toString());
			log.info(role.toString());
			
			
			int id=permissions1.getId();
			Date date = new Date();
			
			Permissions permissions2= new Permissions(id,role.getId(),menu.getMenuID(),date,role,menu);
		
			String resultCode = ErrorCode.SUCCESS_Permissions;
			// 调用修改方法
			permissionsService.updatePermissions(permissions2);
			request.setAttribute("resultCode", resultCode);
			// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
			// response);
			return "result";

	}
}
