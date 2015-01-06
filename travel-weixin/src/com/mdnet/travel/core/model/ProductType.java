package com.mdnet.travel.core.model;

public class ProductType extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1376438149397801054L;
	private int id;
	private String catalogName;// 旅游目的地类型名称
	private String makeTime;// 创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}

}
