package com.mdnet.travel.core.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.dao.TourPriceCanlendar;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.model.GroupMonth;
import com.mdnet.travel.core.model.PersonalCustom;
import com.mdnet.travel.core.vo.ShowProductInfo;

public interface ICustomService {
	public final static String SERVICE_NAME = "com.mdnet.travel.core.service.impl.CustomServiceImpl";
 
	void savePrivate_Dest(String sid, String[] strings, String[] cities, String[] goal);

	PersonalCustom getPersonal(String sid);
	List<PersonalCustom> getPersonals(String sid, int page);

	void updatePersonal(PersonalCustom personal);
	
	List<PersonalCustom> findall(int page, int count, int sType, String context);

	List<GroupDate> getGroupList(String where, int aheadDays, int page, int count);

	List<PersonalCustom> getPersonalByMobile(String mobile, int page);

	int AddGroup(String groupName, String productName, String startDate, int productId, int bookCount,
			int totalCount);

	int UpdateGroup(int groupId, String groupName, String productName,
			String startDate, int productId, int bookCount, int totalCount);

	int deleteGroup(int groupId);

	String markGroupFull(int groupId);

	//ProductAllDetail getProductInfo(String id);

	GroupMonth getGroupCanlendar(List<GroupDate> gs, String id, Calendar c);

	List<GroupMonth> makeGroupCalendar(String id, int aheadDays);

	List<GroupMonth> makeGroupCalendar(String tid, String tourName, List<TourPriceCanlendar> price) throws ParseException;

	//ShowProductInfo[] getProductList(int type, int page);
}
