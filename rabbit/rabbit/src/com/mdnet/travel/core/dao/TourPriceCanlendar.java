package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.Map;

import org.jcouchdb.document.ValueRow;

public class TourPriceCanlendar {
	private String startDate;// 价格开始日期
	private String endDate;// 价格截止日期
	private int bookCount;// 预订参团人数
	private int totalCount;// 预订参团总数
	private String makeTime;// 创建日期',
	private int adultPrice;// 成人报价
	private int childPrice;// 儿童报价

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

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
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

	public static TourPriceCanlendar Unseriable(
			@SuppressWarnings("rawtypes") Map result) {
		TourPriceCanlendar p = new TourPriceCanlendar();

		Field[] fields = p.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			if (result.get(vName) == null)
				continue;
			String value = result.get(vName).toString();
			if (field.getType() == String.class) {
				try {
					field.set(p, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (field.getType() == int.class) {
				try {
					field.setInt(p, Integer.parseInt(value));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end try
			}//end if
		}//end fir
		return p;
	}
}
