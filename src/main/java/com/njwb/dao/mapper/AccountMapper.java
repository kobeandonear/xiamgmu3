package com.njwb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.util.RowMapper;

public class AccountMapper implements RowMapper{
	private Logger log = Logger.getLogger(AccountMapper.class);
	
	public Object mapperObject(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setAccountNo(rs.getInt("t_account_no"));
		account.setAccountUser(rs.getString("t_account_user"));
		account.setAccountType(rs.getString("t_account_type"));
		account.setAccountYao(rs.getString("t_account_yao"));
		account.setAccountmoney(rs.getInt("t_accout_money"));
		account.setAccountTime(rs.getTimestamp("t_accout_time"));
		
		account.setAccountStatus(rs.getString("t_account_status"));
		return account;
	}
	
}
