package com.mdnet.travel.core.dao;

public class KeyValue implements Comparable<KeyValue>{
	protected String key;
	protected String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int compareTo(KeyValue arg0) {
		return arg0.getValue().compareTo(this.getValue());
	}
}
