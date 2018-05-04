package com.njwb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.RoleDao;
import com.njwb.dao.mapper.DeptMapper;
import com.njwb.dao.mapper.RoleMapper;
import com.njwb.dao.mapper.RoleMapper;
import com.njwb.entity.Dept;
import com.njwb.entity.Role;
import com.njwb.entity.Role;
import com.njwb.exception.OAException;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;

public class RoleDaoImpl implements RoleDao{
	private Logger log = Logger.getLogger(RoleDaoImpl.class);

	public Role queryAllByName(String roleName) throws SQLException, OAException {
		String sql = "select t_id,t_role_name,t_create_time from t_role where t_role_name=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Role)JdbcTemplate.executeOne(sql, new RoleMapper(), roleName);

	}

	

	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_role order by t_id";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}

	
	public List<Role> queryAllRoByPage(int pageSize, int pageNo) throws SQLException {
		String sql = "select t_id,t_role_name,t_create_time from t_role order by t_id limit ?,?";
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		List<Role> list = JdbcTemplate.executeQuery(sql, new RoleMapper(),
				((pageNo - 1) * pageSize), pageSize);
		return list;
	}


	public int deleteByNo(String roleNo) throws SQLException,OAException {
		String sql = "delete from t_role where t_id = ?";
		// delete from t_dept where t_dept_no = null
		int count = JdbcTemplate.executeUpdate(sql, roleNo);
		log.info("疯狂经过很久的狂热过count=" + count + ",roleNo:" + roleNo);
		
		return count;
	}


	public Role queryByDeptNo(String roleNo) throws SQLException, OAException {
		String sql = "select t_id,t_role_name,t_create_time from t_role where t_id=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Role)JdbcTemplate.executeOne(sql, new RoleMapper(), roleNo);

	}


	public void addRole(Role role) throws SQLException {
		String sql = "insert into t_role(t_role_name,t_create_time) values(?,now())";
		JdbcTemplate.executeUpdate(sql, role.getRoleName());	
		log.info(role);
	}


	public void updateRole(Role role) throws SQLException, OAException {
		int count = 0;
		String sql = "update t_role set t_role_name=?, t_create_time=now() where t_id=?";
		
		count = JdbcTemplate.executeUpdate(sql, role.getRoleName(),role.getId());
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL_Role, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
		
	}



	public List<Role> queryAllRole() throws SQLException {
		String sql = "select t_id,t_role_name,t_create_time from t_role";
		List<Role> list = JdbcTemplate.executeQuery(sql, new RoleMapper(), null);
		return list;
	}


}
