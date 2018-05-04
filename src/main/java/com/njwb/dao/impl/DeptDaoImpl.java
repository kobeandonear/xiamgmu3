package com.njwb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.njwb.constant.ErrorCode;
import com.njwb.dao.DeptDao;
import com.njwb.dao.mapper.DeptMapper;
import com.njwb.entity.Dept;
import com.njwb.exception.OAException;
import com.njwb.util.JdbcTemplate;
import com.njwb.util.RowMapper;
import com.njwb.util.StringUtil;

public class DeptDaoImpl implements DeptDao {
	private Logger log = Logger.getLogger(DeptDaoImpl.class);

	public List<Dept> queryAllDept() throws SQLException {
		String sql = "select t_dept_no,t_dept_name,t_dept_loc,t_dept_manager,t_create_time,t_img_url, t_img_real_name from t_dept";
		List<Dept> list = JdbcTemplate.executeQuery(sql, new DeptMapper(), null);
		return list;
	}

	public void addDept(Dept dept) throws SQLException {
		String sql = "insert into t_dept(t_dept_no,t_dept_name,t_dept_loc,t_dept_manager,t_create_time,t_img_url,t_img_real_name) " + "values(?,?,?,?,now(),?,?)";
		JdbcTemplate.executeUpdate(sql, dept.getDeptNo(), dept.getDeptName(), dept.getDeptLoc(), dept.getDeptManager(), dept.getImgUrl(), dept.getImgRealName());
	}

	public void deleteByNo(String deptNo) throws SQLException, OAException {
		String sql = "delete from t_dept where t_dept_no = ?";
		// delete from t_dept where t_dept_no = null
		int count = JdbcTemplate.executeUpdate(sql, deptNo);
		log.info("疯狂经过很久的狂热过count=" + count + ",deptNo:" + deptNo);
		if (count != 1) {
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.DEL_FAIL, "数据库删除失败，未删除到数据");
		}
	}

	public List<Dept> selectByDeptNo(String DeptNo) throws SQLException {
		String sql = "select t_dept_no,t_dept_name,t_dept_loc,t_dept_manager,t_create_time,t_img_url,t_img_real_name from t_dept where t_dept_no=?";
		List<Dept> list = JdbcTemplate.executeQuery(sql, new DeptMapper(), DeptNo);
		return list;
	}

	public Dept queryByDeptNo(String deptNo) throws SQLException, OAException {
		String sql = "select t_dept_no,t_dept_name,t_dept_loc,t_dept_manager,t_create_time, t_img_url, t_img_real_name from t_dept where t_dept_no=?";
		/*
		 * List<Dept> list=JdbcTemplate.executeQuery(sql, new
		 * DeptMapper(),deptNo); if(list != null && list.size() == 1) { return
		 * list.get(0); } return null;
		 */
		return (Dept) JdbcTemplate.executeOne(sql, new DeptMapper(), deptNo);
	}

	public void updateDept(Dept dept) throws SQLException, OAException {
		int count = 0;
		String sql = "update t_dept set t_dept_name=?, t_dept_loc=?, t_dept_manager=? ";

		if (!StringUtil.isEmpty(dept.getImgRealName())) {
			// 如果上传了新的图片，set t_img_url = ?,如果没有上传，则不用改
			sql += ", t_img_url=?, t_img_real_name=?";
			sql += "where t_dept_no=?";
			count = JdbcTemplate.executeUpdate(sql, dept.getDeptName(), dept.getDeptLoc(), dept.getDeptManager(), dept.getImgUrl(), dept.getImgRealName(), dept.getDeptNo());
		} else {
			// 没有上传图片
			sql += "where t_dept_no=?";
			count = JdbcTemplate.executeUpdate(sql, dept.getDeptName(), dept.getDeptLoc(), dept.getDeptManager(), dept.getDeptNo());

		}
		if (count != 1) {
			// TODO 日志打印，记录dept参数，count也要记录
			// 删除0或者是多条数据，都视为失败，要进行回滚
			throw new OAException(ErrorCode.UPDATE_FAIL, "数据库修改失败，未修改到数据，或者修改了多条数据");
		}
	}

	public List<Dept> queryAllByPage(int pageSize, int pageNo) throws SQLException {
		String sql = "select t_dept_no,t_dept_name,t_dept_loc,t_dept_manager,t_create_time ,t_img_url, t_img_real_name from t_dept order by t_dept_no limit ?,?";
		// 一般来说，分页之前，先排序，排序一般的规则，是order by t_create_time,t_dept_no desc（降序）
		// (asc:升序，不给，默认是升序)
		// 开始位置： startIndex = (pageNo-1)*pageSize
		List<Dept> list = JdbcTemplate.executeQuery(sql, new DeptMapper(),
				((pageNo - 1) * pageSize), pageSize);
		return list;
	}

	public int queryCount() throws SQLException, OAException {
		String sql = "select count(*) as cnt " + " from t_dept order by t_dept_no";
		// 使用匿名内部类
		int count = (Integer) JdbcTemplate.executeOne(sql, new RowMapper() {

			public Integer mapperObject(ResultSet rs) throws SQLException {
				int count = rs.getInt("cnt");
				return count;
			}
		}, null);

		return count;
	}

	public List<Dept> selectByDeptName(String deptName) throws SQLException {
		String sql = "select t_dept_no,t_dept_name,t_dept_loc,t_dept_manager,t_create_time,t_img_url, t_img_real_name " + "from t_dept " + "where t_dept_name=?";
		List<Dept> list = JdbcTemplate.executeQuery(sql, new DeptMapper(), deptName);
		return list;
	}

}
