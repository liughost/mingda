package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Journey {
	private String day;
	private String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private List<Traffic> traffic = new ArrayList<Traffic>();// 交通信息

	public List<Traffic> getTraffic() {
		return traffic;
	}

	public void setTraffic(List<Traffic> traffic) {
		this.traffic = traffic;
	}

	private String hotel;// 住宿

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	private Meal meal;// 餐饮
	private List<DetailContext> detail = new ArrayList<DetailContext>();

	public List<DetailContext> getDetail() {
		return detail;
	}

	public void setDetail(List<DetailContext> detail) {
		this.detail = detail;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public static Journey Unseriable(@SuppressWarnings("rawtypes") Map result) {
		Journey j = new Journey();
		Field[] fields = j.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			String value = result.get(vName) == null ? "" : result.get(vName)
					.toString();
			if (field.getType() == String.class) {
				try {
					field.set(j, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (field.getType() == int.class) {
				try {
					field.setInt(j, Integer.parseInt(value));
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
			} else if (field.getType() == Meal.class) {
				Meal meal = Meal.Unseriable((Map) result.get(vName));
				j.setMeal(meal);

			} else if (field.getType() == List.class
					&& vName.compareTo("traffic") == 0) {
				List<Map> ts = (List<Map>) result.get(vName);
				for (Map t : ts) {
					Traffic to = Traffic.Unseriable(t);
					j.traffic.add(to);
				}
			} else if (field.getType() == List.class
					&& vName.compareTo("detail") == 0) {
				List<Map> ts = (List<Map>) result.get(vName);
				for (Map t : ts) {
					DetailContext d = DetailContext.Unseriable(t);
					j.detail.add(d);
				}
			}

		}
		return j;
	}
}
