package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.DeptDao;
import com.njwb.entity.Dept;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.DeptService;
import com.njwb.system.SystemProterties;
import com.njwb.transaction.Transaction;

public class DeptServiceImpl implements DeptService {

	private Logger log = Logger.getLogger(DeptServiceImpl.class);

	private DeptDao deptDao;

	private Transaction transaction;

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public List<Dept> queryAllDept() {
		List<Dept> deptList = null;
		try {
			deptList = deptDao.queryAllDept();
		} catch (SQLException e) {
			log.info("查询部门所有信息失败", e);
		}
		return deptList;
	}

	/**
	 * 添加部门 准确的提示，是名称重复，还是数据库添加异常 使用自定义异常（errorCode,msg）
	 * 
	 * @param dept
	 * @throws OAException
	 */
	public void addDept(Dept dept) throws OAException {
		// 重名校验，部门名称不能重复
		// 假装调用数据库查询
		/*if ("总经办".equals(dept.getDeptName()) || "渠道部".equals(dept.getDeptName())) {
			// 使用自定义异常终止流程
			throw new OAException(ErrorCode.ADD_FAIL_NAME, "名称重复");
		}*/

		transaction.begin();
		List<Dept> list1 = selectByDeptNo(dept.getDeptNo());

		try {
			List<Dept> list = queryAllDept();
			for (Dept de : list) {
				for (Dept de1 : list1) {
					if (dept.getDeptName().equals(de1.getDeptName()) && dept.getDeptLoc().equals(de1.getDeptLoc())) {
					} else {
						if (de.getDeptName().equals(dept.getDeptName()) || de.getDeptName().equals(dept.getDeptName())) {
							throw new OAException(ErrorCode.UPDATE_FAIL, "名称已存在");
						}
					}
				}
			}
			deptDao.addDept(dept);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加部门失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE, "数据库添加失败");
		}

	}

	public void deleteByNo(String deptNo) throws OAException {
		log.info("进行部门删除，deptNo：" + deptNo);
		transaction.begin();
		try {
			
			deptDao.deleteByNo(deptNo);
			System.out.println("11111111111111111");
			transaction.commit();
			
		} catch (SQLException e) {
			log.info("删除部门失败", e);
			//transaction.rollBack();
			throw new OAException(ErrorCode.DEL_FAIL, "数据库删除失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			log.info("删除部门失败22",e);
			throw e;
		}

	}
	public List<Dept> selectByDeptNo(String DeptNo) {
		List<Dept> list = null;
		try {
			list = deptDao.selectByDeptNo(DeptNo);
		} catch (SQLException e) {
			log.info("查询单个部门失败", e);
		}
		return list;
	}

	public Dept queryByDeptNo(String deptNo) throws OAException {
		Dept dept = null;
		try {
			dept = deptDao.queryByDeptNo(deptNo);
		} catch (SQLException e) {
			log.info("部门查询失败",e);
		} catch (OAException e) {
			throw e;
		}
		return dept;
	}

	public void updateDept(Dept dept) throws OAException {
		// TODO 重名校验
		transaction.begin();
		List<Dept> list1 = selectByDeptNo(dept.getDeptNo());

		try {
			List<Dept> list = queryAllDept();
			for (Dept de : list) {
				for (Dept de1 : list1) {
					if (dept.getDeptName().equals(de1.getDeptName()) && dept.getDeptLoc().equals(de1.getDeptLoc())) {
					} else {
						if (de.getDeptName().equals(dept.getDeptName()) || de.getDeptName().equals(dept.getDeptName())) {
							throw new OAException(ErrorCode.EDL_FAIL, "名称已存在");
						}
					}
				}

			}
			deptDao.updateDept(dept);
			transaction.commit();

		} catch (SQLException e) {
			log.info("修改部门失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.UPDATE_FAIL, "修改部门失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			throw e;
		}
	}

	public PageModel<Dept> queryAllByPage(String pageNoStr) throws OAException {
		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Dept> pageModel = new PageModel<Dept>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<Dept> deptList = null;
		try {
			//查总数,先设置总数量
			int count = deptDao.queryCount();
			pageModel.setCnt(count);
			//在setPageNo,因为pageNo在set的时候，会判断总页数
			pageModel.setPageNo(pageModel.getPageNoFromPage(pageNoStr));
			
			deptList = deptDao.queryAllByPage(pageSize, pageModel.getPageNo());
			pageModel.setDataList(deptList);
			log.info("pageModel:"+pageModel.getDataList());
		} catch (SQLException e) {
			log.info("分页查询部门所有信息失败", e);
		}
		return pageModel;
	}

	public int queryCount() throws OAException {
		int count = 0;
		try {
			count = deptDao.queryCount();
		} catch (SQLException e) {
			log.info("分页查询部门数量失败", e);
		}
		return count;
	}

	public List<Dept> selectByDeptName(String deptName) {
		List<Dept> list=null;
		try {
			list=deptDao.selectByDeptName(deptName);
		} catch (SQLException e) {
			log.info("根据部门名称查询部门失败",e);
		}
		return list;
	}
}
