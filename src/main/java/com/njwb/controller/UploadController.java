package com.njwb.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.njwb.constant.Constant;
import com.njwb.constant.ErrorCode;
import com.njwb.entity.Dept;
import com.njwb.service.DeptService;
import com.njwb.system.SystemProterties;



/**
 * 文件上传
 * @author Administrator
 *
 */
public class UploadController {
	private Logger log = Logger.getLogger(UploadController.class);
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	/**
	 * 文件上传，只有一个上传控件
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadImg(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		log.info("处理文件上传请求");
		//1.判断是否是二进制提交
		if(!ServletFileUpload.isMultipartContent(req))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			req.setAttribute("resultCode", ErrorCode.ERROR);
			return "success";
		}
		
		//是二进制提交，继续。通过SerlvetFileUpload解析request，拿到请求中的控件对象集合
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		//所有的form中的控件集合
		List<FileItem> list = fileUpload.parseRequest(req);
		//循环控件
		log.info("控件集合,list.size:" + list.size());
		for (FileItem fileItem : list) {
			log.info("isFormField，是否普通文本域" + fileItem.isFormField());
			log.info("form中控件的名称：" + fileItem.getFieldName());
			log.info("fileItem.name : " + fileItem.getName());
			log.info("fileItem.size : " + fileItem.getSize());
			
			//将流写入指定的文件
			//也可以和io流中的文件复制一样，使用while循环  fileItem.getInputStream()
			
			/*BufferedInputStream bis = new BufferedInputStream(fileItem.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("D:\\uploadFile\\test.jpg")));
			byte[] b = new byte[1024];
			int length = bis.read(b);
			while(length != -1)
			{
				bos.write(b, 0, length);
				length = bis.read(b);
			}
			//关闭流
*/			log.info("iiiiodf几个v方面："+fileItem.getName());
			fileItem.write(new File("/home/soft01/uploadFile/" + fileItem.getName()));
			log.info("写入成功");
		}
		
		
		req.setAttribute("resultCode", ErrorCode.SUCCESS);
		return "success";
	}
	
	/**
	 * 文件上传，有多个控件
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String upload(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		log.info("处理文件上传请求");
		//1.判断是否是二进制提交
		if(!ServletFileUpload.isMultipartContent(req))
		{
			log.info("请求不是二进制提交，请检查表单的enctype,method");
			req.setAttribute("resultCode", ErrorCode.ERROR);
			return "success";
		}
		
		String deptNo = "";
		String deptName = "";
		String deptLoc = "";
		//存放所有的普通文本域参数
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//是二进制提交，继续。通过SerlvetFileUpload解析request，拿到请求中的控件对象集合
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		//所有的form中的控件集合
		List<FileItem> list = fileUpload.parseRequest(req);
		//循环控件
		log.info("控件集合,list.size:" + list.size());
		for (FileItem fileItem : list) {
			//需要判断是上传控件，还是普通文本域
			if(fileItem.isFormField())
			{
				//普通文本域
				//提交方式改为了二进制，那么req.getParameter()方法已经取不到参数了
				log.info("isFormField，是否普通文本域" + fileItem.isFormField());
				log.info("form中控件的名称：" + fileItem.getFieldName());
				log.info("form中控件的值：" + fileItem.getString());
				//参数放到map容器中，key为控件名称，value为控件的值
				String value = fileItem.getString();
				paramMap.put(fileItem.getFieldName(),new String(value.getBytes("iso-8859-1"),"utf-8"));
				
				/*if("deptNo".equals(fileItem.getFieldName()))
				{
					deptNo = fileItem.getString();
				}else if("deptName".equals(fileItem.getFieldName()))
				{
					deptName = fileItem.getString();
				}else if("deptLoc".equals(fileItem.getFieldName()))
				{
					deptLoc = fileItem.getString();
				}*/
			}
			else
			{
				//上传控件
				log.info("fileItem.name : " + fileItem.getName());
				log.info("fileItem.size : " + fileItem.getSize());
				
				String sourceName = fileItem.getName();
				//把文件存储路径，原始文件名称存放到parameMap参数容器中
				paramMap.put("imgRealName", sourceName);
				
				//文件名，使用重命名  4.jpg
				//时间戳+后缀
				String newFileName = (new Date()).getTime() + sourceName.substring(sourceName.lastIndexOf("."));
//				String imgPath = "D:\\uploadFile\\" + newFileName;
				// D:\\uploadFile\\--》这个地址，从配置文件中找
				String imgPath = SystemProterties.getValue(Constant.UPLOAD_PATH) + newFileName;
				
				//文件存储路径放入参数容器
				paramMap.put("imgUrl", imgPath);
				//将流写入指定的文件
				fileItem.write(new File(imgPath));
			}
			log.info("----------------------------");
		}
		
		deptNo = paramMap.get("deptNo");
		deptName = paramMap.get("deptName");
		deptLoc = paramMap.get("deptLoc");
		String imgUrl =paramMap.get("imgUrl");
		String imgRealName = paramMap.get("realName");
		log.info("deptNo:" + deptNo + ",deptName:" + deptName + ",deptLoc:" + deptLoc);
		
		Dept dept=new Dept(deptNo,deptName,deptLoc,"",imgUrl,imgRealName);
		dept.setImgUrl(paramMap.get("imgUrl"));
		dept.setImgRealName(paramMap.get("imgRealName"));
		//调用service，保存dept数据
		deptService.addDept(dept);
		
		req.setAttribute("resultCode", ErrorCode.SUCCESS);
		return "success";
	}
	
}
