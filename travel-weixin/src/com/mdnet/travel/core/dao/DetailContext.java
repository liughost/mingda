package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.Map;

public class DetailContext {
	private String type;
	private String text;

	public DetailContext(String _type, String _text) {
		this.text = _text;
		this.type = _type;
	}

	public DetailContext() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static DetailContext Unseriable(@SuppressWarnings("rawtypes") Map m) {
		DetailContext d = new DetailContext();
		Field[] fields = d.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			String value = m.get(vName).toString();
			if (field.getType() == String.class) {
				try {
					field.set(d, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return d;
	}
}
