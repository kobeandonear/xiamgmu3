package com.njwb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.njwb.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.entity.Holiday;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.HolidayService;
import com.njwb.util.DateUtil;
import com.njwb.util.StringUtil;
import org.apache.struts2.ServletActionContext;

public class HolidayController {
	private Logger log = Logger.getLogger(HolidayController.class);
	private HolidayService holidayService;
	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}
	
	public String queryAllHdByPage() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String pageNoStr = req.getParameter("pageNo");
		User user = (User)req.getSession().getAttribute("loginUser");
		PageModel<Holiday> pageModel = null;
		String holidayName = "";
		if(user.getRole().getRoleName().equals("普通用户")){
			holidayName =user.getUserName();
		}
		log.info("holidayName="+holidayName);
			try {
				pageModel =holidayService.queryAllHdByPage(pageNoStr,holidayName);
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
	 */
	public void deleteHoliday() throws IOException {
		// 1.取参数
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String holidayNo = request.getParameter("holidayNo");
		String holidayStatus = request.getParameter("holidayStatus");
		log.info("holidayNo:"+holidayNo);
		log.info("holidayStatus:"+holidayStatus);
		String result = "success";
		if(holidayStatus.equals("已提交")){
			
			result = "error";
		}else{
			// TODO 参数合法性校验
			int count = 0;
			// 2.调用service删除
			
			String resultCode = ErrorCode.SUCCESS;
			try {
				count = holidayService.deleteByNo(holidayNo);
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
	
	/**
	 * 根据某个编号查询详情
	 * @return 
	 */
	public String holidayDetail() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String[] list = {"事假","婚假","年假","调休","病假","丧假"};
		
		HttpSession session = req.getSession();
		String holidayNo = req.getParameter("holidayNo");
		
		String holidayStatus =req.getParameter("holidayStatus");
		
		req.getSession().setAttribute("holidayStatus",holidayStatus);
		log.info("详情holidayStatus="+holidayStatus);
		Holiday holiday;
		try {
			holiday = holidayService.queryByHolidayNo(holidayNo);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute("resultCode", e.getErrorCode());
			// req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
			return "error";
		}
		log.info("kdjfi客服号："+holiday.getHolidayType());
		List<String> list1=new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			if(!holiday.getHolidayType().equals(list[i])){				
				list1.add(list[i]);			
			}
		}
		req.setAttribute("list", list1);
		session.setAttribute("holiday", holiday);
		
		
		log.info(req.getAttribute("holiday"));
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
	
	
	public String addHoliday() throws UnsupportedEncodingException, FileUploadException, Exception {
		//首先判断是否为二进制提交
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Holiday);
			return "success";
		}
		Map<String, String> paramMap = parseParameter(request);
		//提取出公共方法
		
		
		//参数校验
		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		
		if (StringUtil.isEmpty(paramMap.get("holidayType")) || StringUtil.isEmpty(paramMap.get("holidayBz"))
				||StringUtil.isEmpty(paramMap.get("startTime"))||StringUtil.isEmpty(paramMap.get("endTime"))) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			log.info("编码，名称都不能为空");
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Holiday);
			return "error";
		}
		Date date = new Date();
		// 2.调用后台service添加数据
		if(paramMap.get("holidayStatus").equals("提交")){
			paramMap.put("holidayStatus", "已"+paramMap.get("holidayStatus"));
		}
		//Integer.valueOf(paramMap.get("holidayNo"))
		Holiday holiday = new Holiday((String)request.getSession().getAttribute("userName"),(String)paramMap.get("holidayType"), paramMap.get("holidayBz"),DateUtil.getDateFromStr(paramMap.get("startTime"),"yyyy-MM-dd HH:mm:ss"),DateUtil.getDateFromStr(paramMap.get("endTime"), "yyyy-MM-dd HH:mm:ss"),paramMap.get("holidayStatus"),date);
		
		try {
			holidayService.addHoliday(holiday);
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
	public String editHoliday() throws UnsupportedEncodingException, FileUploadException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Holiday);
			return "success";
		}
			
			//提取出公共方法
			Map<String, String> paramMap = parseParameter(request);
			//取参数，从参数容器中取
			String holidayNo = paramMap.get("holidayNo");
			String holidayUser = paramMap.get("holidayUser");
			String holidayType = paramMap.get("holidayType");
			String holidayBz = paramMap.get("holidayBz");
			String startTime = paramMap.get("startTime");
			String endTime = paramMap.get("endTime");
			String holidayStatus = paramMap.get("holidayStatus"); 
			String createTime =paramMap.get("createTime");
			log.info("holidayNo="+holidayNo);
			log.info("holidayStatus="+holidayStatus);
			log.info("原来的holidayStatus"+request.getSession().getAttribute("holidayStatus"));
			if(request.getSession().getAttribute("holidayStatus").equals("已提交")){
				request.setAttribute("resultCode", ErrorCode.EIDT_FAIL_Holiday);
				return "result";
			}
			// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
			// 调用service之前，需要保证参数的准确性
			if (StringUtil.isEmpty(holidayUser) || StringUtil.isEmpty(holidayType)||StringUtil.isEmpty(holidayBz)||StringUtil.isEmpty(startTime)||StringUtil.isEmpty(endTime)||StringUtil.isEmpty(holidayStatus)||StringUtil.isEmpty(createTime)) {
				// 跳转到错误页面，提示参数非法
				// 错误码1002，需要把错误码带到result.jsp页面
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Holiday);
				// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
				// response);
				return "result";
			}
			Date date = new Date();
			// 2.调用后台service添加数据
			Holiday holiday= new Holiday(Integer.valueOf(holidayNo),holidayUser,holidayType,holidayBz,DateUtil.getDateFromStr(startTime, "yyyy-MM-dd HH:mm:ss"),DateUtil.getDateFromStr(endTime, "yyyy-MM-dd HH:mm:ss"),holidayStatus,date);

		
			String resultCode = ErrorCode.SUCCESS_Holiday;
			// 调用修改方法
			holidayService.updateHoliday(holiday);
			request.setAttribute("resultCode", resultCode);
			// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
			// response);
			return "result";
		}
		
}
