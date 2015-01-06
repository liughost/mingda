package com.mdnet.travel.core.model;

import java.util.ArrayList;
import java.util.List;

public class GroupMonth {
//	private List<GroupWeek> week = new ArrayList<GroupWeek>();
//
//	public List<GroupWeek> getWeek() {
//		return week;
//	}
//
//	public void setWeek(List<GroupWeek> week) {
//		this.week = week;
//	}
	
	private List<GroupDay> days = new ArrayList<GroupDay>();
	private int year;
	private int month;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<GroupDay> getDays() {
		return days;
	}

	public void setDays(List<GroupDay> days) {
		this.days = days;
	}

}
