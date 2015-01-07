package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.BaseDocument;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

import com.mdnet.travel.core.vo.ShowProductInfo;

public class ProductAllDetail extends BaseDocument {

	// 出境游产品详细数据
	// 基本信息
	private int pid = 0;// 产品ID关联到原来的数据库
	private String productName = "";// 产品名称
	private String aliasName;// 产品别名，或者内部名称
	private String serialName;// 系列名称
	

	private String deaprtCity;// 出发城市 上海 出发
	private String destCity;// 目的地拉斯维加斯、旧金山、洛杉矶、美国
	private String settle;// 时间安排11天 9晚
	private String traffic;// 交通信息 飞机去飞机回查看详情

	// 费用说明
	private String feeIntro;// 费用说明
	private String feeInclude;// 费用包含
	private String feeNotInclude;// 费用不包含

	private String bookNotice;// 预订说明
	private List<Journey> journey = new ArrayList<Journey>();// 行程安排

	private String className;// 类名称
	private String recommend; // 推荐理由

	private String detail;// 产品详情
	private String feature;// 产品特色
	private String assembly;// 集合地点说明
	private List<String> images = new ArrayList<String>();
	private int aheadDays=10;//团期显示提前天数
	
	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public int getAheadDays() {
		return aheadDays;
	}

	public void setAheadDays(int aheadDays) {
		this.aheadDays = aheadDays;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getDeaprtCity() {
		return deaprtCity;
	}

	public void setDeaprtCity(String deaprtCity) {
		this.deaprtCity = deaprtCity;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getSettle() {
		return settle;
	}

	public void setSettle(String settle) {
		this.settle = settle;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getFeeIntro() {
		return feeIntro;
	}

	public void setFeeIntro(String feeIntro) {
		this.feeIntro = feeIntro;
	}

	public String getFeeInclude() {
		return feeInclude;
	}

	public void setFeeInclude(String feeInclude) {
		this.feeInclude = feeInclude;
	}

	public String getFeeNotInclude() {
		return feeNotInclude;
	}

	public void setFeeNotInclude(String feeNotInclude) {
		this.feeNotInclude = feeNotInclude;
	}

	public String getBookNotice() {
		return bookNotice;
	}

	public void setBookNotice(String bookNotice) {
		this.bookNotice = bookNotice;
	}

	public List<Journey> getJourney() {
		return journey;
	}

	public void setJourney(List<Journey> journey) {
		this.journey = journey;
	}

	// 单件模式
	protected static ProductAllDetail _product = null;
	protected Database db;

	public static ProductAllDetail instance() {
		if (_product == null)
			_product = new ProductAllDetail();
		return _product;
	}

	protected ProductAllDetail() {
		db = new Database("guantravel.com", 5984, "travel");
//		AuthScope authScope = new AuthScope("guantravel.com", 5984);
//		Credentials credentials = new UsernamePasswordCredentials("mingda", "mingdaNET");
//		db.getServer().setCredentials(authScope, credentials);
		className = this.getClass().getName();
	}

	public ProductAllDetail getById(String id) {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.pid != null && doc.pid == "
				+ id + "){emit(doc._id, doc);}}\"}";
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, null,
				null);
		// 将值实例化到对象
		return this.Unseriable(result.getRows().get(0));
	}

	@SuppressWarnings("unchecked")
	public ProductAllDetail Unseriable(ValueRow<Map> result) {
		// TODO Auto-generated method stub
		// List<ValueRow<Map>> rr = result.getRows();
		// m = rr.get(0);
		// ProductAllDetail p = new ProductAllDetail();
		this.journey.clear();
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
			} else if (field.getType() == List.class
					&& vName.compareTo("journey") == 0) {
				List<Map> js = (List<Map>) result.getValue().get(vName);
				for (Map j : js) {
					Journey jo = Journey.Unseriable(j);
					this.journey.add(jo);
				}
			} else if (field.getType() == List.class
					&& vName.compareTo("images") == 0) {
				// String img = (String) ;
				this.images = (List<String>) result.getValue().get(vName);

			}

		}
		return this;
	}

