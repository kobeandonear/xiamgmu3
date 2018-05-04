package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.AccountDao;
import com.njwb.dao.AccountDao;
import com.njwb.entity.Account;
import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;
import com.njwb.service.AccountService;
import com.njwb.system.SystemProterties;
import com.njwb.transaction.Transaction;

public class AccountServiceImpl implements AccountService{
	private Logger log = Logger.getLogger(AccountServiceImpl.class);
	private AccountDao accountDao;
	private Transaction transaction;
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	
	public PageModel<Account> queryAllHdByPage(String pageNoStr,String accName) {
		String pageSizeStr = SystemProterties.getValue("pageSize");
		log.info("pageSize="+pageSizeStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		PageModel<Account> pageModel = new PageModel<Account>();
		
		pageModel.setPageSize(pageSize);

		//数据
		List<Account> accountList = null;
		//查总数,先设置总数量
		int count;
		try {
			count = accountDao.queryCount();
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
			accountList = accountDao.queryAllHdByPage(pageSize, pageModel.getPageNo(),accName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageModel.setDataList(accountList);
		log.info("pageModel:"+pageModel.getDataList());
		return pageModel;
	}

	/**
	 * 删除
	 * @throws OAException 
	 */
	public int deleteByNo(String accountNo) throws OAException {
		log.info("进行请假员工删除，employNo：" + accountNo);
		int count=0;
		transaction.begin();
		try {
			count=accountDao.deleteByNo(accountNo);
			System.out.println("11111111111111111");		
			transaction.commit();
			
		} catch (SQLException e) {
			log.info("删除请假员工失败", e);
			//transaction.rollBack();
			throw new OAException(ErrorCode.DEL_FAIL_Account, "数据库删除失败");
		}
		return count;
		
	}

	public Account queryByHolidayNo(String accountNo) throws OAException {
		Account account = null;
		try {
			account = accountDao.queryByDeptNo(accountNo);
		} catch (SQLException e) {
			log.info("员工查询失败",e);
		} catch (OAException e) {
			throw e;
		}
		return account;
	}

	
	public void addAccount(Account account) throws OAException {
		transaction.begin();
		
		try {
						
			accountDao.addAccount(account);
			transaction.commit();

		} catch (SQLException e) {
			log.info("添加报销员工失败", e);
			transaction.rollBack();
			throw new OAException(ErrorCode.ADD_FAIL_DATABASE_Account, "数据库添加失败");
		}
	
	}

	public void updateAccount(Account account) throws OAException {
		try{
			transaction.begin();
			accountDao.updateAccount(account);
			transaction.commit();

		} catch (SQLException e1) {
			log.info("修改报销员工失败", e1);
			transaction.rollBack();
			throw new OAException(ErrorCode.UPDATE_FAIL_Account, "修改报销员工失败");
		} catch (OAException e) {
			// 如果数据库不止删除了一条数据，那么，需要回滚
			transaction.rollBack();
			throw e;
		}
	}

}
