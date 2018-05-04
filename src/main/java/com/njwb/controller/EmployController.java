package com.njwb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.entity.Dept;
import com.njwb.entity.Employ;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.DeptService;
import com.njwb.service.EmployService;
import com.njwb.system.SystemProterties;
import com.njwb.util.DateUtil;
import com.njwb.util.StringUtil;

public class EmployController {
	private Logger log = Logger.getLogger(EmployController.class);
	private EmployService employService;
	private DeptService deptService;
	
	public void setEmployService(EmployService employService) {
		this.employService = employService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	/**
	 * 修改员工信息
	 * @param
	 * @param resp
	 * @throws Exception 
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 */
	public String editEmploy(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, FileUploadException, Exception {
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR);
			return "success";
		}
			//提取出公共方法
			Map<String, String> paramMap = parseParameter(request);
			//取参数，从参数容器中取
			String employNo = paramMap.get("employNo");
			String employName = paramMap.get("employName");
			String employSex = paramMap.get("employSex");
			String employDept = paramMap.get("employDept");
			String createTime =paramMap.get("createTime");

			// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
			// 调用service之前，需要保证参数的准确性
			if (StringUtil.isEmpty(employNo) || StringUtil.isEmpty(employName)) {
				// 跳转到错误页面，提示参数非法
				// 错误码1002，需要把错误码带到result.jsp页面
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL);
				// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
				// response);
				return "result";
			}

			// 2.调用后台service添加数据
			Employ employ = new Employ(Integer.valueOf(employNo), employName, employSex, employDept,createTime);

		
			String resultCode = ErrorCode.SUCCESS;
			try {
				// 调用修改方法
				employService.updateEmploy(employ);
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
	 * 根据模糊名字和部门分页查询
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws OAException
	 */
	
	
	public void queryCha(HttpServletRequest req, HttpServletResponse resp) throws IOException, OAException {
		ArrayList<Employ> list = new ArrayList<Employ>();
		PageModel<Employ> pageModel = new PageModel<Employ>();
		String pageNoStr = req.getParameter("pageNo");
		log.info("乐福pageNo="+pageNoStr);
		String deptName = req.getParameter("employName");
		String employDept = req.getParameter("employDept");
		PageModel<Employ> pageModel1 = employService.queryAllEmploy(pageNoStr);
		log.info("到廊坊控股和你pageNo="+pageModel1.getPageNo());
		log.info("到廊坊控股和你pageTotal="+pageModel1.getTotalPage());
		
		
		String result=null;
		int i=0;		
		int j=(pageModel1.getPageNo()-1)*pageModel1.getPageSize();
		for(Employ employ:pageModel1.getDataList()){
			if(!(deptName.trim().equals("")&&employDept.trim().equals(""))){
				if(deptName.trim().equals("")){
					if(employ.getEmployDeptName().equals(employDept)){
						if(i>=j){
							if(i<(j+pageModel1.getPageSize())){
								list.add(employ);
							}
						}		
						i++;
					}
					}else if(employDept.trim().equals("")){
						
						
						if(employ.getEmployName().contains(deptName)){
							if(i>=j){
								if(i<(j+pageModel1.getPageSize())){
									list.add(employ);
								}
							}		
							i++;
						}
				
				}else{
					
					
					if(employ.getEmployName().contains(deptName)&&employ.getEmployDeptName().equals(employDept)){						
						if(i>=j){
							if(i<(j+pageModel1.getPageSize())){
								list.add(employ);
							}
						}		
						i++;
					}
				}
				
				pageModel1.setCnt(i);
				log.info("金佛totalPage="+pageModel1.getTotalPage());
				log.info("List:"+list);
				pageModel1.setDataList(list);
				result = JSONObject.fromObject(pageModel1).toString();
				
			}else{
				result="1";
			}
			
		}		
		log.info("响应给前台，result:" + result);
		//设置响应的内容是什么格式，并且是什么编码格式
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().write(result);
	}
	/**
	 * 增加员工
	 * @throws Exception 
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 */
	public String addEmploy(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, FileUploadException, Exception {
		
		//首先判断是否为二进制提交
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR);
			return "success";
		}
		Map<String, String> paramMap = parseParameter(request);
		//提取出公共方法
		
		
		//参数校验
		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		if ( StringUtil.isEmpty(paramMap.get("employName"))
				||StringUtil.isEmpty(paramMap.get("createTime"))) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			log.info("编码，名称都不能为空");
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL);
			return "error";
		}
		
		try{
			DateUtil.getDateFromStr(paramMap.get("createTime"), "yyyy-MM-dd HH:mm:ss");
		}catch (Exception e) {
			// 失败，跳转到result
			request.setAttribute("resultCode", ErrorCode.FAIL);
			return "error";
		}
		
		// 2.调用后台service添加数据
		Employ employ = new Employ( paramMap.get("employName"), 
					paramMap.get("employSex"), paramMap.get("employDept"),paramMap.get("createTime"));
		
		try {
			employService.addEmploy(employ);
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
	 * 删除某个员工信息，根据编号
	 * @throws IOException 
	 */
	
	
	public void deleteEmploy(HttpServletRequest request,
			HttpServletResponse resp) throws IOException {
			// 1.取参数
			String employNo = request.getParameter("employNo");
			log.info("employNo:"+employNo);
			// TODO 参数合法性校验
			int count = 0;
			// 2.调用service删除
			String result = "success";
			String resultCode = ErrorCode.SUCCESS;
			try {
				count = employService.deleteByNo(employNo);
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
	
	/**
	 * 根据分页查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String queryAllEpByPage(HttpServletRequest req,
			HttpServletResponse resp) {
		//int pageSize = 3;
		String pageNoStr = req.getParameter("pageNo");
		
		PageModel<Employ> pageModel = null;
		try {
			pageModel = employService.queryAllEpByPage(pageNoStr);
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
	 * 根据某个编号查询详情
	 */
	public String employDetail(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String employNo = req.getParameter("employNo");
		
		Employ employ;
		try {
			employ = employService.queryByEmployNo(employNo);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute("resultCode", e.getErrorCode());
			// req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
			return "error";
		}
		List<Dept> deptList=deptService.queryAllDept();
		session.setAttribute("deptList", deptList);
		session.setAttribute("employ", employ);
		log.info(req.getAttribute("employ"));
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
	
}
