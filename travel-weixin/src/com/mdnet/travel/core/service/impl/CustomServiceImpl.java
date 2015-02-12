package com.mdnet.travel.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mdnet.travel.core.dao.GroupDateDAO;
import com.mdnet.travel.core.dao.PersonalCustomDAO;
import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.dao.WeixinAccountDAO;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.model.GroupDay;
import com.mdnet.travel.core.model.GroupMonth;
import com.mdnet.travel.core.model.PersonalCustom;
import com.mdnet.travel.core.service.ICustomService;
import com.mdnet.travel.core.service.IMessageService;
import com.mdnet.travel.core.vo.ShowProductInfo;

@Service(ICustomService.SERVICE_NAME)
@Scope("prototype")
public class CustomServiceImpl implements ICustomService {

	@Resource(name = PersonalCustomDAO.DAO_NAME)
	protected PersonalCustomDAO personalDAO;
	@Resource(name = GroupDateDAO.DAO_NAME)
	protected GroupDateDAO groupDateDAO;

	@Override
	public void savePrivate_Dest(String sid, String[] national,
			String[] cities, String[] goal) {
		PersonalCustom pc = this.getPersonal(sid);
		boolean isUpdate = true;
		if (pc == null) {
			isUpdate = false;
			pc = new PersonalCustom();
			pc.setState(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pc.setMakeTime(sdf.format(new Date()));

		}
		pc.setSessionID(sid);
		String cityNames = array2str(cities);
		pc.setCities(cityNames);
		pc.setGoal(array2str(goal));
		String strNational = array2str(national);
		pc.setNational(strNational);
		if (!isUpdate)
			personalDAO.save(pc);
		else
			personalDAO.update(pc);
	}

	public String array2str(String[] array) {
		String result = "";
		for (int i = 0; array != null && i < array.length; i++) {
			result += array[i] + ",";
		}
		return result;
	}

	@Override
	public void updatePersonal(PersonalCustom personal) {
		personalDAO.update(personal);
	}

	@Override
	public PersonalCustom getPersonal(String sid) {
		return personalDAO.getPersonalBySession(sid);
	}

	@Override
	public List<PersonalCustom> findall(int page, int count, int sType,
			String context) {
		return this.personalDAO.findAll(page, count, sType, context);
	}

	@Override
	public List<GroupDate> getGroupList(String where, int aheadDays, int page) {

		if (where != null && where.length() > 0) {
			where += " and ";
		} else
			where += " where";
		// 生成团期最小日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		long d = now.getTime() / 1000 + aheadDays * 24 * 60 * 60;
		Date minDay = new Date(d * 1000);

		String minDate = sdf.format(minDay);

		where += " startDate >'" + minDate + "'";

		return this.groupDateDAO.getList(where, page);
		// else
		// return this.groupDateDAO.getList("where productId=" + pid, page);
	}

	@Override
	public List<PersonalCustom> getPersonalByMobile(String mobile, int page) {
		return personalDAO.findAll(page, 20, 0, mobile);
	}

	@Override
	public List<PersonalCustom> getPersonals(String sid, int page) {
		return this.personalDAO.findAll(page, 20, 2, sid);
	}

	@Override
	public int AddGroup(String groupName, String productName, String startDate,
			int productId, int bookCount, int totalCount) {
		GroupDate entity = new GroupDate();
		entity.setBookCount(bookCount);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String makeTime = sdf.format(new Date());
		entity.setMakeTime(makeTime);
		entity.setProductId(productId);
		entity.setProductName(groupName);
		entity.setStartDate(startDate);
		entity.setTotalCount(totalCount);
		return this.groupDateDAO.save(entity);
	}

	@Override
	public int UpdateGroup(int groupId, String groupName, String productName,
			String startDate, int productId, int bookCount, int totalCount) {
		GroupDate entity = this.groupDateDAO.get(groupId);
		entity.setBookCount(bookCount);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String makeTime = sdf.format(new Date());
		entity.setMakeTime(makeTime);
		entity.setProductId(productId);
		entity.setProductName(groupName);
		entity.setStartDate(startDate);
		entity.setTotalCount(totalCount);
		this.groupDateDAO.update(entity);
		return 0;

	}

	@Override
	public int deleteGroup(int groupId) {
		GroupDate entity = this.groupDateDAO.get(groupId);
		if (entity != null)
			this.groupDateDAO.delete(entity);
		return groupId;
	}

	@Override
	public String markGroupFull(int groupId) {
		GroupDate entity = this.groupDateDAO.get(groupId);
		entity.setBookCount(entity.getTotalCount());
		this.groupDateDAO.update(entity);
		Gson g = new Gson();
		return g.toJson(entity);
	}


	protected int monthDays(int year, int month) {
		int localMaxDay = 31;
		if (month == 2) {
			if (year % 100 != 0 && year % 4 == 0) {
				localMaxDay = 29;// 闰年
			} else
				localMaxDay = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			localMaxDay = 30;
		}

		return localMaxDay;
	}

	@Override
	public GroupMonth getGroupCanlendar(List<GroupDate> gs, String id,
			Calendar c) {

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

		// 计算日历初始日期
		// 计算上个月总天数
		int lastMaxDay = 31;// 上个月最大日
		lastMaxDay = this.lastMonthDays(year, month);
		// 计算本月总天数
		int localMaxDay = 31;// 本月最大日
		localMaxDay = this.monthDays(year, month);
		// 计算起始日和起始段，有从1号开始的月份
		int day = 1;// 上个月的起始日
		int seg = 0;// 0,上个月；1本月；2小个月,有时候从1开始
		if (dayOfWeek == 0) {
			seg = 1;// 从本月开始
		} else {
			day = lastMaxDay - dayOfWeek + 1;// 上个月的起始日

		}
		// 计算日历总天数，上个月的天数+本月天数
		int totalDays = ((dayOfWeek + localMaxDay) / 7 + 1) * 7;

		GroupMonth m = new GroupMonth();
		m.setYear(year);
		m.setMonth(month);
		// 月循环
		for (int i = 0; i < totalDays; i++) {
			GroupDay d = new GroupDay();

			// d.setPrice(this.getGroupPrice(gs, seg, year, month, day));
			this.setGroupPrice(gs, seg, year, month, day, d);

			d.setDay(day++);
			d.setLocalMon(seg == 1 ? true : false);
			// 计算下一天
			if (day > lastMaxDay && seg == 0) {
				day = 1;
				seg = 1;
			}
			if (day > localMaxDay && seg == 1) {
				day = 1;
				seg = 2;
			}
			m.getDays().add(d);
		}
		return m;
	}

	private int setGroupPrice(List<GroupDate> gs, int seg, int year, int month,
			int day, GroupDay d) {
		if (seg == 0)// 上个月
		{
			if (month == 1) {
				year--;
				month = 12;
			} else
				month--;
		} else if (seg == 2)// 下个月
		{
			if (month == 12) {
				year++;
				month = 1;
			} else
				month++;
		}
		for (GroupDate g : gs) {
			String curDate = String.format("%04d-%02d-%02d", year, month, day);
			if (g.getStartDate().substring(0, 10).compareTo(curDate) == 0) {
				d.setPrice(g.getAdultPrice());
				d.setChildrenPrice(g.getChildPrice());
				d.setHotelSpanPrice(g.getHotelSpanPrice());
				d.setDate(g.getStartDate());
				return 0;
			}
		}
		return -1;
	}

	private int getGroupPrice(List<GroupDate> gs, int seg, int year, int month,
			int day) {
		if (seg == 0)// 上个月
		{
			if (month == 1) {
				year--;
				month = 12;
			} else
				month--;
		} else if (seg == 2)// 下个月
		{
			if (month == 12) {
				year++;
				month = 1;
			} else
				month++;
		}
		for (GroupDate g : gs) {
			String curDate = String.format("%04d-%02d-%02d", year, month, day);
			if (g.getStartDate().substring(0, 10).compareTo(curDate) == 0) {
				return g.getAdultPrice();
			}
		}
		return 0;
	}

	private int lastMonthDays(int year, int month) {
		if (month == 1)// 上一年
		{
			month = 12;
			year--;
		} else {
			month--;
		}

		return this.monthDays(year, month);
	}

	@Override
	public List<GroupMonth> makeGroupCalendar(String id, int aheadDays) {
		List<GroupMonth> gms = new ArrayList<GroupMonth>();
		List<GroupDate> gs = this.getGroupList("where productId=" + id,
				aheadDays, 0);
		int lastYear = 0;
		int lastMonth = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (GroupDate g0 : gs) {
			Calendar c = Calendar.getInstance();
			try {
				Date d = sdf.parse(g0.getStartDate().substring(0, 7) + "-01");
				c.setTime(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (lastYear != c.get(Calendar.YEAR)
					|| lastMonth != c.get(Calendar.MONTH) + 1) {
				lastYear = c.get(Calendar.YEAR);
				lastMonth = c.get(Calendar.MONTH) + 1;

				GroupMonth gm = this.getGroupCanlendar(gs, id, c);
				gms.add(gm);
			}
		}
		return gms;
	}

	

}
