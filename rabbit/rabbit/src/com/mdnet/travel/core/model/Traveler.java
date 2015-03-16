package com.mdnet.travel.core.model;

public class Traveler extends BaseModel{
	private static final long serialVersionUID = 7727L;
	public static final String ROLE_TRAVELER = "ROLE_TRAVELER";
	public static final String ROLE_MEM = "ROLE_MEM";
	private String travelerId;
	private String loginName;
	private String userName;
	private int classLevel;
	private String inviteCode;
	private int totalScore;
	private String passText;
	private String headImg;//用户头像的key，默认文件名为head.jpg
	private String tourKey;//对应的导游信息结构的ID

	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getTourKey() {
		return tourKey;
	}
	public void setTourKey(String tourKey) {
		this.tourKey = tourKey;
	}
	public String getPassText() {
		return passText;
	}
	public void setPassText(String passText) {
		this.passText = passText;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public int getClassLevel() {
		return classLevel;
	}
	public void setClassLevel(int classLevel) {
		this.classLevel = classLevel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	private String password;
	private String mobile;
	private String authority;
	private String wxFromUserName;
	private String wxOpenId;
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	public String getWxFromUserName() {
		return wxFromUserName;
	}
	public void setWxFromUserName(String wxFromUserName) {
		this.wxFromUserName = wxFromUserName;
	}
	public Traveler(){}
	public Traveler(String loginName, String password, String mobile) {
		this.loginName = loginName;
		this.password = password;
		this.mobile = mobile;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getTravelerId() {
		return travelerId;
	}
	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
