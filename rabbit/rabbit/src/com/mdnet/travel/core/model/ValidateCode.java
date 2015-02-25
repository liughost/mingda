package com.mdnet.travel.core.model;

import java.util.Date;

public class ValidateCode extends BaseModel{
	private static final long serialVersionUID = 33923L;

	private Integer vcId;
	private Date createTime;
	private String mobile;
	private String validCode;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ValidateCode(Date createTime, String mobile,
			String validCode) {
		
		this.createTime = createTime;
		this.mobile = mobile;
		this.validCode = validCode;
		this.status = 0;
	}
	public ValidateCode(){}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getVcId() {
		return vcId;
	}
	public void setVcId(Integer vcId) {
		this.vcId = vcId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
}
