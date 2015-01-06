package com.mdnet.travel.core.model;

import java.util.Date;

public class DriverCard {
	private int id;// 指定使用identity主键生成策略
	private int custId;// 关联登录信息表的traveler_id
	private String userName;// 用户姓名
	private String identityCard;// 身份证
	private int sexy;// 性别,0:男；1：女
	private String country;// 国籍
	private String address;// 地址
	private Date birthday;// 出生日期
	private Date firstDate;// 初次领证日期
	private String coveredVehicles;// 准驾车型
	private Date validateDate;// 有效期
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public int getSexy() {
		return sexy;
	}
	public void setSexy(int sexy) {
		this.sexy = sexy;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	public String getCoveredVehicles() {
		return coveredVehicles;
	}
	public void setCoveredVehicles(String coveredVehicles) {
		this.coveredVehicles = coveredVehicles;
	}
	public Date getValidateDate() {
		return validateDate;
	}
	public void setValidateDate(Date validateDate) {
		this.validateDate = validateDate;
	}

}
