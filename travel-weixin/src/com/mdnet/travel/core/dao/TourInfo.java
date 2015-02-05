package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.document.BaseDocument;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

public class TourInfo extends BaseDocument {
	protected static TourInfo _tourInfo = null;
	protected Database db;
	// 图片
	private String headImg;// 头像图片名称
	private String introImg;// 领队介绍的大图;
	// 基本信息
	private String nickName;// 领队的昵称
	private int gender;// 性别，0：保密，1：男性；2：女性
	private String location;// 所在地，常驻城市
	private int serviceLevel;// 服务水平，星级，从0-5级
	private String serviceCities;// 服务的城市
	private String scenic;// 服务景点
	private int planning;//规划师，0不是；1是
	private String carType;//车型描述，空为不带车，例如，宝马SUV7座

	

	private String introduce;// 个人简介
	private LangCapability language = new LangCapability();// 语言能力
	private String career;// 职业

	// 报价
	private int unitPrice;// 基础报价
	private List<TourPriceCanlendar> price = new ArrayList<TourPriceCanlendar>();

	// 联系方式
	private String weixinId;// 微信号
	private String QQ;// QQ号
	private String EMail;// 邮箱
	private String mobile;// 移动电话,手机号码作为唯一的标识
	private String weixinQR;// 二维码

	// 服务说明
	private String feeDesc;// 费用包含
	private String feeExcept;// 费用不包含
	private String supplement;// 其他说明

	private List<String> serviceItem = new ArrayList<String>();

	public int getPlanning() {
		return planning;
	}

	public void setPlanning(int planning) {
		this.planning = planning;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}

