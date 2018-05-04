package com.njwb.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * @author Fly
 *
 */
public class JdbcUtil {
	private static DataSource ds = null;
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	static{
		Properties p = new Properties();
		try {
			p.load(JdbcUtil.class.getClassLoader().getResourceAsStream("datasource.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ds = BasicDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = threadLocal.get();
		if(null==conn){
			try {
				conn = ds.getConnection();
				threadLocal.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return threadLocal.get();
	}
	/**
	 * 针对事务关闭连接
	 */
	public static void close(){
		Connection conn = threadLocal.get();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		threadLocal.remove();
	}
	/**
	 * 针对每一次sql，释放资源
	 * @param ps
	 * @param rs
	 */
	public static void close(PreparedStatement ps,ResultSet rs){
		if(null!=rs){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null!=ps){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
