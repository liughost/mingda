package com.mdnet.travel.core.model;

public class ProductTourLink extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int tourId;// 导游id
	public int getTourId() {
		return tourId;
	}
	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
	private int productId;// 产品ID
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

}