	public List<String> getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(List<String> serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getIntroImg() {
		return introImg;
	}

	public void setIntroImg(String introImg) {
		this.introImg = introImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(int serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getServiceCities() {
		return serviceCities;
	}

	public void setServiceCities(String serviceCities) {
		this.serviceCities = serviceCities;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public LangCapability getLanguage() {
		return language;
	}

	public void setLanguage(LangCapability language) {
		this.language = language;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public List<TourPriceCanlendar> getPrice() {
		return price;
	}

	public void setPrice(List<TourPriceCanlendar> price) {
		this.price = price;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getEMail() {
		return EMail;
	}

	public void setEMail(String eMail) {
		EMail = eMail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWeixinQR() {
		return weixinQR;
	}

	public void setWeixinQR(String weixinQR) {
		this.weixinQR = weixinQR;
	}

	public String getFeeDesc() {
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}

	public String getFeeExcept() {
		return feeExcept;
	}

	public void setFeeExcept(String feeExcept) {
		this.feeExcept = feeExcept;
	}

	public String getSupplement() {
		return supplement;
	}

	public void setSupplement(String supplement) {
		this.supplement = supplement;
	}

	protected TourInfo() {
		db = new Database("guantravel.com", 5984, "tours");
		// AuthScope authScope = new AuthScope("guantravel.com", 5984);
		// Credentials credentials = new UsernamePasswordCredentials("mingda",
		// "mingdaNET");
		// db.getServer().setCredentials(authScope, credentials);
		// className = this.getClass().getName();
	}

	public static TourInfo instance() {
		if (TourInfo._tourInfo == null)
			TourInfo._tourInfo = new TourInfo();
		return TourInfo._tourInfo;
	}

	@SuppressWarnings("unchecked")
	public TourInfo Unseriable(ValueRow<Map> result) {
		this.price.clear();
		this.serviceItem.clear();

		Field[] fields = this.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			if (result.getValue().get(vName) == null)
				continue;
			String value = result.getValue().get(vName).toString();
			if (field.getType() == String.class) {
				try {
					field.set(this, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (field.getType() == int.class) {
				try {
					field.setInt(this, Integer.parseInt(value));
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
			} else if (field.getType() == LangCapability.class) {
				Map lang = (Map) result.getValue().get(vName);
				this.setLanguage(LangCapability.Unseriable(lang));

			} else if (field.getType() == List.class
					&& vName.compareTo("price") == 0) {
				List<Map> ps = (List<Map>) result.getValue().get(vName);
				for (Map j : ps) {
					TourPriceCanlendar p = TourPriceCanlendar.Unseriable(j);
					this.price.add(p);
				}
			} else if (field.getType() == List.class
					&& vName.compareTo("serviceItem") == 0) {
				// String img = (String) ;
				this.serviceItem = (List<String>) result.getValue().get(vName);

			}

		}
		return this;
	}

	public List<TourInfo> search(String[] cities, String[] scenics,
			String[] services) {
		String mapFunc = "{\"map\":\"function(doc) {" + "if(doc.mobile != null"
				+ "&&matchCity(doc)" + "&&matchScenic(doc)"
				+ "&&matchService(doc))" + "{emit(doc._id, doc);}" + "}";

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
		@SuppressWarnings("rawtypes")
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, null,
				null);

		List<TourInfo> list = new ArrayList<TourInfo>();
		for (int i = 0; i < result.getRows().size(); i++) {
			list.add(this.Unseriable(result.getRows().get(0)));
		}
		return list;

	}

	private String arrayStr(String[] cities) {
		String str = "";
		for (int i = 0; cities != null && i < cities.length; i++) {
			str += "'" + cities[i] + "'";
			if (i != cities.length - 1)
				str += ",";
		}
		return str;
	}

	public int Add() {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.mobile != null && doc.mobile == "
				+ this.mobile + "){emit(doc._rev, null);}}\"}";
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, null,
				null);
		// 将值实例化到对象
		if (result.getRows().size() == 0) {
			db.createDocument(this);
		} else {
			String id = result.getRows().get(0).getId();
			this.setId(id);
			String rev = result.getRows().get(0).getKey().toString();
			this.setRevision(rev);
			db.updateDocument(this);
		}
		return 0;
	}

	public TourInfo getByMobile(String mobile) {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.mobile != null && doc.mobile == "
				+ mobile + "){emit(doc._id, doc);}}\"}";
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, null,
				null);

		return this.Unseriable(result.getRows().get(0));
	}

	public static void main(String[] args) {

		TourInfo t = TourInfo.instance();
		//查询
		String[] cities = new String[1];
		cities[0] = "洛杉矶";
		//cities[1] = "北京";
		//cities[2] = "广州";
		String[] scenics = null;//new String[2];
//		scenics[0] = "拉斯维加斯";
//		scenics[1] = "环球影城";
		String[] services = null;//new String[2];
//		services[0] = "体育项目";
//		services[1] = "人文历史";
		List<TourInfo> ti = t.search(cities, scenics, services);
		System.out.println(ti.size());

		//t.getByMobile("13911531721");

		t.setHeadImg("http://guantravel.com/resources/media/201412/c7a136dee9caaf8c9edcc53d16cdf871.jpeg");// 头像图片名称
		t.setIntroImg("http://xdujia.com/resources/media/201408/c09dc705c2aff207e4edbbe33f9f08b8.jpg");// 领队介绍的大图;
																										// 基本信息
		t.setNickName("刘志军");// 领队的昵称
		t.setGender(1);// 性别，0：保密，1：男性；2：女性
		t.setLocation("上海");// 所在地，常驻城市
		t.setServiceLevel(3);// 服务水平，星级，从0-5级
		t.setServiceCities("北京、上海、广州");// 服务的城市
		t.setScenic("拉斯维加斯、环球影城");
		t.setIntroduce("无所不能的超级地陪冠行是北京青年旅行社股份有限公司旗下的自驾游品牌,我们出行的方式是小团体自驾游或半自驾游（巴士与自驾车结合），具有行程自由化与个性化、灵活性与舒适性等特点。我们的客户是不愿意受拘束,追求人格独立和心性自由的旅行者。");// 个人简介
		LangCapability lang = new LangCapability();
		lang.setForeignLang("英语、法语");
		lang.setMotherLang("汉语");
		t.setLanguage(lang);// 语言能力 t.setCareer("学生");
		t.setCarType("保时捷轿跑2座");
		t.setPlanning(1);
		t.setCareer("专业导游");

		// 联系方式
		t.setWeixinId("YANGZONGYUAN");// 微信号
		t.setQQ("1439983591");// QQ号
		t.setEMail("yzy@zhinet.net");// 邮箱
		t.setMobile("13911531721");// 移动电话,手机号码作为唯一的标识
		t.setWeixinQR("http://guantravel.com/resources/media/201410/566c75560d6e19fcf33b480bf0f030c1.jpg");// 二维码

		// 服务说明
		t.setFeeDesc("每天服务时长:9个小时\n超时服务费： CNY 100（1小时）\n接待人数：最多4人");// 费用包含
		t.setFeeExcept("服务小费：无\n餐补费：无\n异地住宿补贴：无");// 费用不包含
		t.setSupplement("1.根据客人的要求，专门为您设计只属于您的行程\n2.可以按照您的要求，预定特色酒店\n3.免费提供无线上网mifi,您可以在任何地方发微信，看微博。\n4.接机送机当天有安排旅游行程的，只需支付当天旅游费用600元，接机时间算在导游时间里。（导游机场往返车费客人支付）只接机送机没有安排旅游行程需支付300元。\n5.带您到汇率最高的货币兑换点换取外汇，给您提供各种VIP卡，让您的旅游更经济实惠");// 其他说明
		List<String> items = new ArrayList<String>();
		items.add("接机");
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
		t.setUnitPrice(200);// 基础报价
		List<TourPriceCanlendar> price = new ArrayList<TourPriceCanlendar>();
		TourPriceCanlendar p = new TourPriceCanlendar();
		p.setAdultPrice(400);
		p.setBookCount(0);
		p.setChildPrice(350);
		p.setStartDate("2015-01-01");
		p.setEndDate("2015-06-30");
		p.setMakeTime("2015-02-05");
		p.setTotalCount(14);
		price.add(p);

		p = new TourPriceCanlendar();
		p.setAdultPrice(500);
		p.setBookCount(0);
		p.setChildPrice(350);
		p.setStartDate("2015-07-01");
		p.setEndDate("2015-12-31");
		p.setMakeTime("2015-02-05");
		p.setTotalCount(14);
		price.add(p);

		t.setPrice(price);

		t.Add();

		System.out.println("game over!");
	}
}