	// private ViewResult<Map> Seriable() {
	// ViewResult<Map> result;
	// return result;
	// }
	public int Add() {

		String mapFunc = "{\"map\":\"function(doc) {if(doc.pid != null && doc.pid == "
				+ this.pid + "){emit(doc._rev, null);}}\"}";
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
	

	public List<ShowProductInfo> getByCities(String cities, String types) {
		//生成城市表达式
		String[] cs = cities.split(",");
		String cities_str = "";
		for (String c : cs) {
			if(c!= null && c.length()>0)
				cities_str += "'" + c + "',";
		}
		if(cities_str != null && cities_str.length()>0)
			cities_str = cities_str.substring(0, cities_str.length() - 1);
		//生成线路类型表达式
		String types_str = "";
		String[] ts = types.split(",");
		for (String t : ts) {
			if(t!= null && t.length()>0)
				types_str += "'" + t + "',";
		}
		if(types_str != null && types_str.length()>0)
			types_str = types_str.substring(0, types_str.length() - 1);
		
		List<ShowProductInfo> list = new ArrayList<ShowProductInfo>();
		String mapFunc = createCitiesMap(cities_str, types_str);
		ViewResult<String[]> result = db.queryAdHocView(String[].class,
				mapFunc, null, null);
		for (@SuppressWarnings("rawtypes")
		ValueRow vr : result.getRows()) {
			ShowProductInfo e = new ShowProductInfo();
			e.setProductID(Integer.parseInt(vr.getKey().toString()));
			String[] v = (String[]) vr.getValue();
			e.setName(v[0]);
			e.setImg(v[1]);
			e.setCities_str(v[2]==null?"":v[2]);
			e.setDays(v[3]==null?"":v[3]);
			e.setLineType(v[4]==null?"":v[4]);
			list.add(e);
		}
		return list;
	}

	public String createCitiesMap(String cities, String types) {
		String mapFunc = "{\"map\":\""
				+ "function(doc) {"
				+ "	  if(doc.pid != null)"
				+ "	  {    var cities = ["	+ cities	+ "];"
				+ "        if(cities== null || cities.length==0){filterType(doc);return;}"
				+ "	       for(var i=0; i<cities.length; i++)"
				+ "	       {"
				+ "	           if(doc.destCity.indexOf(cities[i])>=0)"
				+ "	           {"
				+ "	               filterType(doc);"
				+ "	               return;" 
				+ "	           }" 
				+ "	       }"
				+ "	  }" 
				+ "	}" 
				+ "function filterType(doc)"
				+ "{"
				+ "		var types = ["+types+"];"
				+ "		if(types== null || types.length==0){output(doc);return;}"
				+ "	    for(var i=0; i<types.length; i++)"
				+ "		{"
				+ "			if(doc.lineType.indexOf(types[i])>=0)"
				+ "			{	output(doc); return; }"
				+ "		}"
				+ "}"
				+ "function output(doc) { emit(doc.pid, [doc.productName,doc.images[0], doc.destCity, doc.settle, doc.lineType]); }"
				+ "\"}";
		return mapFunc;
	}

	/**
	 * 
	 * @param sName 产品系列名称
	 * @return
	 */
	public ShowProductInfo[] getProductList(int sName) {
		Options opt = new Options();
		opt.limit(30);
		opt.reduce(false);
		// db.
		opt.descending(true);
		String viewName= "product/list";
		if(sName==1)
			viewName= "product/alone";
		else if(sName==2)
			viewName= "product/cheap";
		else if(sName==3)
			viewName= "product/spring";
		else if(sName==4)
			viewName= "product/standard";
		ViewResult<String[]> result = db.queryView(viewName,
				String[].class, opt, null);
		if (result.getRows().size() > 0) {
			int i = 0;
			ShowProductInfo[] spi = new ShowProductInfo[result.getRows().size()];
			for (@SuppressWarnings("rawtypes")
			ValueRow vr : result.getRows()) {
				spi[i] = new ShowProductInfo();
				spi[i].setProductID(Integer.parseInt(vr.getKey().toString()));
				String[] v = (String[]) vr.getValue();
				spi[i].setName(v[0]);
				spi[i].setImg(v[1]);
				i++;
			}
			return spi;
		} else
			return null;
	}

	public static void main(String[] args) {
		ProductAllDetail p = ProductAllDetail.instance();
		p.setPid(24);// 北京/上海出发。产品ID关联到原来的数据库
		p.setProductName("【任性1人团】西部大环线12日美国自驾游◆七大公园+66号公路◆全国联运◆纯玩◆0自费！");// 产品名称
		//p.setProductName("上海直飞：奥兰多+迈阿密+拉斯维加斯+洛杉矶美国");// 产品名称
		p.setAliasName("美国西部七大国家公园/部落公园+66号公路12日纵情自驾之旅");// 产品别名，或者内部名称
		p.setDeaprtCity("北京/上海 出发");// 出发城市 上海 出发
		//p.setDeaprtCity("上海 出发");// 出发城市 上海 出发
		p.setDestCity("拉斯维加斯、洛杉矶、犹他州");
		p.setSettle("12天 10晚");
		p.setTraffic("飞机去飞机回");// 交通信息
		p.setRecommend("【冠行之旅】专为您一人打造的私人定制团，时间、路线、车型和酒店等均可调整。有导游的自由行！让您的行程更加精彩、自由、省心、安全！ 我们的旅行专家，都有超过10年以上的专业旅行经验，对您的旅行目的地，都了如指掌，为了旅程的完美与难忘，我们投入了全部的心力，贡献我们全部的能力，这既是对旅行的尊重，也是对自身的尊重！那必将令您永生难忘！ 每一位专属导游，都是最专业的旅行家，专属的行程安排与7x24小时导游服务，给您一次独一无二旅行体验！");

		//北京出发的
//		p.getImages()
	//			.add("http://guantravel.com/resources/media/201411/891164c1c01aeb45952fd12968c0781a.jpg");

		//上海出发
		p.getImages()
		.add("http://guantravel.com/resources/media/201411/87ac4426db868ff89a51832946a5df31.jpg");

		// 费用包含
		p.setFeeInclude("1、7x24小时优秀中/英双语自驾向导服务；"
+"2、包含七大公园门票：环球影城+宰恩+拱门+布莱斯+大峡谷+羚羊谷+纪念碑峡谷；<br/>"
+"3、国内往返美国航班经济舱机票、燃油附加费、机票税；<br/>"
+"4、城市繁华区4钻品质酒店，小镇3钻酒店住宿；<br/>"
+"5、免收每人每天8美元的导游小费；<br/>"
+"6、汽车租赁费及随车保险（车损或盗抢不计免赔，第三者100万美金+个人意外伤害保险+各种税费）；<br/>"
+"7、全程燃油/对讲机/中文导航；<br/>"
+"8、中国驾照英文翻译件；<br/>"
+"9、英国太阳联合海外无忧个人旅游保险（高达100万的意外险），详情见保单。<br/>");

		// 费用不包含
		p.setFeeNotInclude(""
				+ "1、护照、签证等相关证件的费用；<br/>"
				+ "2、超出机票包含免费行李的行李托运费；<br/>"
				+ "3、全程餐食自理（多数酒店早餐包含，午、晚餐走到哪吃到哪儿，丰简由己，午餐约4-8美金，晚餐约10美金；<br/>"
				+ "4、停车费、高速通行费及违章罚款等费用请自理（若产生）；<br/>"
				+ "5、行程中自由活动时的产生的一切费用请自理；<br/>"
				+ "6、行程以外的一切私人消费，如酒店内的洗衣费、电话费、饮料、付费电视、行李搬运费以及吸烟罚款等费用，个人消费所引起的小费、出入境的行李海关课税、超重行李的托运费、管理费等；<br/>"
				+ "7、不可抗拒的客观原因（如天灾、战争、罢工等）或航空公司航班延误、取消、领馆签证延误等非我公司原因引起的特殊情况，我公司有权变更或取消行程，由此产生的相关费用（如在外延期签证、食、宿、行一切费用）请自理；<br/>");

		// 费用说明
		p.setFeeIntro("");

		// 线路特色
		p.setFeature("★ 任性一人团，全心全意只为您一人服务！<br/>"
+"★ 专属导游，随身极致服务，伴您放心出行！<br/>"
+"★ 专属线路，具体行程专为您打造！<br/>"
+"★ 全程专业自驾向导7X24小时陪同，无超时费用！<br/>"
+"★ 包含七大公园门票：环球影城+宰恩+拱门+布莱斯+大峡谷+羚羊谷+纪念碑峡谷!<br/>"
+"★ 国内到洛杉矶国际航班往返直飞！<br/>"
+"★ 城市繁华区4钻品质酒店（小镇3钻）！<br/>"
+"★ 美国西海岸七大公园+66号公路！<br/>"
+"★ 无旅游商店购物或额外收费项目！<br/>"
+"★ 巨无霸越野车+燃油+豪华保险套餐+导航全部包含！");

		// 产品详情
		p.setDetail("如需我们代办签证，费用为人民币1250元/人（含签证费及使馆面谈辅导、翻译、填表、快递服务等费用，以上费用为美国使馆收取和旅行社实际支出，拒签恕不退还）。咨询电话：4006-910-800.");
		p.setAssembly("集合地点：<br/>北京出发:航班起飞3.5小时前到达北京首都机场T3航站4层出发大厅6号门内中国海关处。<br/>"
		+"上海出发：航班起飞3.5小时前到达浦东国际机场出发大厅。");
		// 预订说明
		p.setBookNotice("预订说明");

		// 行程安排
		List<Journey> journey = new ArrayList<Journey>();
		Journey j = new Journey();
		// 基本信息
		j.setDay("第一天");
		j.setTitle("北京/上海—洛杉矶（车程约60km）");
		//j.setTitle("上海-奥兰多（2月16日）");

		// 交通信息
		List<Traffic> traffic = new ArrayList<Traffic>();
		Traffic t1 = new Traffic();
		t1.setVehicle("第1段 北京起飞 飞机");
		t1.setDetail("航班号：CA987	北京（13:30）-洛杉矶（次日09:30）	座舱：经济舱");
		traffic.add(t1);
		Traffic t2 = new Traffic();
		t2.setVehicle("第1段 上海起飞 飞机");
		t2.setDetail("航班号：AA182	上海（18:20）-拉斯维加斯（23:20）	座舱：经济舱");
		traffic.add(t2);
		j.setTraffic(traffic);
		
		t2 = new Traffic();
		t2.setVehicle("第2段 汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		List<DetailContext> d = new ArrayList<DetailContext>();
		 d.add(new
		 DetailContext("img","https://imgs.qunarzz.com/vc/48/16/61/6ab366aff5b889f5bb2b96ef8f.jpg_92.jpg"));
		d.add(new DetailContext("text", "13:00左右，抵达洛杉矶<br/>" 
+"14:30 过关提取行李，去车场提车 <br/>"
+"15:30 提车后，前往圣莫妮卡海滩游玩(游玩时间约3小时)<br/>" 
+"17:00 游览完毕，前往酒店 <br/>"
+"18:00 抵达酒店 <br/>"
+"19:00 酒店附近吃晚餐，餐后返回酒店休息<br/>" 
+"本日行程取决于航班抵达时间，可能微调。 <br/>"
+"【行程亮点】<br/>"
+"洛杉矶是加州第一大城市，美国第二大城市，仅次于纽约。同时它也是全世界的文化、科技、国际贸易和高等教育中心之一。洛杉矶一望无垠的沙滩和明媚的阳光、闻名遐迩的“电影王国”好莱坞、引人入胜的迪斯尼乐园、峰秀地灵的贝佛利山庄使洛杉矶成为一座举世闻名的“电影城”和“旅游城”。洛杉矶市区广阔，布局分散，整座城市是以千千万万栋一家一户的小住宅为基础。绿荫丛中，鳞次栉比的庭院式建筑，色彩淡雅，造型精巧，风格各异，遍布于平地山丘上。"));
		j.setDetail(d);
		// 结束第一天的数据
		journey.add(j);
		
		

		j = new Journey();
		// 基本信息
		j.setDay("第二天");
		j.setTitle("洛杉矶（车程约80km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/3d/40/81/77f867b982c14a8c95ac6c4465.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"9:00 出发到环球影城<br/>" 
+"10:00 抵达环球影城游览（游玩时间约7小时） <br/>"
+"17:00 离开环球影城 <br/>"
+"17:15 抵达星光大道，参观星光大道、中国剧院、奥斯卡金像奖颁奖礼的举行地点-杜比剧院（外观）等好莱坞地区景点<br/>" 
+"18:45 离开星光大道，去用餐 <br/>"
+"20:00 回到酒店 <br/>"
+"【行程亮点】<br/>"
+"环球影城是好莱坞最吸引人的去处。它是一个再现电影场景的主题游乐园，其内以多部大制作电影为主题的景点最受欢迎。侏罗纪公园，形态生动的恐龙、危险奇异的侏罗纪丛林、游船从高空俯冲入水的刺激，让人兴奋不已。如果在侏罗纪公园还未过足水瘾，水上世界定可满足你。各类水上特技营造不亚于电影的紧张画面，而火爆的动作场面更会将游客囊括在内，给人真实的危难当头之感。2012年最新推出的变形金刚3D过山车是一段模拟的战车骑乘体验，期间会受到霸天虎的袭击，更有擎天柱英勇的营救。环球影城的电车之旅惊险刺激，乘坐电车，一路上会遇到大地震、洪水、木桥坍塌、大白鲨追尾、与金刚对峙等种种意外！"));
		j.setDetail(d);
		// 结束第二天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第三天");
		j.setTitle("洛杉矶-拉斯维加斯（车程约408km)");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/a3/d1/89/7f6e74f015a0e5986098cc892f.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"9:30 出发 <br/>"
+"12:00 抵达巴斯托小镇，用餐休息 <br/>"
+"13:30 离开巴斯托，前往沙漠上的海市蜃楼-拉斯维加斯<br/>" 
+"16:00 抵达拉斯维加斯，入住酒店 <br/>"
+"18:00 当地特色餐厅用晚餐 <br/>"
+"19:30 夜游拉斯维加斯大道及各赌场，或客人可自由活动（夜游导游服务费包含）<br/>" 
+"22:00左右活动结束，返回酒店 <br/>"
+"【行程亮点】<br/>"
+"在这个沙漠环绕的地方，所有的注意力都集中在热闹非凡的拉斯维加斯大道上。据说世界上十家最大的度假酒店就有九家是在这里。投资商花不惜花费数十亿美元的巨资，打造大批奢华的、标新立异的主题酒店，每家酒店都有自己的赌场、各种高档餐厅以及购物中心，游客可以免费进入参观。每当夜幕降临，拉斯维加斯才会迎来它一天中最辉煌的时刻。除了赌场以外，很多酒店还有丰富多彩的剧场表演秀：歌舞、脱口秀、杂技、催眠、魔术，精彩的你都不知道该看那个。"));
		j.setDetail(d);
		// 结束第三天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第四天");
		j.setTitle("拉斯维加斯-布莱斯(车程约355km)");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/39/26/bd/20df3930ce0e2706f32c27b072.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"9：00 出发前往宰恩国家公园<br/>" 
+"11:00 抵达宰恩国家公园游览（游玩约4小时） <br/>"
+"15:00 前往雪松城 <br/>"
+"16:00 抵达雪松城，入住酒店休息，用餐<br/>" 
+"【行程亮点】<br/>"
+"宰恩国家公园占地约593平方公里，位于科罗拉多高原（Colorado Plateau）、大盆地（Great Basin）与莫哈韦沙漠（Mojave Desert）三地交汇处。公园内的峡谷如鬼斧神工般削就，绝壁凌空，风景如画，为热爱大自然的游客提供了许多游玩选择，既有短途远足路线，也有神秘的探险路线。"));
		j.setDetail(d);
		// 结束第四天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第五天");
		j.setTitle("布莱斯-摩押（车程约569km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/d3/c4/c6/f4f1ca0c0d363064e4c2c35847.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"8:30 出发前往布莱斯国家公园<br/>" 
+"10：00 抵达布莱斯公园（游玩约2小时） <br/>"
+"15:00 游览公园，含午餐时间，之后前往犹他州摩押（Moab）<br/>" 
+"17:30 抵达摩押，入住酒店休息，用餐 <br/>"
+"【行程亮点】<br/>"
+"布莱斯峡谷国家公园（Bryce Canyon National Park）是位于美国犹他州西南部的国家公园。其名字虽有峡谷一词，但其并非真正的峡谷，而是沿着庞沙冈特高原东面，由侵蚀而成的巨大自然露天剧场。其独特的地理结构称为岩柱（hoodoos），由风、河流里的水与冰侵蚀和湖床的沉积岩组成。位于其内的红色、橙色与白色的岩石形成了奇特的自然景观，因此其被誉为天然石俑的殿堂。"));
		j.setDetail(d);
		// 结束第五天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第六天");
		j.setTitle("摩押-科特斯（车程约200km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/d6/21/8e/8284fb1c36f4e3a723c27c159e.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"8:30 前往拱门国家公园参观 <br/>"
+"9:00 抵达拱门国家公园（游玩时间约4小时） <br/>"
+"13:00 离开拱门，前往科罗拉多州科特斯参观梅萨维德国家公园<br/>" 
+"15:00 抵达梅萨维德国家公园（游玩时间约2小时） <br/>"
+"17：00前往科特斯，入住酒店休息，用餐 <br/>"
+"【行程亮点】<br/>"
+"拱门国家公园（Arches National Park）是世界上最大的自然沙岩拱门集中地之一，光是编入目录的就超过二千个，其中最小的只有3尺宽，最大的Landscape Arch则长达306尺。所有的石头上更有着颜色对比非常强烈的纹理。石头的成因为三亿年前这里曾是一片汪洋，海水消失以后又经过了很多年，盐床和其它碎片挤压成岩石并且越来越厚。之后，盐床底部不敌上方的压力而破碎，复经地壳隆起变动，加上风化侵蚀，一个个拱形石头就形成了。直到今天，新的拱门仍持续制造中；反之，老拱门也在逐渐走向毁灭。<br/>" 
+"梅萨维德，西班牙语意为绿色台地，为 18世纪西班牙探险家起的名字。约在2000年前，一个叫作阿纳萨扎伊的印第安部族在此建立了小王国。起初他们在地坑里盖造粗犷的房舍，成为这里最早的聚居和以务农为生的印第安人。后为了躲避其他部族的侵袭，开始迁移到峡谷两侧的悬崖峭壁间，开山凿石，垒砌墙壁，构置峭壁石室，在历史上称为峭壁居民。"));
		j.setDetail(d);
		// 结束第六天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第七天");
		j.setTitle("科特斯-纪念碑谷-佩吉（车程约394km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/a4/75/80/d8bfd674bcaa5feeb349ac62f9.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"8:30 前往美国西部的永恒象征-纪念碑谷和著名的阿甘路游玩<br/>" 
+"10:30 抵达阿甘路，纪念碑谷（游玩时间约2.5小时） <br/>"
+"13:00 离开纪念碑谷，前往佩吉市 <br/>"
+"15:30 抵达亚利桑那州佩吉市,游览马蹄湾峡谷（游玩时间约1小时）<br/>" 
+"17：00入住酒店休息，用餐 <br/>"
+"【行程亮点】<br/>"
+"纪念碑谷公园（Monument Valley Navajo Tribal Park）横跨于亚利桑那州和尤他州的州界上，属于印第安人纳瓦浩（Navajo）部落的保留地，也是美国最大的印地安保留区。谷地由众多巨型孤峰组成，其中最大的孤峰高于谷底约300米，神圣而令人敬畏，一直都是美国西部永恒的象征。它在美国，和大峡谷，黄石公园一样有名。许多从来没有去过纪念碑谷的人，对这里的景色也会有似曾相识的感觉。因为电影《阿甘正传》中，阿甘在他母亲去世后，不停地奔跑，从东海岸跑到西海岸，最后在HWY163停住脚，对他的一群追随者说：“I'm going home”。他一尺多长的白色胡须飘扬在金色的阳光中，背景就是暗红色的Monument Valley的石碑群。"));
		j.setDetail(d);
		// 结束第七天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第八天");
		j.setTitle("佩吉-大峡谷（车程约312km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/e4/08/aa/fb39b4e9a0800adaeb97252f0e.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"8:30 前往羚羊峡谷 <br/>"
+"9：00 抵达羚羊谷游览（游玩时间约1.5小时）<br/>" 
+"10：30 结束羚羊谷游览，前往大峡谷南峡 <br/>"
+"12:30 抵达大峡谷南缘(Grand Canyon South Rim)（游玩约6小时）<br/>" 
+"17:00 入住酒店休息，用餐 <br/>"
+"【行程亮点】<br/>"
+"羚羊峡谷（Antelope Canyon）是世界上著名的狭缝型峡谷之一，被列为地球上十大最奇特的地貌之一。峡谷总长约400多米，谷顶两侧的距离很窄，但由谷顶到谷底的垂直距离却高达十数米。阳光从峡谷的谷顶沿着缝隙倾泻下，到处都是一线天的感觉。太阳光线经由曲曲折折的不同深度的红色岩层的折射，呈现出了魔幻缤纷的颜色，而且谷内的色彩随着日光照射强度不同也在变化,从黄色到桔黄色到粉色到红褐色，让人叹为观止！<br/>" 
+"马蹄湾是科罗拉多河(Colorado River)在亚利桑那州(Arizona)境内的一截U形的河道，也是格兰峡谷(Glen Canyon)其中的一小段，由于河湾环绕的巨岩形似马蹄，所以叫作了“马蹄湾”，也有人叫它科罗拉多河的大拐弯。马蹄湾的景色是千变万化的，正午的时候往往是一湾碧水，而到了傍晚，河水则会映射出四周岩壁的颜色，深邃中更具魅力。"));
		j.setDetail(d);
		// 结束第八天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第九天");
		j.setTitle("大峡谷-拉斯维加斯（车程约347km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/f4/22/9a/4a74d0507d444f8c7ed0aca0c2.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"9: 30 前往拉斯维加斯，途经66号公路小镇sailigman。 <br/>"
+"15:00 抵达拉斯维加斯，入住酒店休息，用餐 <br/>"
+"【行程亮点】<br/>"
+"大峡谷是一处举世闻名的自然奇观，位于亚利桑那州西北部的凯巴布高原上。它全长446公里，平均宽度16公里，最大深度1740米，是联合国教科文组织选为受保护的世界天然遗产之一。其雄伟的地貌、浩瀚的气魄、慑人的神态、奇突的景色，世无其匹！美国总统西奥多-罗斯福来此游览时，感叹地说：“大峡谷使我充满了敬畏，它无可比拟，无法形容，在这辽阔的世界上，绝无仅有。”有人说，在太空唯一可用肉眼看到的自然景观就是大峡谷。<br/>" 
+"66号公路，曾属于美国公路系统，建于1926年。它起始于美国北部的芝加哥，斜贯美国版图一直到西部的洛杉矶，跨八个州，三个时区，全长3943公里。在30年代，66号公路对沿途的许多地区的经济帮助极大，被美国人亲切地唤作“母亲之路”。后来因为不再能胜任日益繁忙的州际交通而被迫退役，于1985年从美国公路系统中被抹去。"));
		j.setDetail(d);
		// 结束第九天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十天");
		j.setTitle("拉斯维加斯-洛杉矶（车程约408km）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("汽车");
		t2.setDetail("座位数：9座及以下");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/94/11/c7/ae38951f024c180d9a235e4d40.jpg_92.jpg"));
		d.add(new DetailContext(
				"text",
				"全天自由活动 <br/>"
+"9:00 离开拉斯，前往棕榈泉 <br/>"
+"12:30 抵达棕榈泉奥特莱斯 <br/>"
+"17:00 前往洛杉矶 <br/>"
+"19：00 抵达酒店，用晚餐。<br/>" 
+"【行程亮点】<br/>"
+"美国西部最大的奥特莱斯-Desert Hills Premium Outlets自由购物，与其它奥特莱斯相比，这里的国际大品牌更多，包括Bottega Veneta、Burberry、D&G、Ferregamo、Gucci、Prada、Versace、Jimmy Choo、Tod'等等，一共有180多个品牌可供选择"));
		j.setDetail(d);
		// 结束第十天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十一、十二天");
		j.setTitle("洛杉矶-北京/上海（车程约30km）");
		//j.setTitle("洛杉矶-上海（2月26日）");
		// 交通信息
		traffic = new ArrayList<Traffic>();
		t2 = new Traffic();
		t2.setVehicle("第1段交通：汽车");
		t2.setDetail("座位数：9座以及下");
		traffic.add(t2);
		j.setTraffic(traffic);

		t2 = new Traffic();
		t2.setVehicle("第2段交通：飞机");
		t2.setDetail("航班号：CA988	洛杉矶（11:50）-北京（16:40）	座舱：经济舱");
		traffic.add(t2);
		j.setTraffic(traffic);
		
		t2 = new Traffic();
		t2.setVehicle("第2段交通：飞机");
		t2.setDetail("航班号：AA183	 	座舱：经济舱");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		// d.add(new
		// DetailContext("img","http://guantravel.com/resources/media/201409/f7a4c716ce1f6d6ee866663d0a50b641.jpg"));
		d.add(new DetailContext("text", "搭乘航班回国。"));
		j.setDetail(d);
		// 结束第十一天的数据
		journey.add(j);

		// 设置所有的行程安排
		p.setJourney(journey);

		p.Add();

	}

}
