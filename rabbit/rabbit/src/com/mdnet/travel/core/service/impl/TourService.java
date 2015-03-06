package com.mdnet.travel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ViewResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mdnet.travel.core.dao.LangCapability;
import com.mdnet.travel.core.dao.TourInfo;
import com.mdnet.travel.core.dao.TourPriceCanlendar;

@Service(TourService.SERVICE_NAME)
@Scope("singleton")
public class TourService extends BaseCouchService {
	public static final String SERVICE_NAME = "com.mdnet.travel.core.service.impl.TourService";

	public TourService() {
		if (db == null)
			db = new Database("guantravel.com", 5984, "tours");

	}

	public List<TourInfo> search(String[] cities, String[] scenics,
			String[] services, String[] language, boolean isAuto,
			boolean isPlan, String startKey, int count) {
		/*
		 * emit参数中的第一个参数是key，用作options的排序和startkey
		 * 默认情况下是doc._id此时按照_id的排序，startkey起到的作用是起始key
		 * 当是doc.carType等其他字段时，就是按照其他字段排序
		 * 当混合料doc.carType和doc._id时，startKey有值时只能取出一条记录，就是_id和startKey相等的记录
		 * 因此目前carType排序和startkey不能同时生效。
		 * 也就是说不能解决专车和导游混合排列的问题，目前的办法就是要么显示专车，要么显示导游
		 */
		String mapFunc = "{\"map\":\"function(doc) {" + "if(doc.mobile != null";
		if (isAuto) {
			mapFunc += "&& doc.carType!=null && doc.carType.length>0";
		}
		if (isPlan) {
			mapFunc += "&& doc.planning!=null && doc.planning==1";
		}
		mapFunc += "&&matchCity(doc)" + "&&matchScenic(doc)"
				+ "&&matchLang(doc)" + "&&matchService(doc))"
				+ "{emit(doc._id, doc);}" + "}";

		mapFunc += "function matchCity(doc){"
				+ "	var cities = ["
				+ this.arrayStr(cities)
				+ "];"
				+ "	for(var i=0; i<cities.length; i++)"
				+ "       	{"
				+ "           if(doc.serviceCities == null || doc.serviceCities.indexOf(cities[i])<0)"
				+ "           {           return false;           }"
				+ "       	}" + "	return true;" + "}";

		mapFunc += "function matchScenic(doc){"
				+ "	var scenics = ["
				+ this.arrayStr(scenics)
				+ "];"
				+ "	for(var i=0; i<scenics.length; i++)"
				+ "       	{"
				+ "           if(doc.scenic == null || doc.scenic.indexOf(scenics[i])<0)"
				+ "           {               return false;           }"
				+ "       	}" + "	return true;" + "}";

		mapFunc += "function matchLang(doc){" + "var lang = ["
				+ this.arrayStr(language) + "];"
				+ "for(var i=0; i<lang.length; i++){"
				+ "if(doc.language== null"
				+ "|| (doc.language.foreignLang.indexOf(lang[i])<0"
				+ "&&doc.language.motherLang.indexOf(lang[i])<0))"
				+ "return false;" + "}	return true;" + "}";

		mapFunc += "function matchService(doc){"
				+ "	var services = ["
				+ this.arrayStr(services)
				+ "];"
				+ "	var serviceItems = '';"
				+ "	if(doc.serviceItem == null) return false;"
				+ "	for(var j=0; j<doc.serviceItem.length; j++)"
				+ "	   {		serviceItems += doc.serviceItem[j]+',';	   }"
				+ "	for(var i=0; i<services.length; i++)"
				+ "       	{"
				+ "           if(doc.serviceItem == null || serviceItems.indexOf(services[i])<0)"
				+ "           {          return false;           }"
				+ "       	}" + "	return true;" + "}";

		mapFunc += "\"}";
		Options options = new Options();

		options.descending(true);
		options.limit(count + 1);
		if (startKey != null)
			options.startKey(startKey);
		@SuppressWarnings("rawtypes")
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, options,
				null);

