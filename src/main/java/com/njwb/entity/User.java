package com.njwb.entity;

import java.util.Date;

public class User {

    private int id; //用户编号
    private String userName;//用户名
    private String pwd; //密码

    private String empNo;
    private Employ employ;
    private String userStatus;
    private Role role;

    public Role getRole() {
        return role;
    }



    public void setRole(Role role) {
        this.role = role;
    }









    public User(int id, String userName, String pwd, String empNo, Employ employ, String userStatus, Role role, int roleId, Date createTime) {
        super();
        this.id = id;
        this.userName = userName;
        this.pwd = pwd;
        this.empNo = empNo;
        this.employ = employ;
        this.userStatus = userStatus;
        this.role = role;
        this.roleId = roleId;
        this.createTime = createTime;
    }



    public User(String userName, String pwd, String empNo, Employ employ, String userStatus, Role role, int roleId, Date createTime) {
        super();
        this.userName = userName;
        this.pwd = pwd;
        this.empNo = empNo;
        this.employ = employ;
        this.userStatus = userStatus;
        this.role = role;
        this.roleId = roleId;
        this.createTime = createTime;
    }



    public String getUserStatus() {
        return userStatus;
    }



    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }



    public User() {
        // TODO Auto-generated constructor stub
    }


    public Employ getEmploy() {
        return employ;
    }
    public void setEmploy(Employ employ) {
        this.employ = employ;
    }
    /**
     * 角色ID
     */
    private int roleId;

    private Date createTime; //创建时间

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmpNo() {
        return empNo;
    }
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    @Override
    public String toString() {
        return "User2 [id=" + id + ", userName=" + userName + ", pwd=" + pwd
                + ", roleId=" + roleId + ", createTime=" + createTime + "employ="+employ+"role="+role+"]";
    }

}
