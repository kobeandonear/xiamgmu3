package com.njwb.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



import com.njwb.constant.ErrorCode;
import com.njwb.dao.UserDao;

import com.njwb.dao.mapper.UserMapper;
import com.njwb.dao.mapper.MenuMapper;

import com.njwb.entity.Menu;
import com.njwb.entity.User;
import com.njwb.exception.OAException;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;
import org.apache.log4j.Logger;


public class UserDaoImpl implements UserDao {
	private Logger log = Logger.getLogger(UserDaoImpl.class);
	
	public User queryOne(String userName, String pwd) {
		User user = null;
		String sql = "select u.t_id,u.t_user_account,u.t_user_pwd,u.t_emp_no,u.t_role_id,u.t_create_time,u.t_user_status, "+
		" e.t_employ_name " +
		",r.t_role_name " +
		"from t_user u inner join t_employ e "
		+" on u.t_emp_no=e.t_employ_no " +
				"inner join t_role r on u.t_role_id=r.t_id" +
				" where t_user_account=? and t_user_pwd=?";
		
		try {
			List clist = JdbcTemplate.executeQuery(sql, new UserMapper(), userName,pwd);
			if(clist.size()!=0){
				user = (User)clist.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<Menu> queryLevelOne(int roleID) throws SQLException {
		//查当前角色可以有权限的菜单ID
		//select t_menu_id from t_permissions where t_role_id = ?
		//不管是一级菜单还是二级菜单，查询的菜单ID，都要在上一个sql的查询结果中
		String sql = "select t_id,t_menu_name,t_href_url,t_parent_id,t_create_time from " +
				"t_menu " +
				" where t_parent_id is null " +
				" and t_id in (select t_menu_id from t_permissions where t_role_id = ?)";
		/*select * from t_menu m
				inner join t_permissions p on m.t_id = p.t_menu_id
			   where m.t_parent_id is null
			     and p.t_role_id = ?*/
		
		
		List<Menu> menuList = JdbcTemplate.executeQuery(sql, new MenuMapper(), roleID);
		return menuList;
	}

	public List<Menu> queryMenuByParentID(int parentID, int roleID) throws SQLException {
		String sql = "select t_id,t_menu_name,t_href_url,t_parent_id,t_create_time from " +
		"t_menu " +
		" where t_parent_id = ? " +
		" and t_id in (select t_menu_id from t_permissions where t_role_id = ?)";
/*select * from t_menu m
			inner join t_permissions p on m.t_id = p.t_menu_id
		   where m.t_parent_id = 1
		     and p.t_role_id = 1  
  	* inner join 另一种写法，企业比较常见 ,如果还有关联(比如left join)，那么p表需要写在form的最后面
 	select * from t_menu m , t_permmissions p 
 			left join t_role on p.t_role_id = t_role.t_id
 			where m.t_id = p.t_menu_id 
 			  and m.t_parent_id = 1
 			  and p.t_role_id = 1 
 */
		List<Menu> menuList = JdbcTemplate.executeQuery(sql, new MenuMapper(), parentID,roleID);
		return menuList;
}

	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_user order by t_id";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}
	
	public List<User> queryAllUsByPage(int pageSize, int startIndex) throws SQLException {
		String sql = "select u.t_id,u.t_user_account,u.t_user_pwd,u.t_emp_no,u.t_role_id,u.t_create_time,u.t_user_status, "+
		" e.t_employ_name,r.t_role_name  from t_user u inner join t_employ e "
		+" on u.t_emp_no=e.t_employ_no  inner join t_role r on u.t_role_id=r.t_id   order by u.t_id limit ?,?";
		
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		List<User> list = JdbcTemplate.executeQuery(sql, new UserMapper(),
				(startIndex, pageSize);
		return list;
	}

	
	public int deleteByNo(Integer userNo) throws SQLException {
		String sql = "delete from t_user where t_id = ?";
		
		int count = JdbcTemplate.executeUpdate(sql, userNo);
		log.info("疯狂经过很久的狂热过count=" + count + ",userNo:" + userNo);
		
		return count;
	}

	public User queryByDeptNo(Integer userNo) throws SQLException, OAException {
		//String sql = "select t_id,t_user_account,t_user_pwd,t_emp_no,t_role_id,t_create_time,t_user_status from t_user where t_id=?";
		String sql="select u.t_id,u.t_user_account,u.t_user_pwd,u.t_emp_no,u.t_role_id,u.t_create_time,u.t_user_status, "+
		" e.t_employ_name,r.t_role_name  from t_user u inner join t_employ e "
		+" on u.t_emp_no=e.t_employ_no  inner join t_role r on u.t_role_id=r.t_id where u.t_id=?";
		
		return (User)JdbcTemplate.executeOne(sql, new UserMapper(), userNo);
	}

	public void addUser(User user) throws SQLException {
		String sql = "insert into t_user(t_user_account,t_user_pwd,t_emp_no,t_role_id,t_create_time,t_user_status) " + " values(?,?,?,?,?,?)";
		JdbcTemplate.executeUpdate(sql, user.getUserName(),user.getPwd(),user.getEmpNo(),user.getRoleId(),user.getCreateTime(),user.getUserStatus());
		log.info(user);
	}


	public void updateUser(User user) throws OAException, SQLException {
		int count = 0;
		String sql = "update t_user set t_user_account=?,t_user_pwd=?,t_emp_no=?,t_role_id=?,t_create_time=?,t_user_status=? where t_id=?";
		
		count = JdbcTemplate.executeUpdate(sql, user.getUserName(), user.getPwd(), user.getEmpNo(),user.getRoleId(),user.getCreateTime(),user.getUserStatus(),user.getId());
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL_User, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
	}

	public List<User> queryUserByRoleId(int roleID) {
		User user = null;
		String sql = "select u.t_id,u.t_user_account,u.t_user_pwd,u.t_emp_no,u.t_role_id,u.t_create_time,u.t_user_status, "+
		" e.t_employ_name " +
		",r.t_role_name " +
		"from t_user u inner join t_employ e "
		+" on u.t_emp_no=e.t_employ_no " +
				"inner join t_role r on u.t_role_id=r.t_id" +
				" where r.id=?";
		List clist=null;
		try {
			clist = JdbcTemplate.executeQuery(sql, new UserMapper(), roleID);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clist;
	}
}
