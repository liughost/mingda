package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.Map;

import org.jcouchdb.document.ValueRow;

public class LangCapability {
	private String motherLang;
	private String foreignLang;

	public String getMotherLang() {
		return motherLang;
	}

	public void setMotherLang(String motherLang) {
		this.motherLang = motherLang;
	}

	public String getForeignLang() {
		return foreignLang;
	}

	public void setForeignLang(String foreignLang) {
		this.foreignLang = foreignLang;
	}

	public static LangCapability Unseriable(
			@SuppressWarnings("rawtypes") Map result) {
		LangCapability lang = new LangCapability();

		Field[] fields = lang.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			if (result.get(vName) == null)
				continue;
			String value = result.get(vName).toString();
			if (field.getType() == String.class) {
				try {
					field.set(lang, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end try
			}//end if

		}//end for
		return lang;
	}
}
