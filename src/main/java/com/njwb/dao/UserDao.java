package com.njwb.dao;


import java.sql.SQLException;
import java.util.List;

import com.njwb.entity.Menu;
import com.njwb.entity.User;
import com.njwb.exception.OAException;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    User queryOne(@Param("userName") String userName,@Param("pwd") String pwd);

    /**
     * 查一级菜单
     * @return
     */
    List<Menu> queryLevelOne(Integer roleID) throws SQLException;

    List<Menu> queryMenuByParentID(@Param("parentID") Integer parentID,@Param("roleID")Integer roleID) throws SQLException;

    Integer queryCount() throws SQLException, OAException;

    List<User> queryAllUsByPage(@Param("pageSize") Integer pageSize, @Param("startIndex") Integer startIndex) throws SQLException;

    void addUser(User user) throws SQLException;

    Integer deleteByNo(Integer userNo) throws SQLException;

    void updateUser(User user) throws OAException, SQLException;

    User queryByDeptNo(Integer userNo) throws SQLException, OAException;

    List<User> queryUserByRoleId(Integer roleID);
}
