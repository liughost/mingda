package com.mdnet.travel.core.model;

import java.util.Date;

public class InviteCode {
	private int id;// 指定使用identity主键生成策略
	//private int custId;// 关联登录信息表的traveler_id
	private String inviteCode;// 邀请码
	private int codeLevel;// 码级别，0：
	private int codeType;// 码类型，0：注册邀请码；1：优惠码
	private Date makeTime;// 码生成时间
	private String policy;
	private int score;
	private String dest_mobile;
	private int codeStatus;//'码的状态，0：未使用；1：已使用；2：已禁用；3：已失效
	private int offPrice;//优惠额度
	private Traveler member;
	private Date usedTime;
	
	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Traveler getMember() {
		return member;
	}

	public void setMember(Traveler member) {
		this.member = member;
	}

	public int getOffPrice() {
		return offPrice;
	}

	public void setOffPrice(int offPrice) {
		this.offPrice = offPrice;
	}

	public int getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(int codeStatus) {
		this.codeStatus = codeStatus;
	}

	public String getDest_mobile() {
		return dest_mobile;
	}

	public void setDest_mobile(String dest_mobile) {
		this.dest_mobile = dest_mobile;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}
	*/
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(int codeLevel) {
		this.codeLevel = codeLevel;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public Date getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(Date makeTime) {
		this.makeTime = makeTime;
	}

}
