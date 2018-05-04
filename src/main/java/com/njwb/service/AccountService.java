package com.njwb.service;

import com.njwb.entity.Account;
import com.njwb.entity.PageModel;
import com.njwb.exception.OAException;

public interface AccountService {

	public PageModel<Account> queryAllHdByPage(String pageNoStr,String accName);

	public int deleteByNo(String accountNo) throws OAException;

	public Account queryByHolidayNo(String accountNo) throws OAException;

	public void addAccount(Account account) throws OAException;

	public void updateAccount(Account account) throws OAException;

}
