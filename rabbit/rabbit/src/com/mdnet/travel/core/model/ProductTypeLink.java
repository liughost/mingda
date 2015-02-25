package com.mdnet.travel.core.model;

public class ProductTypeLink extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int typeId;// 城市id
	private int productId;// 产品ID
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

}
