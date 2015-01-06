package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.Map;

public class Meal {
	private String breakfirst;
	private String lanch;
	private String supper;

	public Meal() {

	}

	public Meal(String _breakfirst, String _lanch, String _supper) {
		this.breakfirst = _breakfirst;
		this.lanch = _lanch;
		this.supper = _supper;
	}

	public String getBreakfirst() {
		return breakfirst;
	}

	public void setBreakfirst(String breakfirst) {
		this.breakfirst = breakfirst;
	}

	public String getLanch() {
		return lanch;
	}

	public void setLanch(String lanch) {
		this.lanch = lanch;
	}

	public String getSupper() {
		return supper;
	}

	public void setSupper(String supper) {
		this.supper = supper;
	}

	public static Meal Unseriable(@SuppressWarnings("rawtypes") Map m) {
		Meal meal = new Meal();
		Field[] fields = meal.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			String value = m.get(vName).toString();
			if (field.getType() == String.class) {
				try {
					field.set(meal, value);
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
					field.setInt(meal, Integer.parseInt(value));
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
		return meal;
	}
}
