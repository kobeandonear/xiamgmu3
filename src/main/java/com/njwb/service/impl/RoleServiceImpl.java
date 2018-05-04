package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.RoleDao;

import com.njwb.entity.Role;
import com.njwb.entity.PageModel;
import com.njwb.entity.Role;
import com.njwb.exception.OAException;
import com.njwb.service.RoleService;
import com.njwb.system.SystemProterties;
import com.njwb.transaction.Transaction;

public class RoleServiceImpl implements RoleService{
	private Logger log = Logger.getLogger(RoleServiceImpl.class);
	
	private RoleDao roleDao;
	private Transaction transaction;
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	
	
	public Role queryAllByName(String roleName) {
		
		Role role = null;
		try {
			role = roleDao.queryAllByName(roleName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}

	
	/**
	 * 分页
	 * @param pageNoStr
	 * @return
	 * @throws OAException
	 */
	public PageModel<Role> queryAllRoByPage(String pageNoStr) throws OAException {
		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Role> pageModel = new PageModel<Role>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<Role> roleList = null;
		try {
			//查总数,先设置总数量
			int count = roleDao.queryCount();
			pageModel.setCnt(count);
			//在setPageNo,因为pageNo在set的时候，会判断总页数
			pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
			
			roleList = roleDao.queryAllRoByPage(pageSize, pageModel.getPageNo());
			pageModel.setDataList(roleList);
			log.info("pageModel:"+pageModel.getDataList());
		} catch (SQLException e) {
			log.info("分页查询员工所有信息失败", e);
		}
		return pageModel;
	}

	
	public int deleteByNo(String roleNo) throws OAException {
		log.info("进行请假员工删除，employNo：" + roleNo);
		int count=0;
		transaction.begin();
		try {
			count=roleDao.deleteByNo(roleNo);
			System.out.println("11111111111111111");		
			transaction.commit();
			
		} catch (SQLException e) {
			log.info("删除请假员工失败", e);
			//transaction.rollBack();
			throw new OAException(ErrorCode.DEL_FAIL_Role, "数据库删除失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			log.info("删除请假员工失败22",e);
			throw e;
		}
		return count;
		
	}

	
	public Role queryByRoleNo(String roleNo) throws OAException {
		Role role = null;
		try {
			role = roleDao.queryByDeptNo(roleNo);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e) {
			throw e;
		}
		return role;
	}

	
	public void addRole(Role role) throws OAException {
		transaction.begin();
		
		try {
						
			roleDao.addRole(role);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加请假员工失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE_Role, "数据库添加失败");
		}
	
	}

	
	public void updateRole(Role role) throws OAException {
		try{
			transaction.begin();
			roleDao.updateRole(role);
			transaction.commit();

		} catch (SQLException e1) {
			log.info("修改员工失败", e1);
			transaction.rollBack();
			throw new OAException(ErrorCode.UPDATE_FAIL_Role, "修改请假员工失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			throw e;
		}
	}

	public List<Role> queryAllRole() {
		List<Role> roleList = null;
		try {
			roleList = roleDao.queryAllRole();
		} catch (SQLException e) {
			log.info("查询部门所有信息失败", e);
		}
		return roleList;
	}


}
