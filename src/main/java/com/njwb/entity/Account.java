package com.njwb.entity;

import java.util.Date;

public class Account {
	private int accountNo;
	private String accountUser;
	private String accountType;
	private String accountYao;
	private int accountmoney;
	private Date accountTime;
	
	private String accountStatus;
	
	
	
	public Account() {
		// TODO Auto-generated constructor stub
	}
	

	

	public Account(int accountNo, String accountUser, String accountType,
			String accountYao, int accountmoney, Date accountTime,
			String accountStatus) {
		super();
		this.accountNo = accountNo;
		this.accountUser = accountUser;
		this.accountType = accountType;
		this.accountYao = accountYao;
		this.accountmoney = accountmoney;
		this.accountTime = accountTime;
		this.accountStatus = accountStatus;
	}

	


	public Account(String accountUser, String accountType, String accountYao,
			int accountmoney, Date accountTime, String accountStatus) {
		super();
		this.accountUser = accountUser;
		this.accountType = accountType;
		this.accountYao = accountYao;
		this.accountmoney = accountmoney;
		this.accountTime = accountTime;
		this.accountStatus = accountStatus;
	}




	public int getAccountNo() {
		return accountNo;
	}




	public String getAccountYao() {
		return accountYao;
	}




	public void setAccountYao(String accountYao) {
		this.accountYao = accountYao;
	}




	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}




	public String getAccountUser() {
		return accountUser;
	}




	public void setAccountUser(String accountUser) {
		this.accountUser = accountUser;
	}




	public String getAccountType() {
		return accountType;
	}




	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}




	public int getAccountmoney() {
		return accountmoney;
	}




	public void setAccountmoney(int accountmoney) {
		this.accountmoney = accountmoney;
	}




	public Date getAccountTime() {
		return accountTime;
	}




	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}




	public String getAccountStatus() {
		return accountStatus;
	}




	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}




	@Override
	public String toString() {
		return "Account [AccountNo=" +accountNo
		+ ", AccountUser=" + accountUser + ", AccountType=" +accountType
		+ ", accountmoney=" + accountmoney+ ",accountTime=" + accountTime + ", accountStatus=" + accountStatus +  "]";
	}
	
}
