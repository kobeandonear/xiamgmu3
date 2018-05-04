package com.njwb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;

import com.njwb.entity.PageModel;
import com.njwb.entity.Permissions;
import com.njwb.entity.Role;
import com.njwb.entity.User;
import com.njwb.exception.OAException;
import com.njwb.service.PermissionsService;
import com.njwb.service.RoleService;
import com.njwb.service.UserService;
import com.njwb.util.StringUtil;
import org.apache.struts2.ServletActionContext;


public class RoleController {
	private Logger log = Logger.getLogger(RoleController.class);
	private RoleService roleService;
	private PermissionsService permissionsService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setPermissionsService(PermissionsService permissionsService) {
		this.permissionsService = permissionsService;
	}
	
	public void queryAllRole() throws IOException {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		// //调用service
		
		List<Role> roleList = roleService.queryAllRole();
		// 数据传输
		
		log.info("roleList:"+roleList);
		String result = JSONArray.fromObject(roleList).toString();
		
		
		log.info("响应给前台，result:" + result);
		//设置响应的内容是什么格式，并且是什么编码格式
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
		// 页面不跳转，直接返回success
	}

	public String queryAllRoByPage(HttpServletRequest req,
			HttpServletResponse resp) {
		String pageNoStr = req.getParameter("pageNo");
		
		PageModel<Role> pageModel = null;
		try {
			pageModel =roleService.queryAllRoByPage(pageNoStr);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.setAttribute("pageMode", pageModel);
		log.info(pageModel.getDataList());
		//可以在登录的时候，丢到session中
		/*req.setAttribute("tomcatPath", SystemProterties.getValue("tomcatPath"));
		log.info("路径："+SystemProterties.getValue("tomcatPath"));*/
		// 页面不跳转，直接返回success
		return "success";
	}
	
	/**
	 * 删除请假人员信息
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public void deleteRole() throws IOException, NumberFormatException, SQLException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		// 1.取参数
		String roleNo = request.getParameter("roleId");
		
		log.info("roleNo:"+roleNo);
		List<User> list =null;
		String result = "success";
		
		try{
			 list = userService.queryUserByRoleId(Integer.valueOf(roleNo));
			 result = "error";
		}catch (Exception e1) {
			
			/*if(list.size()!=0){ 
				result = "error";
			}else{*/
			
				// TODO 参数合法性校验
				int count = 0;
				// 2.调用service删除
				
				String resultCode = ErrorCode.SUCCESS;
				try {
					count = roleService.deleteByNo(roleNo);
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

			}
	
		
		
		
	
		//结果返回给result
		log.info("响应给前台，result:" + result);
		//如果不设置，那么浏览器接收到请求，会将结果作为xml解析，这个时候解析会出错，浏览器控制台会报语法错误，但是并不影响功能
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
	}
	
/*	*//**
	 * 根据某个编号查询详情
	 * @return 
	 */
	public String roleDetail() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		
		String roleNo = req.getParameter("roleId");
		req.getSession().setAttribute("roleId", roleNo);
	//	log.info("详情roleStatus="+roleStatus);
		Role role;
		List<Role> roleList = roleService.queryAllRole();
		try {
			role = roleService.queryByRoleNo(roleNo);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("resultCode", e.getErrorCode());
			// req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
			return "error";
		}
		
	
		req.getSession().setAttribute("role",  role);
		
		req.getSession().setAttribute("roleList", roleList);
		log.info(req.getAttribute("role"));
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
	
	
	public String addRole() throws UnsupportedEncodingException, FileUploadException, Exception {
		//首先判断是否为二进制提交
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Role);
			return "success";
		}
		Map<String, String> paramMap = parseParameter(request);
		//提取出公共方法
		
		
		//参数校验
		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		
		if (StringUtil.isEmpty(paramMap.get("roleName")) ) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			log.info("编码，名称都不能为空");
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role);
			return "error";
		}
		Date date = new Date();
		// 2.调用后台service添加数据
		
		//Integer.valueOf(paramMap.get("roleNo"))
		Role role = new Role(paramMap.get("roleName"),date);
		List<Role> list = roleService.queryAllRole();
		for (Role r:list) {			
			if(role.getRoleName().equals(r.getRoleName())){
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role2);
				return "error";
			}
		}
		
		try {
			roleService.addRole(role);
			// 3.页面跳转（成功和失败）
			// 成功到dept.jsp
			return "success";
		} catch (OAException e) {
			// 失败，跳转到result
			request.setAttribute("resultCode", e.getErrorCode());
			return "error";
		}

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
	 * 修改请假员工信息
	 * @param req
	 * @param resp
	 * @throws Exception 
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 */
	public String editRole() throws UnsupportedEncodingException, FileUploadException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Role);
			return "success";
		}
			
			//提取出公共方法
			Map<String, String> paramMap = parseParameter(request);
			//取参数，从参数容器中取
			String roleName = paramMap.get("roleName");
			Role role1=roleService.queryAllByName(roleName);
			String resultCode = ErrorCode.SUCCESS_Role;
			if(role1!=null){
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role3);
				return "result";
			}
			List<Permissions> permList=null;
			try{
				permList=permissionsService.queryAll();	
			}catch (Exception e) {				
				if(permList!=null){
					for(Permissions per:permList){
						if(per.getRole().getRoleName().equals(roleName)){
							request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role22);
							// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
							// response);
							return "result";
						}
				}					
			}
		}
			if(permList!=null){
				for(Permissions per:permList){
					if(per.getRole().getRoleName().equals(roleName)){
						request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role22);
						// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
						// response);
						return "result";
					}
			}					
		}
			log.info("roleName="+roleName);
			int roleId = Integer.valueOf(paramMap.get("roleId"));
			// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
			// 调用service之前，需要保证参数的准确性
			if (StringUtil.isEmpty(roleName)) {
				// 跳转到错误页面，提示参数非法
				// 错误码1002，需要把错误码带到result.jsp页面
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role);
				// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
				// response);
				return "result";
			}
			Date date = new Date();
			
			Role role= new Role(roleId,roleName,date);

		
			
			List<Role> list = roleService.queryAllRole();
			for (Role r:list) {			
				if(role.getRoleName().equals(r.getRoleName())){					
						request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Role2);
						return "result";
					
				}
			}
			
			
			// 调用修改方法
			roleService.updateRole(role);
			request.setAttribute("resultCode", resultCode);
			// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
			// response);
			return "result";
		}
	
	
	
	
	
	
	
	
	
	
}
