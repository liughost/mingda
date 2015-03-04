package com.mdnet.travel.core.vo;

import java.util.List;

import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.ProductType;
import com.mdnet.travel.core.model.Tour;

public class ShowProductInfo {
	private int productID;
	private String img;
	private String name;
	private int MediaID;
	private int bookedCount;
	private int fullCount;
	private int lowPrice;
	private List<CityDef> cities;
	private String cities_str;
	private String lineType;
	private String couchId;
	
	public String getCouchId() {
		return couchId;
	}
	public void setCouchId(String couchId) {
		this.couchId = couchId;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getCities_str() {
		return cities_str;
	}
	public void setCities_str(String cities_str) {
		this.cities_str = cities_str;
	}
	private String introduce;
	private List<Tour> tour;
	private List<ProductType> productType;
	
	public List<ProductType> getProductType() {
		return productType;
	}
	public void setProductType(List<ProductType> productType) {
		this.productType = productType;
	}
	public List<Tour> getTour() {
		return tour;
	}
	public void setTour(List<Tour> tour) {
		this.tour = tour;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getGroupDate() {
		return groupDate;
	}
	public void setGroupDate(String groupDate) {
		this.groupDate = groupDate;
	}
	private String days;
	private String groupDate;
	
	public List<CityDef> getCities() {
		return cities;
	}
	public void setCities(List<CityDef> cities) {
		this.cities = cities;
	}
	public int getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}
	public int getBookedCount() {
		return bookedCount;
	}
	public void setBookedCount(int bookedCount) {
		this.bookedCount = bookedCount;
	}
	public int getFullCount() {
		return fullCount;
	}
	public void setFullCount(int fullCount) {
		this.fullCount = fullCount;
	}
	public int getMediaID() {
		return MediaID;
	}
	public void setMediaID(int mediaID) {
		MediaID = mediaID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
