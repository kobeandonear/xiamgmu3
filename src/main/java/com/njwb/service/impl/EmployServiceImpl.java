package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.EmployDao;
import com.njwb.entity.Employ;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.EmployService;
import com.njwb.system.SystemProterties;
import com.njwb.transaction.Transaction;

public class EmployServiceImpl implements EmployService{
	private Logger log = Logger.getLogger(EmployServiceImpl.class);
	
	private EmployDao employDao;
	private Transaction transaction;

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public void setEmployDao(EmployDao employDao) {
		this.employDao = employDao;
	}
	
	public PageModel<Employ> queryAllEpByPage(String pageNoStr)
			throws OAException {
		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Employ> pageModel = new PageModel<Employ>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<Employ> employList = null;
		try {
			//查总数,先设置总数量
			int count = employDao.queryCount();
			pageModel.setCnt(count);
			//在setPageNo,因为pageNo在set的时候，会判断总页数
			pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
			
			employList = employDao.queryAllEpByPage(pageSize, pageModel.getPageNo());
			pageModel.setDataList(employList);
			log.info("pageModel:"+pageModel.getDataList());
		} catch (SQLException e) {
			log.info("分页查询员工所有信息失败", e);
		}
		return pageModel;
	}


	public int queryCount() throws OAException {
		int count = 0;
		try {
			count = employDao.queryCount();
		} catch (SQLException e) {
			log.info("分页查询部门数量失败", e);
		}
		return count;
	}

	public PageModel<Employ> queryAllEmploy(String pageNoStr) throws OAException {
		List<Employ> employList = null;
		String pageSizeStr = SystemProterties.getValue("pageSize");
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Employ> pageModel = new PageModel<Employ>();
		pageModel.setPageSize(pageSize);
		try {
			int count = employDao.queryCount();
			pageModel.setCnt(count);
			//在setPageNo,因为pageNo在set的时候，会判断总页数
			log.info("dfg给人打工pageNoStr="+pageNoStr);
			pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
			log.info("ldjg一份节日pageNoStr="+pageModel.getPageNo());
			employList = employDao.queryAllEmploy();
			pageModel.setDataList(employList);
		} catch (SQLException e) {
			log.info("查询员工所有信息失败", e);
		}
		return pageModel;
	}

	public int deleteByNo(String employNo) throws OAException {
		
		log.info("进行员工删除，employNo：" + employNo);
		int count=0;
		transaction.begin();
		try {
			count=employDao.deleteByNo(employNo);
			System.out.println("11111111111111111");		
			transaction.commit();
			
		} catch (SQLException e) {
			log.info("删除员工失败", e);
			//transaction.rollBack();
			throw new OAException(ErrorCode.DEL_FAIL, "数据库删除失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			log.info("删除员工失败22",e);
			throw e;
		}
		return count;
		
	}

	
	
	public void addEmploy(Employ employ) throws OAException {	
		transaction.begin();
		
		try {
						
			employDao.addEmploy(employ);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加员工失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE, "数据库添加失败");
		}
	
	}

	public Employ queryByEmployNo(String employNo) throws OAException {
		Employ employ = null;
		try {
			employ = employDao.queryByDeptNo(employNo);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e) {
			throw e;
		}
		return employ;
	}

	
	public void updateEmploy(Employ employ) throws OAException {
		try{
		transaction.begin();
		employDao.updateDept(employ);
		transaction.commit();

	} catch (SQLException e1) {
		log.info("修改员工失败", e1);
		transaction.rollBack();
		throw new OAException(ErrorCode.UPDATE_FAIL, "修改员工失败");
	} catch (OAException e) {
		// 如果数据库不止删除了一条数据，那么，需要回滚
		transaction.rollBack();
		throw e;
	}
}


	public List<Employ> queryAllEmploy() {
		List<Employ> list = null;
		try {
			list = employDao.queryAllEmploy();
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		}
		return list;
	}





}
