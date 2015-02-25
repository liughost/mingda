package com.mdnet.travel.core.model;

public class GroupDate {
	private int id;
	private int productId;//产品编码
	private String startDate;//出发日期
	private String productName;//产品名称
	private int bookCount;//参团人数
	private int TotalCount;//参团总数
	private String makeTime;//创建日期
	private String groupDate;//参团日期
	private int adultPrice;//成人报价
	private int childPrice;//儿童报价
	private int hotelSpanPrice;//单房差价
	public int getHotelSpanPrice() {
		return hotelSpanPrice;
	}
	public void setHotelSpanPrice(int hotelSpanPrice) {
		this.hotelSpanPrice = hotelSpanPrice;
	}
	public int getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(int adultPrice) {
		this.adultPrice = adultPrice;
	}
	public int getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(int childPrice) {
		this.childPrice = childPrice;
	}
	public String getGroupDate() {
		return groupDate;
	}
	public void setGroupDate(String groupDate) {
		this.groupDate = groupDate;
	}
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getBookCount() {
		return bookCount;
	}
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	public int getTotalCount() {
		return TotalCount;
	}
	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}
	public String getMakeTime() {
		return makeTime;
	}
	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}
	}
