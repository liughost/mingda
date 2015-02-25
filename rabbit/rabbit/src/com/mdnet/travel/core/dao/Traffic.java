package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.Map;

public class Traffic {
	private String vehicle;// 交通工具
	private String detail;// 详细描述

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public static Traffic Unseriable(Map t) {

		Traffic traffic = new Traffic();
		Field[] fields = traffic.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			String value = t.get(vName).toString();
			if (field.getType() == String.class) {
				try {
					field.set(traffic, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else if (field.getType() == int.class) {
				try {
					field.setInt(traffic, Integer.parseInt(value));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return traffic;
	}

}
