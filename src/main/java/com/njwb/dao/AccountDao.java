package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Account;
import com.njwb.exception.OAException;

public interface AccountDao {

	public List<Account> queryAllHdByPage(int pageSize, int pageNo ,String accName) throws SQLException;

	public int queryCount() throws SQLException, OAException;

	public int deleteByNo(String accountNo) throws SQLException;

	public Account queryByDeptNo(String accountNo) throws SQLException, OAException;

	public void addAccount(Account account) throws SQLException;

	public void updateAccount(Account account) throws OAException, SQLException;

}