		List<TourInfo> list = new ArrayList<TourInfo>();
		for (int i = 0; i < result.getRows().size(); i++) {
			TourInfo ti = new TourInfo();
			ti.Unseriable(result.getRows().get(i));
			// ti.seti
			list.add(ti);
		}
		return list;

	}

	@SuppressWarnings("rawtypes")
	public TourInfo takeTour(String id) {
		Map result = db.getDocument(Map.class, id);
		TourInfo ti = new TourInfo();
		// result.getProperty("price");

		return ti.Unseriable(result);
	}

	public TourInfo newTour() {
		return new TourInfo(db);
	}

	@SuppressWarnings("rawtypes")
	public TourInfo getByMobile(String mobile) {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.mobile != null && doc.mobile == "
				+ mobile + "){emit(doc._id, doc);}}\"}";
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, null,
				null);

		TourInfo tour = new TourInfo();
		return tour.Unseriable(result.getRows().get(0));
	}

	public static void main(String[] args) {

		TourService tour = new TourService();
		// t.test();
		// tour.dataStatic("static/city");
		// TourInfo pppp = tour.takeTour("618c84ab493269bc0ac54fa0c700cd4d");
		// 查询
		// String[] cities = null;// new String[1];
		// cities[0] = "洛杉矶";
		// cities[1] = "北京";
		// cities[2] = "广州";
		// String[] scenics = null;// new String[2];
		// scenics[0] = "拉斯维加斯";
		// scenics[1] = "环球影城";
		// String[] services = null;// new String[2];
		// services[0] = "体育项目";
		// services[1] = "人文历史";
		// String[] lang0 = new String[2];
		// lang0[0] = "英语";
		// lang0[1] = "汉语";
		//
		// List<TourInfo> ti = tour.search(cities, scenics, services, lang0,
		// true,
		// true, "618c84ab493269bc0ac54fa0c700d9ba", 10);
		// System.out.println(ti.size());

		// t.getByMobile("13911531721");

		TourInfo t = tour.newTour();
		t.setHeadImg("head.jpg");// 头像图片名称
		t.setIntroImg("intro.jpg");// 领队介绍的大图;
																										// 基本信息
		t.setNickName("李世涛");// 领队的昵称
		t.setGender(1);// 性别，0：保密，1：男性；2：女性
		t.setLocation("北京");// 所在地，常驻城市
		t.setServiceLevel(5);// 服务水平，星级，从0-5级
		t.setServiceCities("拉斯维加斯、洛杉矶、旧金山、弗雷斯诺、佩吉、夏威夷、奥兰多、盐湖城、蒙特利、迈阿密、芝加哥");// 服务的城市
		t.setScenic("17英里海岸、66号公路、一号公路、七哩桥、大峡谷、大提顿国家公园、大沼泽国家公园、奥特莱斯、好莱坞、黄石公园、布莱斯、拉斯维加斯、拱门国家公园");
		t.setIntroduce("李世涛 86年出生 从事涉外旅游8年 纯正美式英语交流无障碍,热衷于美国西部文化.美国自驾7万公里以上,对加州,亚利桑那州,犹他州及西部国家公园等线路非常熟悉.本人性格豪爽,喜欢旅行,喜欢交友。美国,不见不散!");// 个人简介
		LangCapability lang = new LangCapability();
		lang.setForeignLang("英语");
		lang.setMotherLang("汉语");
		t.setLanguage(lang);// 语言能力 t.setCareer("学生");
		t.setCarType("全尺寸越野车");
		t.setPlanning(1);
		t.setCareer("专业导游");

		// 联系方式
		t.setWeixinId("lishitao4945");// 微信号
		t.setQQ("470071864");// QQ号
		t.setEMail("470071864@qq.com");// 邮箱
		t.setMobile("18614089389");// 移动电话,手机号码作为唯一的标识
		t.setWeixinQR("QR.jpg");// 二维码

		// 服务说明
		t.setFeeDesc("每天服务时长:10个小时\n超时服务费： 面议（1小时）\n接待人数：最多15人");// 费用包含
		t.setFeeExcept("服务小费：无\n餐补费：无\n异地住宿补贴：无");// 费用不包含
		t.setSupplement("1.根据客人的要求，专门为您设计只属于您的行程\n2.可以按照您的要求，预定特色酒店\n3.免费提供无线上网mifi,您可以在任何地方发微信，看微博。\n4.接机送机当天有安排旅游行程的，只需支付当天旅游费用600元，接机时间算在导游时间里。（导游机场往返车费客人支付）只接机送机没有安排旅游行程需支付300元。\n5.带您到汇率最高的货币兑换点换取外汇，给您提供各种VIP卡，让您的旅游更经济实惠");// 其他说明
		List<String> items = new ArrayList<String>();
		items.add("接机");
		items.add("送机");
		items.add("驾车");
		items.add("熟悉人文历史");
		items.add("熟悉体育项目");
		items.add("熟悉音乐");
		items.add("熟悉自然科学");
		items.add("熟悉红灯区");
		items.add("熟悉美术");
		items.add("熟悉建筑");
		items.add("熟悉当地美食");
		items.add("代定酒店");
		items.add("代定机票");

		t.setServiceItem(items); // 报价
		t.setUnitPrice(150);// 基础报价
		List<TourPriceCanlendar> price = new ArrayList<TourPriceCanlendar>();
		TourPriceCanlendar p = new TourPriceCanlendar();
		p.setAdultPrice(150);
		p.setBookCount(0);
		p.setChildPrice(120);
		p.setStartDate("2015-03-05");
		p.setEndDate("2015-12-31");
		p.setMakeTime("2015-03-04");
		p.setTotalCount(14);
		price.add(p);

		t.setPrice(price);

		t.Add();

		System.out.println("game over!");
	}
}
