package com.njwb.dao;


        import java.sql.SQLException;
        import java.util.List;

        import com.njwb.entity.Menu;
        import com.njwb.entity.User;
        import com.njwb.exception.OAException;

public interface UserDao {

    public abstract User queryOne(String userName,String pwd);

    /**
     * 查一级菜单
     * @return
     */
    public List<Menu> queryLevelOne(int roleID) throws SQLException;

    public List<Menu> queryMenuByParentID(int parentID,int roleID) throws SQLException;

    public abstract int queryCount() throws SQLException, OAException;

    public abstract List<User> queryAllUsByPage(int pageSize, int pageNo) throws SQLException;

    public abstract void addUser(User user) throws SQLException;

    public abstract int deleteByNo(String userNo) throws SQLException;

    public abstract void updateUser(User user) throws OAException, SQLException;

    public abstract User queryByDeptNo(String userNo) throws SQLException, OAException;

    public abstract List<User> queryUserByRoleId(int roleID);
}
