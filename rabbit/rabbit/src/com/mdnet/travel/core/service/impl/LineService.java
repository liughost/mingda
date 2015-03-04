package com.mdnet.travel.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mdnet.travel.core.dao.DetailContext;
import com.mdnet.travel.core.dao.GroupDateDAO;
import com.mdnet.travel.core.dao.Journey;
import com.mdnet.travel.core.dao.Meal;
import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.dao.TourInfo;
import com.mdnet.travel.core.dao.Traffic;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.vo.ShowProductInfo;

@Service(LineService.SERVICE_NAME)
@Scope("singleton")
public class LineService extends BaseCouchService {

	@Resource(name = GroupDateDAO.DAO_NAME)
	protected GroupDateDAO groupDateDAO;
	public static final String SERVICE_NAME = "com.mdnet.travel.core.service.impl.LineService";

	public LineService() {
		if (db == null)
			db = new Database("guantravel.com", 5984, "travel");
	}

	public ProductAllDetail newLine() {
		return new ProductAllDetail(db);
	}

	public List<ShowProductInfo> getByCities(String cities, String types) {
		// 生成城市表达式
		String[] cs = cities.split(",");
		String cities_str = "";
		for (String c : cs) {
			if (c != null && c.length() > 0)
				cities_str += "'" + c + "',";
		}
		if (cities_str != null && cities_str.length() > 0)
			cities_str = cities_str.substring(0, cities_str.length() - 1);
		// 生成线路类型表达式
		String types_str = "";
		String[] ts = types.split(",");
		for (String t : ts) {
			if (t != null && t.length() > 0)
				types_str += "'" + t + "',";
		}
		if (types_str != null && types_str.length() > 0)
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
			e.setCities_str(v[2] == null ? "" : v[2]);
			e.setDays(v[3] == null ? "" : v[3]);
			e.setLineType(v[4] == null ? "" : v[4]);
			list.add(e);
		}
		return list;
	}

	public List<ShowProductInfo> search(String[] cities, String[] scenics,
			String[] types, String attach) {
		List<ShowProductInfo> list = new ArrayList<ShowProductInfo>();
		String mapFunc = this.createSearchMapFunction(cities, scenics, types,
				attach);
		ViewResult<String[]> result = db.queryAdHocView(String[].class,
				mapFunc, null, null);
		for (@SuppressWarnings("rawtypes")
		ValueRow vr : result.getRows()) {
			ShowProductInfo e = new ShowProductInfo();
			
			e.setProductID(Integer.parseInt(vr.getKey().toString()));
			String[] v = (String[]) vr.getValue();
			e.setName(v[0]);
			e.setImg(v[1]);
			e.setCities_str(v[2] == null ? "" : v[2]);
			e.setDays(v[3] == null ? "" : v[3]);
			e.setLineType(v[4] == null ? "" : v[4]);
			e.setCouchId(v[5] == null ? "" : v[5]);
			list.add(e);
		}
		return list;
	}

	protected String createSearchMapFunction(String[] cities, String[] scenics,
			String[] types, String attach) {

		// String mapFunc = "function(doc){emit(doc._id, [doc.destCity]);}";
		String mapFunc = "{\"map\":\""
				+ "function(doc){"
				+ "if(doc.pid != null && matchCity(doc) &&matchScenic(doc) &&matchType(doc)&&matchAttach(doc)){"
				+ "output(doc);}}";

		mapFunc += "function matchScenic(doc){"
				+ "var scenics = ["
				+ this.arrayStr(scenics)
				+ "];"
				+ "for(var i=0; i<scenics.length; i++){"
				+ "if(doc.scenics == null || doc.scenics.indexOf(scenics[i])<0) {"
				+ " return false;    }  	}" + "return true;}";

		mapFunc += "function matchType(doc){"
				+ "var types = ["
				+ this.arrayStr(types)
				+ "];"
				+ "for(var i=0; i<types.length; i++)	{"

				+ "if(doc.lineType == null || doc.lineType.indexOf(types[i])<0) {"
				+ "           return false;       }      	}" + "return true;}";
		mapFunc += "function matchAttach(doc){"
				+ "var attach = '"
				+ (attach == null ? "" : attach)
				+ "';"
				+ "if(attach== null || attach.length==0) return true;"
				+ "if(doc.recommend == null || doc.recommend.indexOf(attach)<0)   {"
				+ "return false; }" + "return true;	}";

		mapFunc += "function matchCity(doc){"
				+ "var cities = ["
				+ this.arrayStr(cities)
				+ "];"
				+ "for(var i=0; i<cities.length; i++){"
				+ "if(doc.destCity == null || doc.destCity.indexOf(cities[i])<0){"
				+ " return false;}	}" + "return true;	}";

		mapFunc += "function output(doc){"
				+ "emit(doc.pid, [doc.productName,doc.images[0], doc.destCity, doc.settle, doc.lineType, doc._id]);}";

		mapFunc += "\"}";
		return mapFunc;
	}

	protected String createCitiesMap(String cities, String types) {
		String mapFunc = "{\"map\":\"" + "function(doc) {"
				+ "	  if(doc.pid != null)" + "	  {    var cities = ["
				+ cities
				+ "];"
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
				+ "		var types = ["
				+ types
				+ "];"
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

	protected String createCatelogMap(String catelog) {
		String mapFunc = "{\"map\":\"" + "function(doc) {"
				+ "	  if(doc.pid != null && doc.aliasName.indexOf('" + catelog
				+ "')>=0){" + " emit(doc.productName, doc);" + "}}" + "\"}";
		return mapFunc;
	}

	public ShowProductInfo[] getProductList(int type, int page) {
		ShowProductInfo[] spis = this.getProductList(type);
		for (ShowProductInfo spi : spis) {
			// 生成团期最小日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			long d = now.getTime() / 1000 + 30 * 24 * 60 * 60;
			Date minDay = new Date(d * 1000);

			String minDate = sdf.format(minDay);

			String where = " where productId=" + spi.getProductID()
					+ " and  startDate >'" + minDate + "'";

			GroupDate g = this.groupDateDAO.getFirst(where);
			if (g != null)// 单位：元
				spi.setLowPrice(g.getAdultPrice());
			// ps[i].setDays(pi.getDays());
			// ps[i].setIntroduce(pi.getIntroduce());
		}
		return spis;
	}

	public ProductAllDetail getProductInfo(String id) {
		return this.getById(id);
	}

	public ProductAllDetail getById(String id) {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.pid != null && doc.pid == "
				+ id + "){emit(doc._id, doc);}}\"}";
		@SuppressWarnings("rawtypes")
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, null,
				null);
		// 将值实例化到对象
		ProductAllDetail p = new ProductAllDetail();
		System.out.println("-----" + id + "--------");
		return p.Unseriable(result.getRows().get(0));
	}

	/**
	 * 
	 * @param sName
	 *            产品系列名称
	 * @return
	 */
	public ShowProductInfo[] getProductList(int sName) {
		Options opt = new Options();
		opt.limit(30);
		opt.reduce(false);
		// db.
		opt.descending(true);
		String viewName = "product/list";
		if (sName == 1)
			viewName = "product/alone";
		else if (sName == 2)
			viewName = "product/cheap";
		else if (sName == 3)
			viewName = "product/spring";
		else if (sName == 4)
			viewName = "product/standard";
		ViewResult<String[]> result = db.queryView(viewName, String[].class,
				opt, null);
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

	@SuppressWarnings("unchecked")
	public List<ShowProductInfo> findProductsBycatalog(String lineCatelog,
			String contextPath) {
		List<ShowProductInfo> spi = new ArrayList<ShowProductInfo>();
		String mapF = this.createCatelogMap(lineCatelog);
		@SuppressWarnings("rawtypes")
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapF, null, null);
		for (@SuppressWarnings("rawtypes")
		ValueRow vr : result.getRows()) {
			ProductAllDetail p = new ProductAllDetail();
			p.Unseriable(vr);
			ShowProductInfo s = new ShowProductInfo();
			s.setCouchId(p.getId());
			s.setName(p.getProductName());
			if (p.getImages().size() <= 0)
				s.setImg(contextPath + "/resources/rabbit/images/photo-4.jpg");
			else
				s.setImg(p.getImages().get(0));
			s.setIntroduce(p.getRecommend());
			s.setProductID(p.getPid());
			spi.add(s);
		}
		return spi;
	}

	public static void main(String[] args) {

		/*
		 * LineService line = new LineService(); String[] cities=new String[2];
		 * cities[0] = "弗雷斯诺"; cities[1] = "都柏林"; String[] scenics=new
		 * String[2]; scenics[0] = "米德湖"; scenics[1] = "大峡谷西峡"; String[]
		 * types=new String[2]; types[0] = "购物"; types[1] = "美食";
		 * //line.getByCities("", ""); line.search(cities, scenics, types, "");
		 */
		LineService line = new LineService();
		// line.
		ProductAllDetail p = line.newLine();
		p.setPid(19);
		p.setProductName("【1到15人】征服美国母亲路：完整66号公路14日美国自驾游（包含美国西部主要国家公园）◆全国联运◆纯玩◆0自费！");
		// p.setProductName("上海直飞：奥兰多+迈阿密+拉斯维加斯+洛杉矶美国");// 产品名称
		p.setAliasName("征服美国母亲路：完整66号公路14日");
		p.setDeaprtCity("北京/上海   出发");// 出发城市 上海 出发
		// p.setDeaprtCity("上海 出发");// 出发城市 上海 出发
		p.setDestCity("科罗拉多州、拉斯维加斯、洛杉矶、<br/>伊利诺伊州、美国、犹他州、芝加哥");
		p.setSettle("14天 12晚");
		p.setTraffic("飞机去飞机回");// 交通信息
		p.setRecommend("【冠行之旅】专为您一人打造的私人定制团，时间、路线、车型和酒店等均可调整。有导游的自由行！让您的行程更加精彩、自由、省心、安全！ 我们的旅行专家，都有超过10年以上的专业旅行经验，对您的旅行目的地，都了如指掌，为了旅程的完美与难忘，我们投入了全部的心力，贡献我们全部的能力，这既是对旅行的尊重，也是对自身的尊重！那必将令您永生难忘！每一位专属导游，都是最专业的旅行家，专属的行程安排与7x24小时导游服务，给您一次独一无二旅行体验！");

		p.getImages()
				.add("http://img1.qunarzz.com/p/tts1/1412/b0/b061f1ce90ed3c1fcdb.jpg_r_390x260x90_35dc01f1.jpg");

		// 费用包含
		p.setFeeInclude("1、7x24小时优秀中/英双语自驾向导服务；<br/>"
				+ "2、环球影城+微利斯塔+大拱门+羚羊谷+纪念碑等公园门票！<br/>"
				+ "3、北京往返美国航班经济舱机票、燃油附加费、机票税；<br/>"
				+ "4、城市繁华区4钻品质酒店，小镇3钻酒店住宿；<br/>" + "5、免收每人每天8美元的导游小费；<br/>"
				+ "6、汽车租赁费及随车保险（车损或盗抢不计免赔，第三者100万美金+个人意外伤害保险+各种税费）；<br/>"
				+ "7、一箱燃油/对讲机/中文导航；<br/>" + "8、中国驾照英文翻译件；<br/>"
				+ "9、英国太阳联合海外无忧个人旅游保险（高达100万的意外险），详情见保单。");

		// 费用不包含
		p.setFeeNotInclude("1、护照、签证等相关证件的费用；<br/>"
				+ "2、超出机票包含免费行李的行李托运费；<br/>"
				+ "3、全程餐食自理（多数酒店早餐包含，午、晚餐走到哪吃到哪儿，丰简由己，午餐约4-8美金，晚餐约10美金+）；<br/>"
				+ "4、停车费、高速通行费及违章罚款等费用请自理（若产生）；<br/>"
				+ "5、行程中自由活动时的产生的一切费用请自理；<br/>"
				+ "6、行程以外的一切私人消费，如酒店内的洗衣费、电话费、饮料、付费电视、行李搬运费以及吸烟罚款等费用，个人消费所引起的小费、出入境的行李海关课税、超重行李的托运费、管理费等；<br/>"
				+ "7、不可抗拒的客观原因（如天灾、战争、罢工等）或航空公司航班延误、取消、领馆签证延误等非我公司原因引起的特殊情况，我公司有权变更或取消行程，由此产生的相关费用（如在外延期签证、食、宿、行一切费用）请自理；");

		// 费用说明
		p.setFeeIntro("");

		// 线路特色
		p.setFeature("★ 任性一人团，全心全意只为您一人服务！<br/>" + "★ 专属导游，随身极致服务，伴您放心出行！<br/>"
				+ "★ 专属线路，具体行程专为您打造！<br/>" + "★ 全程专业自驾向导7X24小时陪同，无超时费用！<br/>"
				+ "★ 包含门票：环球影城+大峡谷+梅萨维德+羚羊谷+纪念碑+微利斯塔+老爷车博物馆+牛仔博物馆等景点门票！<br/>"
				+ "★ 城市繁华区4钻品质酒店（小镇3钻）！<br/>" + "★ 无旅游商店购物或额外收费项目！<br/>"
				+ "★ 真正的66号公路穿越！<br/>" + "★ 巨无霸越野车四千公里自驾，横贯美国东西10州！<br/>"
				+ "★ 66号公路深度探索+西部四大国家/部落公园纵情体验！<br/>"
				+ "★ 全程中文导航及豪华保险包含，杜绝后顾之忧！");

		// 产品详情
		p.setDetail("如需我们代办签证，费用为人民币1250元/人（含签证费及使馆面谈辅导、翻译、填表、快递服务等费用，以上费用为美国使馆收取和旅行社实际支出，拒签恕不退还）。咨询电话：4006-910-800.");
		p.setAssembly("<br/>北京出发:航班起飞3.5小时前到达北京首都机场T3航站4层出发大厅6号门内中国海关处。<br/>"
				+ "上海出发：航班起飞3.5小时前到达浦东国际机场出发大厅。");
		// 预订说明
		p.setBookNotice("预订说明");

		// 行程安排
		List<Journey> journey = new ArrayList<Journey>();
		Journey j = new Journey();
		// 基本信息
		j.setDay("第一天");
		j.setTitle("北京/上海—芝加哥");

		// 交通信息
		List<Traffic> traffic = new ArrayList<Traffic>();
		Traffic t1 = new Traffic();

		t1.setVehicle("第1段 飞机");
		t1.setDetail("国际航班	北京-芝加哥（次日到达）	座舱：经济舱");
		traffic.add(t1);

		Traffic t2 = new Traffic();
		t2.setVehicle("第1段 飞机");
		t2.setDetail("国际航班	上海-芝加哥（次日到达）	座舱：经济舱");
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
		d.add(new DetailContext(
				"img",
				"http://guantravel.com/resources/media/201410/2ad01cd869a93ddc7a73413aacc3444c.jpg"));
		d.add(new DetailContext(
				"text",
				"搭乘国际航班飞往芝加哥。抵达后提车前往酒店休息。下午品尝著名的深盘Pizza，体验独特的芝加哥美食。傍晚登顶威利斯大厦(含门票)，观赏钢铁之城夜景。<br/>"
						+ "芝加哥（Chicago）是位于美国中西部，属伊利诺伊州，东临密歇根湖，是美国仅次于纽约市和洛杉矶的国际化大都市，美国第三大城市。芝加哥也是全球最重要的一个金融中心，是美国第二大商业中心区，也是美国最大的期货市场。也被评为美国发展最均衡的经济体。此外，芝加哥都市区新增的企业数一直位居美国第一位。被誉为“摩天大楼的故乡”。"));
		j.setDetail(d);
		// 结束第一天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第二天");
		j.setTitle("芝加哥");
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
				"http://guantravel.com/resources/media/201410/2f474b0ba4fa1f43ca7ee8c354f0d9bf.jpg"));
		d.add(new DetailContext(
				"text",
				"全天游览芝加哥著名景点，包括66号公路起点标志、华丽一英里（Magnificent Mile）、千禧公园、白金汉喷泉、格兰特公园等。<br/>"
						+ "华丽一英里（Magnificent Mile）是指美国芝加哥的北密歇根大街的一段，从卢普区（Chicago Loop）与近北区边界、芝加哥河上的的密歇根大街桥（Michigan Avenue Bridge），直到橡树街为止。它将芝加哥的黄金海岸与市中心连接起来。这里有世界上最好的餐馆、旅馆、和商店，还有许多世界最高的建筑物。此外，众多享有声誉的建筑都坐落在华丽一英里，例如约翰汉考克中心、芝加哥水塔和论坛报大厦。"));
		j.setDetail(d);
		// 结束第二天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第三天");
		j.setTitle("芝加哥-圣路易斯（478公里, 5小时左右车程）");
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
				"http://guantravel.com/resources/media/201410/70b972dd114fe41060cb270b43ac3ab8.jpg"));
		d.add(new DetailContext(
				"text",
				"开始我们的征服之旅！本日途经“吉米尼巨人(Gemini Giant)、在“波尔卡点汽车餐馆(Polka-Dot Drive In)感受美国公路饮食文化、保留着大量66号公路元素的Pontia，以及位于春田市的林肯纪念碑。傍晚抵达密苏里州圣路易斯。"));
		j.setDetail(d);
		// 结束第三天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第四天");
		j.setTitle("圣路易斯-塔尔萨（635公里, 6小时左右车程）");
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
				"http://guantravel.com/resources/media/201410/5fb429cd839d8e140e7ad7dcca0c874e.jpg"));
		d.add(new DetailContext(
				"text",
				"早饭后参观被人们叫做“通向西部之路”(Gateway to the West)的圣路易斯大拱门和百威啤酒厂。之后一路向西，游览66号公路重镇-墙画之城古巴和乔布林等地，满眼的尽是贩卖冒着气泡水的酒馆，霓虹灯闪烁的汽车旅馆，古式而老旧的加油站，西部的传奇逸事在眼前一一上演。傍晚抵达塔尔萨。"));
		j.setDetail(d);
		// 结束第四天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第五天");
		j.setTitle("塔尔萨-埃尔克城（349公里, 4小时左右车程）");
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
				"http://guantravel.com/resources/media/201410/c1e931bdd811a4ff349579abc2a2f420.jpg"));
		d.add(new DetailContext(
				"text",
				"前往俄克拉荷马城，参观国家牛仔和西部遗产博物馆（含门票），了解66公路的历史文化。之后在66号公路上著名的安妮炸鸡店Ann’s chicken Fry house享用标准美式午餐。下午在Clinton 参观66号公路老爷车博物馆（含门票）。傍晚抵达埃尔克城。"));
		j.setDetail(d);
		// 结束第五天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第六天");
		j.setTitle("埃尔克城-雷顿（623公里, 6小时左右车程）");
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
				"http://guantravel.com/resources/media/201410/648aaaa6ad9fabf4155fe39c0f4992f0.jpg"));
		d.add(new DetailContext(
				"text",
				"经过塞尔（Sayre）、沙特克(Shattuck)、盖蒙(Guymon)等地，进入美国中西部新墨西哥州，一路上湛蓝如洗的天空飘着朵朵白云，对应的是一望无际的金色草原、成群的肥壮牛羊和远方缓缓起伏的山脉。厌倦了都市生活的人，一定会享受这单纯而又壮丽的景色。夜宿雷顿。"));
		j.setDetail(d);
		// 结束第六天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第七天");
		j.setTitle("雷顿-科特兹（394公里,4小时左右车程）");
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
				"http://guantravel.com/resources/media/201410/3344f6e0e6f9849192a2cfe4339d440e.jpg"));
		d.add(new DetailContext(
				"text",
				"今天我们进入科罗拉多高原。这是一片让人赞叹的土地，红色大地、岩盘、拱洞、峡谷，苍穹，酷日，热风，色彩浓重，对比强烈，它的粗犷荒凉让人啧啧称奇。吴宇森的电影《断箭》（Broken Arrow）中，约翰屈伏塔（John Travdta）和克里斯汀史莱特（Christian Slater）正邪大斗法的场景就是在这里拍摄的。抵达科特兹后，参观本此行程中第一个国家公园-梅萨维德国家公园（含门票）。"));
		j.setDetail(d);
		// 结束第七天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第八天");
		j.setTitle("科特兹-佩吉(394公里4小时左右车程)");
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
				"http://guantravel.com/resources/media/201410/556fc735a7a349d4e886a134911db9bb.jpg"));
		d.add(new DetailContext(
				"text",
				"驱车前往美国西部的永恒象征-纪念碑谷（含门票）和著名的阿甘路游览。下午抵达佩吉，参观地球上十大最奇特的地貌之一羚羊谷（含门票）和科罗拉多河大拐弯-马蹄湾。<br/>"
						+ "* 纪念碑谷公园（Monument Valley Navajo Tribal Park）横跨于亚利桑那州和尤他州的州界上，属于印第安人纳瓦浩（Navajo）部落的保留地，也是美国最大的印地安保留区。谷地由众多巨型孤峰组成，其中最大的孤峰高于谷底约300米，神圣而令人敬畏，一直都是美国西部永恒的象征。它在美国，和大峡谷，黄石公园一样有名。许多从来没有去过纪念碑谷的人，对这里的景色也会有似曾相识的感觉。因为电影《阿甘正传》中，阿甘在他母亲去世后，不停地奔跑，从东海岸跑到西海岸，最后在HWY163停住脚，对他的一群追随者说：“I'm going home”。他一尺多长的白色胡须飘扬在金色的阳光中，背景就是暗红色的Monument Valley的石碑群。<br/>"
						+ "*羚羊峡谷（Antelope Canyon）是世界上著名的狭缝型峡谷之一，被列为地球上十大最奇特的地貌之一。峡谷总长约400多米，谷顶两侧的距离很窄，但由谷顶到谷底的垂直距离却高达十数米。阳光从峡谷的谷顶沿着缝隙倾泻下，到处都是一线天的感觉。太阳光线经由曲曲折折的不同深度的红色岩层的折射，呈现出了魔幻缤纷的颜色，而且谷内的色彩随着日光照射强度不同也在变化,从黄色到桔黄色到粉色到红褐色，让人叹为观止！<br/>"
						+ "* 马蹄湾是科罗拉多河(Colorado River)在亚利桑那州(Arizona)境内的一截U形的河道，也是格兰峡谷(Glen Canyon)其中的一小段，由于河湾环绕的巨岩形似马蹄，所以叫作了“马蹄湾”，也有人叫它科罗拉多河的大拐弯。马蹄湾的景色是千变万化的，正午的时候往往是一湾碧水，而到了傍晚，河水则会映射出四周岩壁的颜色，深邃中更具魅力。"));
		j.setDetail(d);
		// 结束第八天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第九天");
		j.setTitle("佩吉-威廉姆斯(312公里, 3.4小时左右车程)");
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
				"http://guantravel.com/resources/media/201410/1213669205e4c767ef6b5604740e3e9c.jpg"));
		d.add(new DetailContext(
				"text",
				"前往大峡谷国家公园游览（（含门票，南峡）。夜宿威廉姆斯。<br/>"
						+ "大峡谷是一处举世闻名的自然奇观，位于亚利桑那州西北部的凯巴布高原上。它全长446公里，平均宽度16公里，最大深度1740米，是联合国教科文组织选为受保护的世界天然遗产之一。其雄伟的地貌、浩瀚的气魄、慑人的神态、奇突的景色，世无其匹！美国总统西奥多-罗斯福来此游览时，感叹地说：“大峡谷使我充满了敬畏，它无可比拟，无法形容，在这辽阔的世界上，绝无仅有。”有人说，在太空唯一可用肉眼看到的自然景观就是大峡谷。"));
		j.setDetail(d);
		// 结束第九天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十天");
		j.setTitle("威廉姆斯-拉斯维加斯(347公里, 4小时左右车程)");
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
				"http://guantravel.com/resources/media/201410/4c8e57ea5b761c696a14d942ca281112.jpg"));
		d.add(new DetailContext(
				"text",
				"前往沙漠上的海市蜃楼-拉斯维加斯。沿途参观塞利格曼、金曼66号公路著名站点。抵达拉斯维加斯入住后酒店休息。傍晚夜游拉斯维加斯大道（包含），体验世界赌城各种极尽奢华的酒店赌场。<br/>"
						+ "在这个沙漠环绕的地方，所有的注意力都集中在热闹非凡的拉斯维加斯大道上。据说世界上十家最大的度假酒店就有九家是在这里。投资商花不惜花费数十亿美元的巨资，打造大批奢华的、标新立异的主题酒店，每家酒店都有自己的赌场、各种高档餐厅以及购物中心，游客可以免费进入参观。每当夜幕降临，拉斯维加斯才会迎来它一天中最辉煌的时刻。除了赌场以外，很多酒店还有丰富多彩的剧场表演秀：歌舞、脱口秀、杂技、催眠、魔术，精彩的你都不知道该看那个。"));
		j.setDetail(d);
		// 结束第十天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十一天");
		j.setTitle("拉斯维加斯-洛杉矶(408公里4.5小时左右车程)");
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
				"http://guantravel.com/resources/media/201410/597c52483ef9e52d56dd6fdcb810c3cc.jpg"));
		d.add(new DetailContext(
				"text",
				"前往美国西部最大的奥特莱斯-Desert Hills Premium Outlets自由购物，与其它奥特莱斯相比，这里的国际大品牌更多，包括Bottega Veneta、Burberry、D&G、Ferregamo、Gucci、Prada、Versace、Jimmy Choo、Tod'等等，一共有180多个品牌可供选择。傍晚抵达天使之城-洛杉矶。"));
		j.setDetail(d);
		// 结束第十一天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十二天");
		j.setTitle("洛杉矶");
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
				"http://guantravel.com/resources/media/201410/50e99bf0f0d84fa4ef498543d02d8634.jpg"));
		d.add(new DetailContext(
				"text",
				"上午在环球影城游玩（含门票）。之后参观星光大道、中国剧院、奥斯卡金像奖颁奖礼的举行地点-杜比剧院（外观）等好莱坞景点。傍晚抵达本次行程最后一站-圣莫妮卡海滩，即66号终点的标志牌，结束66号征服之旅。"));
		j.setDetail(d);
		// 结束第十二天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十三天");
		j.setTitle("洛杉矶-北京/上海");
		// j.setTitle("洛杉矶-上海（2月26日）");
		// 交通信息
		traffic = new ArrayList<Traffic>();

		t2 = new Traffic();
		t2.setVehicle("飞机");
		t2.setDetail("国际航班	座舱：经济舱");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		d.add(new DetailContext(
				"img",
				"http://guantravel.com/resources/media/201408/7566c32b4aa09c92dfa1bb3fc9affa3f.jpg"));
		d.add(new DetailContext("text", "搭乘航班回国。"));
		j.setDetail(d);
		// 结束第十三天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第十四天");
		j.setTitle("北京/上海");
		// j.setTitle("洛杉矶-上海（2月26日）");
		// 交通信息
		traffic = new ArrayList<Traffic>();

		t2 = new Traffic();
		t2.setVehicle("飞机");
		t2.setDetail("国际航班	座舱：经济舱");
		traffic.add(t2);
		j.setTraffic(traffic);

		j.setHotel("四星或同等酒店");
		j.setMeal(new Meal("自助", "自理", "自理"));

		// 详细内容
		d = new ArrayList<DetailContext>();
		// d.add(new
		// DetailContext("img","http://guantravel.com/resources/media/201408/7566c32b4aa09c92dfa1bb3fc9affa3f.jpg"));
		d.add(new DetailContext("text", "平安抵达。"));
		j.setDetail(d);
		// 结束第十四天的数据
		journey.add(j);

		// 设置所有的行程安排
		p.setJourney(journey);

		p.Add();
	}

	public ProductAllDetail takeLine(String id) {
		Map result = db.getDocument(Map.class, id);
		ProductAllDetail p = new ProductAllDetail();
		// result.getProperty("price");

		return p.Unseriable(result);
	}

}
