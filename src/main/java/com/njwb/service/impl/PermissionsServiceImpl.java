package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.PermissionsDao;
import com.njwb.entity.PageModel;
import com.njwb.entity.Permissions;
import com.njwb.entity.Permissions;
import com.njwb.entity.Permissions;

import com.njwb.exception.OAException;
import com.njwb.service.PermissionsService;
import com.njwb.system.SystemProterties;

import com.njwb.transaction.Transaction;

public class PermissionsServiceImpl implements PermissionsService{
	private Logger log = Logger.getLogger(PermissionsServiceImpl.class);
	private Transaction transaction;
	private PermissionsDao permissionsDao;
	
	public void setPermissionsDao(PermissionsDao permissionsDao) {
		this.permissionsDao = permissionsDao;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	
	public PageModel<Permissions> queryAllPerByPage(String pageNoStr) {

		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Permissions> pageModel = new PageModel<Permissions>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<Permissions> permList = null;
		//查总数,先设置总数量
		int count;
		try {
			
			count = permissionsDao.queryCount();
			pageModel.setCnt(count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//在setPageNo,因为pageNo在set的时候，会判断总页数
		pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
		
		try {
			permList = permissionsDao.queryAllUsByPage(pageSize, pageModel.getPageNo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageModel.setDataList(permList);
		log.info("pageModel:"+pageModel.getDataList());
		return pageModel;
	}

	public int deleteByNo(String permissionsNo) throws OAException {
		log.info("进行账户删除，permissionsNo：" + permissionsNo);
		int count=0;
		transaction.begin();
		count=permissionsDao.deleteByNo(permissionsNo);
		System.out.println("11111111111111111");		
		transaction.commit();
		return count;
	}

	public Permissions queryByPermissionsNo(int  permissionsNo) throws OAException {
		Permissions permissions = null;
		try {
			permissions = permissionsDao.queryByDeptNo(permissionsNo);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e1) {
			throw e1;
		}
		return permissions;
	}

	/**
	 * 增加
	 */
	public void addPermissions(Permissions permissions) throws OAException {
		transaction.begin();
		
		try {
						
			permissionsDao.addPermissions(permissions);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加报销员工失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE_Permissions, "数据库添加失败");
		}
	
	}
	
	
	

	public void updatePermissions(Permissions permissions) throws OAException {
		try{
			transaction.begin();
			permissionsDao.updatePermissions(permissions);
			transaction.commit();

		} catch (SQLException e1) {
			log.info("修改报销员工失败", e1);
			transaction.rollBack();
			throw new OAException(ErrorCode.UPDATE_FAIL_Permissions, "修改报销员工失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			throw e;
		}
	}
	public Permissions queryAllById(int roleId, int menuId) throws OAException {
		Permissions permissions = null;
		try {
			permissions = permissionsDao.queryById(roleId,menuId);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e1) {	
				throw e1;	
		}
		return permissions;
	}
	public List<Permissions> queryAll() throws SQLException  {
		List<Permissions> list  = null;
	
			try {
				list = permissionsDao.queryAll();
			} catch (SQLException e) {
				throw e;					
			}
		
		return list;
	}
}
