package com.njwb.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.impl.AccountDaoImpl;
import com.njwb.exception.OAException;

/**
 * 模板
 * @author Fly
 *
 */
public class JdbcTemplate {
	
	
	
	/**
	 * 设置占位符
	 */
	private static void setParams(PreparedStatement ps,Object... params) throws SQLException{
		if(!(params==null || params.length==0)){
			for(int i=0;i<params.length;i++){
				ps.setObject((i+1), params[i]);
			}
		}
	}
	/**
	 * 增，删，改
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int executeUpdate(String sql,Object... params) throws SQLException{
		Logger log = Logger.getLogger(JdbcTemplate.class);
		
		int rowCount = 0;
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			setParams(ps,params);
			rowCount = ps.executeUpdate();
			log.info("sql执行结果："+ps.toString());
		} catch (SQLException e) {
			throw e;
		}finally{
			JdbcUtil.close(ps, null);
		}
		return rowCount;
	}
	/**
	 * 查询
	 * @param sql
	 * @param rowMapper
	 * @param params
	 * @return
	 * @throws SQLException 
	 */
	public static List executeQuery(String sql,RowMapper rowMapper,Object...params) throws SQLException{
		List clist = new ArrayList();
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			setParams(ps,params);
			rs = ps.executeQuery();
			ps.toString();
			while(rs.next()){
				Object o = rowMapper.mapperObject(rs);
				clist.add(o);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			JdbcUtil.close(ps, rs);
		}
		return clist;
	}	
	public static Object executeOne(String sql,RowMapper rowMapper,Object...params) throws SQLException, OAException{
		List list = executeQuery(sql, rowMapper, params);
		if(list == null || list.size() == 0)
		{
			return null;
		}
		else if(list.size() == 1)
		{
			return list.get(0);
		}
		else{
			//数据有多条，抛异常
			throw new OAException(ErrorCode.DATABASE_MANY_RESULT, "数据库查询数据太多");
		}
		
	}
}
