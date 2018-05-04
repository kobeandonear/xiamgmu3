package com.njwb.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.Constant;
import com.njwb.constant.ErrorCode;
import com.njwb.entity.Dept;
import com.njwb.entity.Employ;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.DeptService;
import com.njwb.service.EmployService;
import com.njwb.system.SystemProterties;
import com.njwb.util.StringUtil;

/**
 * 部门控制器 部门功能的增删改查
 * 
 * @author Administrator
 * 
 */
public class DeptController {
	
	private Logger log = Logger.getLogger(DeptController.class);

	private DeptService deptService;

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	private EmployService employService;

	public void setEmployService(EmployService employService) {
		this.employService = employService;
	}

	/**
	 * 参数直接固定住，方便核心分发器传参
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException 
	 */
	public void queryAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// //调用service
		// DeptService deptService = (DeptService) ApplicationContext
		// .getBean("deptService");
		List<Dept> deptList = deptService.queryAllDept();
		// 数据传输
		
		log.info("deptList:"+deptList);
		String result = JSONArray.fromObject(deptList).toString();
		
		
		log.info("响应给前台，result:" + result);
		//设置响应的内容是什么格式，并且是什么编码格式
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
		// 页面不跳转，直接返回success
	}
	
	
	
	
	
	/**
	 * 根据分页查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String queryAllByPage(HttpServletRequest req,
			HttpServletResponse resp) {
		//int pageSize = 3;
		String pageNoStr = req.getParameter("pageNo");
		
		PageModel<Dept> pageModel = null;
		try {
			pageModel = deptService.queryAllByPage(pageNoStr);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.setAttribute("pageMode", pageModel);
		log.info(pageModel.getDataList());
		//可以在登录的时候，丢到session中
		req.setAttribute("tomcatPath", SystemProterties.getValue("tomcatPath"));
		log.info("路径："+SystemProterties.getValue("tomcatPath"));
		// 页面不跳转，直接返回success
		return "success";
	}

	/*public String addDept(HttpServletRequest request, HttpServletResponse resp) {

		// 取参数
		String deptNo = request.getParameter("deptNo");
		String deptName = request.getParameter("deptName");
		String deptLoc = request.getParameter("deptLoc");
		String deptManager = request.getParameter("deptMaster");

		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		if (StringUtil.isEmpty(deptNo) || StringUtil.isEmpty(deptName)) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL);
			// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
			// response);
			return "error";
		}

		// 2.调用后台service添加数据
		// DeptService deptService =
		// (DeptService)ApplicationContext.getBean("deptService");
		Dept dept = new Dept(deptNo, deptName, deptLoc, deptManager);
		try {
			deptService.addDept(dept);
			// 3.页面跳转（成功和失败）
			// 成功到dept.jsp
			return "success";
		} catch (OAException e) {
			// 失败，跳转到result
			request.setAttribute("resultCode", e.getErrorCode());
			return "error";
		}
	}*/
	public String addDept(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		//首先判断是否为二进制提交
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR);
			return "success";
		}
		//提取出公共方法
		Map<String, String> paramMap = parseParameter(request);
		
		//参数校验
		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		if (StringUtil.isEmpty(paramMap.get("deptName"))
				||StringUtil.isEmpty(paramMap.get("realName"))) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			log.info("编码，名称，上传图片都不能为空");
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL);
			return "error";
		}
		
		// 2.调用后台service添加数据
		Dept dept = new Dept(paramMap.get("deptNo"), paramMap.get("deptName"), 
					paramMap.get("deptLoc"), paramMap.get("deptMaster"),paramMap.get("imgUrl"),paramMap.get("realName"));
		/*dept.setImgUrl(paramMap.get("imgUrl"));
		dept.setImgRealName(paramMap.get("realName"));*/
		log.info("图片虚拟地址："+dept.getImgUrl()+"真实名："+dept.getImgRealName());
		
		try {
			deptService.addDept(dept);
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
			else
			{
				//file控件
				//拿上传的文件名称
				String realName = fileItem.getName();
				if(!StringUtil.isEmpty(realName))
				{
					//有原始文件名，继续，否则什么也不管  201804101056123.jpg
					//realName.substring(realName.lastIndexOf(".") 截取原始文件命名的后缀
					String newFileName = (new Date()).getTime() + realName.substring(realName.lastIndexOf("."));
					//文件上传路径
					String newFilePath = SystemProterties.getValue(Constant.UPLOAD_PATH);
					//文件上传
					fileItem.write(new File(newFilePath + newFileName));
					paramMap.put("realName", realName);
					paramMap.put("imgUrl", newFileName);
				}
			}
			
			
		}
		return paramMap;
	}

	/**
	 * 根据编号删除
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	public String deleteDept(HttpServletRequest request,
			HttpServletResponse resp) {
		// 1.取参数
		String resultCode = ErrorCode.SUCCESS;
		String deptNo = request.getParameter("deptNo");
		Dept dept=null;
		try {
			dept =deptService.queryByDeptNo(deptNo);
		} catch (OAException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Employ> list = employService.queryAllEmploy();
		for(Employ employ:list){
			if(dept.getDeptName().equals(employ.getEmployDeptName())){
				resultCode = ErrorCode.DEL_FAIL2;
				request.setAttribute("resultCode", resultCode);
				return "success";
			}
		}
		
			// 2.调用service删除
			
			try {
				deptService.deleteByNo(deptNo);
				// 3.页面跳转,result。jsp页面提示操作成功
			} catch (OAException e) {
				// 失败，跳转到result
				resultCode = e.getErrorCode();
			}
			

		// TODO 参数合法性校验

		request.setAttribute("resultCode", resultCode);
		// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
		// response);

		return "success";
	}

	/**
	 * 修改部门信息
	 * 
	 * @param request
	 * @param resp
	 * @return 返回result.jsp页面提示操作成功还是失败
	 * @throws Exception 
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 */
	public String editDept(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, FileUploadException, Exception {
		/*String deptNo = request.getParameter("deptNo");
		String deptName = request.getParameter("deptName");
		String deptLoc = request.getParameter("deptLoc");
		String deptManager = request.getParameter("deptMaster");*/
		//首先判断是否为二进制提交
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR);
			return "success";
		}
		//提取出公共方法
		Map<String, String> paramMap = parseParameter(request);
		//取参数，从参数容器中取
		String deptNo = paramMap.get("deptNo");
		String deptName = paramMap.get("deptName");
		String deptLoc = paramMap.get("deptLoc");
		String deptManager = paramMap.get("deptMaster");
		String imgUrl =paramMap.get("imgUrl");
		String imgRealName = paramMap.get("realName");

		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		if (StringUtil.isEmpty(deptNo) || StringUtil.isEmpty(deptName)) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL);
			// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
			// response);
			return "result";
		}

		// 2.调用后台service添加数据
		Dept dept = new Dept(deptNo, deptName, deptLoc, deptManager,imgUrl,imgRealName);

		//添加图片信息
		dept.setImgUrl(paramMap.get("imgUrl"));
		dept.setImgRealName(paramMap.get("realName"));
		
		String resultCode = ErrorCode.SUCCESS;
		try {
			// 调用修改方法
			deptService.updateDept(dept);
		} catch (OAException e) {
			// 失败，跳转到result
			resultCode = e.getErrorCode();
		}
		request.setAttribute("resultCode", resultCode);
		// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
		// response);
		return "result";
	}

	/**
	 * 查询部门详情
	 * 
	 * @param request
	 * @param resp
	 * @return error:查询异常，result.jsp, modify:修改，deptEdit.jsp, detail:详情，
	 *         deptDetail.jsp
	 */
	public String deptDetail(HttpServletRequest req, HttpServletResponse resp) {
		String deptNo = req.getParameter("deptNo");
		Dept dept;
		try {
			dept = deptService.queryByDeptNo(deptNo);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("resultCode", e.getErrorCode());
			// req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
			return "error";
		}

		req.setAttribute("dept", dept);

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
	/**
	 * ajax请求，返回值为text
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void checkNameText(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String deptName = req.getParameter("deptName");
		log.info("处理ajax请求，验证是否重名,deptName:" + deptName);
		//调用后台service,根据deptName查询是否有数据
		List<Dept> deptList = deptService.selectByDeptName(deptName);
		String result = "success";
		if(deptList != null && deptList.size() > 0)
		{
			result = "error";
		}
		//结果返回给result
		log.info("响应给前台，result:" + result);
		//如果不设置，那么浏览器接收到请求，会将结果作为xml解析，这个时候解析会出错，浏览器控制台会报语法错误，但是并不影响功能
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
	}
	
	/**
	 * ajax请求，返回值为xml
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws OAException 
	 */
	public void queryDeptXml(HttpServletRequest req, HttpServletResponse resp) throws IOException, OAException
	{
		String deptNo = req.getParameter("deptNo");
		log.info("ajax请求处理，查询部门信息，deptNo:" + deptNo);
		Dept dept = deptService.queryByDeptNo(deptNo);
		//响应是xml
		String result = "<dept><deptName>" + dept.getDeptName() + 
						"</deptName><deptNo>" + dept.getDeptNo() + 
						"</deptNo><deptLoc>" + dept.getDeptLoc() +
						"</deptLoc></dept>";
		log.info("响应给前台，result:" + result);
		//设置响应的内容是什么格式，并且是什么编码格式
		resp.setContentType("text/xml;charset=utf-8");
		resp.getWriter().write(result);
	}
	
	/**
	 * ajax请求，返回值为json
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws OAException 
	 */
	public void queryDeptJson(HttpServletRequest req, HttpServletResponse resp) throws IOException, OAException
	{
		String deptNo = req.getParameter("deptNo");
		log.info("ajax请求处理，查询部门信息，deptNo:" + deptNo);
		Dept dept = deptService.queryByDeptNo(deptNo);
		//响应是json   var dept = {deptNo:"A1001",deptName:"研发"}
//		String result = "{deptName:\"" + dept.getDeptName() + 
//						"\",deptNo:\"" + dept.getDeptNo() + 
//						"\",deptLoc:\"" + dept.getDeptLoc() +"\"}";
		//使用工具转换json格式 JSONArray可以转换数组
		String result = JSONObject.fromObject(dept).toString();
		log.info("响应给前台，result:" + result);
		//设置响应的内容是什么格式，并且是什么编码格式
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
	}
	
}
