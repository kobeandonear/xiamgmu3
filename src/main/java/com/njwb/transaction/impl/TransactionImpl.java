package com.njwb.transaction.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.njwb.transaction.Transaction;
import com.njwb.util.JdbcUtil;

public class TransactionImpl implements Transaction{
	public TransactionImpl() {
		System.out.println("调用了TransactionImpl()无参");
	}
	public void begin() {
		//获取连接对象
		Connection conn = JdbcUtil.getConnection();
		//设置自动提交为手动提交
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		//获取连接对象
		Connection conn = JdbcUtil.getConnection();
		//提交事务
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//清除连接
			JdbcUtil.close();
		}
	}

	public void rollBack() {
		//获取连接
		Connection conn = JdbcUtil.getConnection();
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close();
		}
	}

}
