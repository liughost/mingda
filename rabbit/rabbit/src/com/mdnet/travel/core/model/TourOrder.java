package com.mdnet.travel.core.model;

public class TourOrder extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000L;
	private int id;
	private int custId;// 关联到客户表（customer_table）中的id字段
	private String startDate;// 预订开始的日期
	private String endDate;// 预订截止日期
	private String linkPhone;// 客人联系电话
	private String linkName;// 客人姓名
	private String memo;// 备注信息

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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
