package com.njwb.entity;

import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.service.impl.EmployServiceImpl;
import com.njwb.util.StringUtil;

/**
 * 分页模型 T Student ,Grade ,Employee,Dept 
 * @author Administrator
 *
 * @param <T>
 */
public class PageModel<T> {
	
	private Logger log = Logger.getLogger(PageModel.class);
	//页面容量
	private int pageSize;
	
	//当前页的页码  从1开始的1，2,3
	private int pageNo;
	
	//总记录数
	private int cnt;
	
	private int totalPage;
	
	//一页中显示的数据的集合
	private List<T> dataList;
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPage(){
		totalPage = this.cnt % this.pageSize == 0 ? this.cnt/this.pageSize : this.cnt/this.pageSize + 1;
		return totalPage;
	}
	
	/**
	 * 获取首页
	 * @return
	 */
	public int getFirstPage(){
		return 1;
	}
	
	/**
	 * 获取尾页
	 * @return
	 */
	public int getLastPage(){
		return getTotalPage();
	}

	/**
	 * 获取上一页 页码减1 
	 * @return
	 */
	public int getPrePage(){
		if(pageNo<=1){
			return 1;
		}else{
			return pageNo-1;
		}
	}
	
	/**
	 * 获取下一页  页码加1
	 * @return
	 */
	public int getNextPage(){
		if(pageNo>=getTotalPage()){
			return getTotalPage();
		}else{
			return pageNo+1;
		}
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置页码的时候，也要注意屏蔽掉特殊情况，需要先设置cnt，总数量，
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		//判断pageNo是否小于0，或者大于总页数的情况
		if(pageNo<=0 || pageNo>getTotalPage()){
			this.pageNo=1;
		}else{
			this.pageNo = pageNo;
		}
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	/**
	 * 后期web中能用上
	 * @param pageNoStr 从jsp页面获取下来的字符串
	 * @return
	 */
	public int getPageNoFromPage(String pageNoStr){
		int pageNoFrom = 0 ;
		if(null==pageNoStr){
			pageNoFrom=1;
		}else{
			//System.out.println("tfg服个软"+pageNoStr);
			log.info("tfg服个软"+pageNoStr);
			pageNoFrom = StringUtil.isEmpty(pageNoStr)? 1: Integer.valueOf(pageNoStr);
		}
		return pageNoFrom;
	}
}
