package com.njwb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.PermissionsDao;
import com.njwb.dao.mapper.PermissionsMapper;
import com.njwb.dao.mapper.PermissionsMapper;

import com.njwb.entity.Permissions;
import com.njwb.entity.Permissions;
import com.njwb.exception.OAException;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;

public class PermissionsDaoImpl implements PermissionsDao{
	private Logger log = Logger.getLogger(PermissionsDaoImpl.class);
	
	public int deleteByNo(String permissionsNo) {
		String sql = "delete from t_permissions where t_id = ?";
		
		int count = 0;
		try {
			count = JdbcTemplate.executeUpdate(sql, permissionsNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("疯狂经过很久的狂热过count=" + count + ",permissionsNo:" + permissionsNo);
		
		return count;
	}

	public List<Permissions> queryAllUsByPage(int pageSize, int pageNo) throws SQLException {
		String sql = "select p.t_id,p.t_role_id,p.t_menu_id,p.t_create_time, "+
		" m.t_menu_name,r.t_role_name  from t_permissions p inner join t_menu m "
		+" on p.t_menu_id=m.t_id  inner join t_role r on p.t_role_id=r.t_id   order by p.t_id limit ?,?";
		
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		List<Permissions> list = JdbcTemplate.executeQuery(sql, new PermissionsMapper(),
				((pageNo - 1) * pageSize), pageSize);
		return list;
	}
	public Permissions queryByDeptNo(int permissionsNo) throws SQLException, OAException {
		//String sql = "select t_id,t_permissions_account,t_permissions_pwd,t_emp_no,t_role_id,t_create_time,t_permissions_status from t_permissions where t_id=?";
	
		String sql = "select p.t_id,p.t_role_id,p.t_menu_id,p.t_create_time, "+
		" m.t_menu_name,r.t_role_name  from t_permissions p inner join t_menu m "
		+" on p.t_menu_id=m.t_id  inner join t_role r on p.t_role_id=r.t_id   where p.t_id=?";
		
		return (Permissions)JdbcTemplate.executeOne(sql, new PermissionsMapper(), permissionsNo);
	}

	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_permissions order by t_id";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}

	
	
	
	
	
	public void updatePermissions(Permissions permissions) throws SQLException, OAException {
		int count = 0;
		String sql = "update t_permissions set t_role_id=?,t_menu_id =?,t_create_time=? where t_id=?";
		
		count = JdbcTemplate.executeUpdate(sql, permissions.getRoleId(), permissions.getMenuId(), permissions.getCreateTime(),permissions.getId());
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL_Permissions, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
	}

	public void addPermissions(Permissions permissions) throws SQLException {
		String sql = "insert into t_permissions(t_role_id,t_menu_id,t_create_time) " + " values(?,?,?)";
		JdbcTemplate.executeUpdate(sql, permissions.getRoleId(),permissions.getMenuId(),permissions.getCreateTime());
		log.info(permissions);
	}

	public Permissions queryById(int roleId, int menuId) throws SQLException, OAException {
		String sql = "select p.t_id,p.t_role_id,p.t_menu_id,p.t_create_time, "+
		" m.t_menu_name,r.t_role_name  from t_permissions p inner join t_menu m "
		+" on p.t_menu_id=m.t_id  inner join t_role r on p.t_role_id=r.t_id   where p.t_role_id=? and p.t_menu_id=?";
		
		return (Permissions)JdbcTemplate.executeOne(sql, new PermissionsMapper(), roleId,menuId);

	}

	public List<Permissions> queryAll() throws SQLException {
		String sql = "select p.t_id,p.t_role_id,p.t_menu_id,p.t_create_time, "+
		" m.t_menu_name,r.t_role_name  from t_permissions p inner join t_menu m "
		+" on p.t_menu_id=m.t_id  inner join t_role r on p.t_role_id=r.t_id";
		
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		List<Permissions> list = JdbcTemplate.executeQuery(sql, new PermissionsMapper());
		return list;
	}

}
