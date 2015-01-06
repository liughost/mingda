package com.mdnet.travel.core.model;

public class GroupWeek {
	private GroupDay[] day = new GroupDay[7];

	public GroupDay[] getDay() {
		return day;
	}

	public void setDay(GroupDay[] day) {
		this.day = day;
	}
}
