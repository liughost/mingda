package com.mdnet.travel.core.model;

public class ProductCityLink extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int cityId;// 城市id
	private int productId;// 产品ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}
