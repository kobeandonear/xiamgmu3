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

import com.njwb.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.entity.Account;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.AccountService;
import com.njwb.util.StringUtil;

public class AccountController {
	private Logger log = Logger.getLogger(AccountController.class);
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	
	
	
	
	
	public String queryAllAcByPage(HttpServletRequest req,
			HttpServletResponse resp) {
		String pageNoStr = req.getParameter("pageNo");
		User user = (User)req.getSession().getAttribute("loginUser");
		PageModel<Account> pageModel = null;
		String accName = "";
		if(user.getRole().getRoleName().equals("普通用户")){
			accName = user.getUserName();
		}
		pageModel = accountService.queryAllHdByPage(pageNoStr ,accName);
		
		req.setAttribute("pageMode", pageModel);
		log.info(pageModel.getDataList());
		//可以在登录的时候，丢到session中
		/*req.setAttribute("tomcatPath", SystemProterties.getValue("tomcatPath"));
		log.info("路径："+SystemProterties.getValue("tomcatPath"));*/
		// 页面不跳转，直接返回success
		return "success";
	}
	
	/**
	 * 删除报销信息
	 * @throws IOException 
	 */
	public void deleteAccount(HttpServletRequest request,
			HttpServletResponse resp) throws IOException {
		// 1.取参数
		String accountNo = request.getParameter("accountNo");
		String accountStatus = request.getParameter("accountStatus");
		log.info("accountNo:"+accountNo);
		log.info("accountStatus:"+accountStatus);
		String result = "success";
		if(accountStatus.equals("已提交")){
			
			result = "error";
		}else{
			// TODO 参数合法性校验
			int count = 0;
			// 2.调用service删除
			
			String resultCode = ErrorCode.SUCCESS_Account;
			try {
				count = accountService.deleteByNo(accountNo);
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
	 * 查询报销详情
	 * @return
	 */
	
	public String accountDetail(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String accountNo = req.getParameter("accountNo");
		
		String accountStatus =req.getParameter("accountStatus");
		
		req.getSession().setAttribute("accountStatus",accountStatus);
		log.info("详情accountStatus="+accountStatus);
		Account account;
		try {
			account = accountService.queryByHolidayNo(accountNo);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute("resultCode", e.getErrorCode());
			// req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
			return "error";
		}
		
		session.setAttribute("account", account);
		log.info(req.getAttribute("account"));
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
	 * 增加
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 */
	public String addAccount(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, FileUploadException, Exception {
		//首先判断是否为二进制提交
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Account);
			return "success";
		}
		Map<String, String> paramMap = parseParameter(request);
		//提取出公共方法
		
		
		//参数校验
		// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
		// 调用service之前，需要保证参数的准确性
		
		if (StringUtil.isEmpty(paramMap.get("accountType")) || StringUtil.isEmpty(paramMap.get("accountYao"))
				||StringUtil.isEmpty(paramMap.get("accountmoney"))) {
			// 跳转到错误页面，提示参数非法
			// 错误码1002，需要把错误码带到result.jsp页面
			log.info("编码，名称都不能为空");
			request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Account);
			return "error";
		}
		Date date = new Date();
		// 2.调用后台service添加数据
		if(paramMap.get("accountStatus").equals("提交")){
			paramMap.put("accountStatus", "已"+paramMap.get("accountStatus"));
		}
		log.info(paramMap.get("accountYao"));
		//Integer.valueOf(paramMap.get("accountNo"))
		Account account = new Account((String)request.getSession().getAttribute("userName"),(String)paramMap.get("accountType"), paramMap.get("accountYao"),Integer.valueOf(paramMap.get("accountmoney")),date,paramMap.get("accountStatus"));
		
		try {
			accountService.addAccount(account);
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
	 * 编辑
	 * @param request
	 * @param resp
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileUploadException
	 * @throws Exception
	 */
	public String editAccount(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, FileUploadException, Exception {
		if(!ServletFileUpload.isMultipartContent(request))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			request.setAttribute("resultCode", ErrorCode.ERROR_Account);
			return "success";
		}
			
			//提取出公共方法
			Map<String, String> paramMap = parseParameter(request);
			//取参数，从参数容器中取
			String accountNo = paramMap.get("accountNo");
			String accountUser = paramMap.get("accountUser");
			String accountType = paramMap.get("accountType");
			String accountYao= paramMap.get("accountYao");
			String accountmoney = paramMap.get("accountmoney");
			String accountTime = paramMap.get("accountTime");
			String accountStatus = paramMap.get("accountStatus"); 

			log.info("accountNo="+accountNo);
			log.info("accountStatus="+accountStatus);
			log.info("原来的accountStatus"+request.getSession().getAttribute("accountStatus"));
			if(request.getSession().getAttribute("accountStatus").equals("已提交")){
				request.setAttribute("resultCode", ErrorCode.EIDT_FAIL_Account);
				return "result";
			}
			// 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
			// 调用service之前，需要保证参数的准确性
			if (StringUtil.isEmpty(accountUser) || StringUtil.isEmpty(accountType)||StringUtil.isEmpty(accountmoney)||StringUtil.isEmpty(accountTime)||StringUtil.isEmpty(accountStatus)) {
				// 跳转到错误页面，提示参数非法
				// 错误码1002，需要把错误码带到result.jsp页面
				request.setAttribute("resultCode", ErrorCode.ADD_FAIL_Account);
				// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
				// response);
				return "result";
			}
			Date date = new Date();
			// 2.调用后台service添加数据
			Account account= new Account(Integer.valueOf(accountNo),accountUser,accountType,accountYao,Integer.valueOf(accountmoney),date,accountStatus);

		
			String resultCode = ErrorCode.SUCCESS_Account;
			// 调用修改方法
			accountService.updateAccount(account);
			request.setAttribute("resultCode", resultCode);
			// request.getRequestDispatcher("/njwb/result.jsp").forward(request,
			// response);
			return "result";

	}
}
