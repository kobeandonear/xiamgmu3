package com.njwb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.AccountDao;
import com.njwb.dao.mapper.AccountMapper;
import com.njwb.dao.mapper.HolidayMapper;
import com.njwb.entity.Account;
import com.njwb.entity.Account;
import com.njwb.entity.Holiday;
import com.njwb.exception.OAException;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;

public class AccountDaoImpl implements AccountDao{
	private Logger log = Logger.getLogger(AccountDaoImpl.class);
	
	
	
	
	
	
	public List<Account> queryAllHdByPage(int pageSize, int pageNo,String accName) throws SQLException {
		List<Account> list;
		if(!accName.equals("")){
			String sql = "select t_account_no,t_account_user,t_account_type,t_account_yao,t_accout_money,t_accout_time,t_account_status from t_account where t_account_user=? order  by t_account_no limit ?,?";
			list = JdbcTemplate.executeQuery(sql, new AccountMapper(),accName,
					((pageNo - 1) * pageSize), pageSize);
		}else{
			String sql = "select t_account_no,t_account_user,t_account_type,t_account_yao,t_accout_money,t_accout_time,t_account_status from t_account order by t_account_no limit ?,?";
			list = JdbcTemplate.executeQuery(sql, new AccountMapper(),
					((pageNo - 1) * pageSize), pageSize);
			
		}
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		return list;
	}

	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_account order by t_account_no";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}

	public int deleteByNo(String accountNo) throws SQLException {
		String sql = "delete from t_account where t_account_no = ?";
		// delete from t_dept where t_dept_no = null
		int count = JdbcTemplate.executeUpdate(sql, accountNo);
		log.info("疯狂经过很久的狂热过count=" + count + ",accountNo:" + accountNo);
		
		return count;
	}

	public Account queryByDeptNo(String accountNo) throws SQLException, OAException {
		String sql = "select t_account_no,t_account_user,t_account_type,t_account_yao,t_accout_money,t_accout_time,t_account_status from t_account where t_account_no=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Account)JdbcTemplate.executeOne(sql, new AccountMapper(), accountNo);
	}

	public void addAccount(Account account) throws SQLException {
		String sql = "insert into t_account(t_account_no,t_account_user,t_account_type,t_accout_money,t_accout_time,t_account_status,t_account_yao) " + " values(?,?,?,?,?,?,?)";
		log.info(account);
		JdbcTemplate.executeUpdate(sql, account.getAccountNo(),account.getAccountUser(),account.getAccountType(),account.getAccountmoney(),account.getAccountTime(),account.getAccountStatus(),account.getAccountYao());
		log.info(account);
	}

	public void updateAccount(Account account) throws OAException, SQLException {
		int count = 0;
		String sql = "update t_account set t_account_user=?, t_account_type=?,t_accout_money=?,t_accout_time=?,t_account_status=? where t_account_no=?";
		
		count = JdbcTemplate.executeUpdate(sql, account.getAccountUser(), account.getAccountType(), account.getAccountmoney(),account.getAccountTime(),account.getAccountStatus(),account.getAccountNo());
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL_Account, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
	}
}

