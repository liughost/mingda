package com.mdnet.travel.core.model;

public class GroupDay {
	private int day;
	private int price;
	private boolean isLocalMon;
	private int childrenPrice;
	private int hotelSpanPrice;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getChildrenPrice() {
		return childrenPrice;
	}

	public void setChildrenPrice(int childrenPrice) {
		this.childrenPrice = childrenPrice;
	}

	public int getHotelSpanPrice() {
		return hotelSpanPrice;
	}

	public void setHotelSpanPrice(int hotelSpanPrice) {
		this.hotelSpanPrice = hotelSpanPrice;
	}

	public boolean isLocalMon() {
		return isLocalMon;
	}

	public void setLocalMon(boolean isLocalMon) {
		this.isLocalMon = isLocalMon;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
