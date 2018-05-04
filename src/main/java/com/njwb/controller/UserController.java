package com.njwb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.njwb.entity.*;
import com.njwb.system.ApplicationContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.njwb.constant.ErrorCode;
import com.njwb.entity.User;
import com.njwb.exception.OAException;
import com.njwb.service.EmployService;
import com.njwb.service.RoleService;
import com.njwb.service.UserService;

import com.njwb.system.SystemProterties;
import com.njwb.util.MakeCertPic;
import com.njwb.util.StringUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserController {
    private Logger log = Logger.getLogger(UserController.class);
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private RoleService roleService;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    private EmployService employService;

    public void setEmployService(EmployService employService) {
        this.employService = employService;
    }

    public void codeImg() throws IOException {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        //1.生成验证码图片
        String code = MakeCertPic.getCertPic(60, 20, resp.getOutputStream());
        //2.拿到验证码结果
        //3.将结果保存到session
        req.getSession().setAttribute("validaterCode", code);
        //4.图片返回给浏览器,图片已经写到response中的出入流中了
        log.info("验证码：" + code);

    }


    /**
     * 登陆
     *
     * @param
     * @param
     * @return 成功success， 失败error
     */
    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        String userName = request.getParameter("userName");
        request.getSession().setAttribute("userName", userName);
        log.info("登陆的用户名=" + userName);
        String pwd = request.getParameter("password");
        log.info("登陆的密码：" + pwd);
        String code = request.getParameter("code");

        //校验验证码是否输入正确
        String validaterCode = (String) request.getSession().getAttribute("validaterCode");
        if (!validaterCode.equals(code)) {
            request.setAttribute("resultCode", ErrorCode.LOGIN_FAIL_CODE);
            return "error";
        }


        //调用service,根据用户名，密码查询数据库，是否有这个用户

        User user = userService.queryOne(userName, pwd);
        log.info("之官罚款i=" + user.toString());
        //一般密码，数据库存储的，一般是加密后的字符串，一般来说不可逆，目前使用比较多的加密方式，MD5加密
        if (user.getUserName() != null) {

            //使用session存储user对象,如果key值重复，那么value会覆盖
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
//    		response.sendRedirect(request.getContextPath() + "/njwb/main.jsp");

            List<Menu> menuList = userService.queryMenu(user.getRoleId());
            log.info("menuList:" + menuList);
            session.setAttribute("menuList", menuList);

            Employ employ = null;
            try {
                employ = employService.queryByEmployNo(user.getEmpNo());
            } catch (OAException e) {
                request.setAttribute("resultCode", ErrorCode.LOGIN_FAIL);
                return "error";
            }
            session.setAttribute("employ", employ);
            session.setAttribute("tomcatPath", SystemProterties.getValue("tomcatPath"));
            return "success";
        } else {
            log.info("登录失败，userName:" + userName + ",pwd:" + pwd);
            //如果没有，跳转到错误页面，提示（“用户名，密码不匹配”），然后再跳回登陆页面
            //使用转发,/打头，那么地址是从webRoot开始，转发是在服务器内部进行
            request.setAttribute("resultCode", ErrorCode.LOGIN_FAIL);
//	    	request.getRequestDispatcher("/njwb/result.jsp").forward(request, response);
            return "error";
        }
    }


    public String editPwd() throws UnsupportedEncodingException, FileUploadException, Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        if (!ServletFileUpload.isMultipartContent(request)) {
            log.info("请求不是二进制提交，请检查表单的enctype,method");
            request.setAttribute("resultCode", ErrorCode.ERROR_Permissions);
            return "success";
        }

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        //提取出公共方法
        Map<String, String> paramMap = parseParameter(request);
        //取参数，从参数容器中取
        String oldPwd = paramMap.get("oldPwd");
        String newPwd = paramMap.get("newPwd");
        String cofPwd = paramMap.get("cofPwd");


        log.info("oldPwd=" + oldPwd);
        log.info("newPwd=" + newPwd);
        log.info("cofPwd=" + cofPwd);
        // 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
        // 调用service之前，需要保证参数的准确性


        if (StringUtil.isEmpty(cofPwd.trim()) || StringUtil.isEmpty(newPwd.trim()) || StringUtil.isEmpty(oldPwd.trim())) {
            // 跳转到错误页面，提示参数非法
            // 错误码1002，需要把错误码带到result.jsp页面
            request.setAttribute("resultCode", ErrorCode.EDIT_FAIL_Pwd);
            // request.getRequestDispatcher("/njwb/result.jsp").forward(request,
            // response);
            return "result";
        }
        String resultCode = ErrorCode.SUCCESS_EDIT_PWD;
        if (oldPwd.equals(loginUser.getPwd().trim()) && cofPwd.trim().equals(newPwd.trim())) {
            User user = new User(loginUser.getId(), loginUser.getUserName(), newPwd, loginUser.getEmpNo(), loginUser.getEmploy(), loginUser.getUserStatus(), loginUser.getRole(), loginUser.getRoleId(), loginUser.getCreateTime());

            // 调用修改方法
            try {
                userService.updateUser(user);
            } catch (OAException e) {
                resultCode = ErrorCode.EDIT_FAIL_User3;
            }

        } else {
            resultCode = ErrorCode.EDIT_FAIL_User2;
        }
        request.setAttribute("resultCode", resultCode);
        return "result";
    }

    /**
     * 退出系统
     *
     * @throws IOException
     */
    public void exit() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        request.getSession().invalidate();

        //request.setAttribute("resultCode", ErrorCode.EXIT);
        String result = "success";
        //结果返回给result
        log.info("响应给前台，result:" + result);
        //如果不设置，那么浏览器接收到请求，会将结果作为xml解析，这个时候解析会出错，浏览器控制台会报语法错误，但是并不影响功能
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(result);
    }

    public String exit2() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        request.getSession().invalidate();
        String resultCode = ErrorCode.EXIT2;

        request.setAttribute("resultCode", resultCode);
        // request.getRequestDispatcher("/njwb/result.jsp").forward(request,
        // response);
        return "result";
    }


    public String queryAllUsByPage() {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        String pageNoStr = req.getParameter("pageNo");

        PageModel<User> pageModel = null;
        pageModel = userService.queryAllUsByPage(pageNoStr);

        req.setAttribute("pageMode", pageModel);
        log.info(pageModel.getDataList());
        //可以在登录的时候，丢到session中
        /*req.setAttribute("tomcatPath", SystemProterties.getValue("tomcatPath"));
		log.info("路径："+SystemProterties.getValue("tomcatPath"));*/
        // 页面不跳转，直接返回success
        return "success";
    }

    /**
     * 删除信息
     *
     * @throws IOException
     */
    public void delUser() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        // 1.取参数
        String userNo = request.getParameter("userNo");
        String userStatus = request.getParameter("userStatus");
        log.info("userNo:" + userNo);

        String result = "success";

        // TODO 参数合法性校验
        int count = 0;
        // 2.调用service删除

        String resultCode = ErrorCode.SUCCESS_User;
        try {
            count = userService.deleteByNo(userNo);
            // 3.页面跳转,result。jsp页面提示操作成功
        } catch (OAException e) {
            result = "error";
            // 失败，跳转到result
            resultCode = e.getErrorCode();
        }
        if (count != 1) {
            result = "error";
        } else {
            result = "success";
        }


        //结果返回给result
        log.info("响应给前台，result:" + result);
        //如果不设置，那么浏览器接收到请求，会将结果作为xml解析，这个时候解析会出错，浏览器控制台会报语法错误，但是并不影响功能
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(result);

    }


    /**
     * 增加
     *
     * @param request
     * @param resp
     * @return
     * @throws Exception
     * @throws FileUploadException
     * @throws UnsupportedEncodingException
     */
    public String addUser() throws UnsupportedEncodingException, FileUploadException, Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        //首先判断是否为二进制提交
        if (!ServletFileUpload.isMultipartContent(request)) {
            log.info("请求不是二进制提交，请检查表单的enctype,method");
            request.setAttribute("resultCode", ErrorCode.ERROR_User);
            return "success";
        }
        Map<String, String> paramMap = parseParameter(request);
        //提取出公共方法


        //参数校验
        // 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
        // 调用service之前，需要保证参数的准确性

        if (StringUtil.isEmpty(paramMap.get("userName")) || StringUtil.isEmpty(paramMap.get("employId")) || StringUtil.isEmpty(paramMap.get("userPwd"))
                || StringUtil.isEmpty(paramMap.get("employName")) || StringUtil.isEmpty(paramMap.get("roleStatus")) || StringUtil.isEmpty(paramMap.get("roleName")) || StringUtil.isEmpty(paramMap.get("userStatus"))) {
            // 跳转到错误页面，提示参数非法
            // 错误码1002，需要把错误码带到result.jsp页面
            log.info("编码，名称都不能为空");
            request.setAttribute("resultCode", ErrorCode.ADD_FAIL_User);
            return "error";
        }
        Date date = new Date();
        // 2.调用后台service添加数据

        Employ employ = employService.queryByEmployNo(paramMap.get("employId"));
        Role role = roleService.queryAllByName(paramMap.get("roleName"));
        log.info(employ.toString());
        log.info(role.toString());
        if (employ == null || role == null) {
            log.info("员工或角色找不到！无法添加");
            request.setAttribute("resultCode", ErrorCode.ADD_FAIL_User2);
            return "error";
        }

        //Integer.valueOf(paramMap.get("userNo"))
        User user = new User(paramMap.get("userName"), paramMap.get("userPwd"), paramMap.get("employId"), employ, paramMap.get("roleStatus"), role, role.getId(), date);

        try {
            userService.addUser(user);
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
     *
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
            if (fileItem.isFormField()) {
                //普通文本域
                //拿普通文本域的值
                String value = fileItem.getString();
                //如果为空，直接空串，不为空，进行转码
                value = StringUtil.isEmpty(value) ? "" : new String(value.getBytes("iso-8859-1"), "utf-8");
                //将参数放入容器
                paramMap.put(fileItem.getFieldName(), value);
            }

        }
        return paramMap;
    }

    /**
     * 查询账户详情
     *
     * @return
     */

    public String userDetail() {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        HttpSession session = req.getSession();
        String userNo = req.getParameter("userNo");
        User user;
        List<Role> rolelist = roleService.queryAllRole();
        try {
            user = userService.queryByUserNo(userNo);
        } catch (OAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            session.setAttribute("resultCode", e.getErrorCode());
            // req.getRequestDispatcher("/njwb/result.jsp").forward(req, resp);
            return "error";
        }
        session.setAttribute("Rolelist", rolelist);
        session.setAttribute("user", user);
        log.info(req.getAttribute("user"));
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
     * 编辑
     *
     * @param request
     * @param resp
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileUploadException
     * @throws Exception
     */
    public String editUser() throws UnsupportedEncodingException, FileUploadException, Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse resp = ServletActionContext.getResponse();
        if (!ServletFileUpload.isMultipartContent(request)) {
            log.info("请求不是二进制提交，请检查表单的enctype,method");
            request.setAttribute("resultCode", ErrorCode.ERROR_User);
            return "success";
        }

        //提取出公共方法
        Map<String, String> paramMap = parseParameter(request);
        //取参数，从参数容器中取
        String userName = paramMap.get("userName");
        String pwd = paramMap.get("pwd");
        String empNo = paramMap.get("empNo");
        String employName = paramMap.get("employName");
        String userStatus = paramMap.get("userStatus");
        String roleName = paramMap.get("roleName");


        log.info("userStatus=" + userStatus);
        log.info("roleName=" + roleName);

        // 参数的合法性校验（比如为空，长度。。），请求有合法（来源于deptAdd.jsp）也有非法（比如浏览器直接请求）
        // 调用service之前，需要保证参数的准确性
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(pwd) || StringUtil.isEmpty(empNo) || StringUtil.isEmpty(employName) || StringUtil.isEmpty(userStatus) || StringUtil.isEmpty(roleName)) {
            // 跳转到错误页面，提示参数非法
            // 错误码1002，需要把错误码带到result.jsp页面
            request.setAttribute("resultCode", ErrorCode.ADD_FAIL_User3);
            // request.getRequestDispatcher("/njwb/result.jsp").forward(request,
            // response);
            return "result";
        }

        Employ employ = employService.queryByEmployNo(paramMap.get("empNo"));
        Role role = roleService.queryAllByName(paramMap.get("roleName"));


        if (employ == null || role == null || (!employ.getEmployName().equals(employName))) {
            log.info("员工编号与员工姓名不一致或角色找不到！不能修改");
            request.setAttribute("resultCode", ErrorCode.EDIT_FAIL_User);
            return "result";
        }


        User user1 = (User) request.getSession().getAttribute("user");
        int id = user1.getId();
        Date date = new Date();

        User user = new User(id, userName, pwd, empNo, employ, userStatus, role, role.getId(), date);


        String resultCode = ErrorCode.SUCCESS_User;
        // 调用修改方法
        userService.updateUser(user);
        request.setAttribute("resultCode", resultCode);
        // request.getRequestDispatcher("/njwb/result.jsp").forward(request,
        // response);
        return "result";

    }
}
