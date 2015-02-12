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
	
	public ProductAllDetail newLine()
	{
		return new ProductAllDetail();
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

	public String createCitiesMap(String cities, String types) {
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
	
	
	public ShowProductInfo[] getProductList(int type, int page) {
		ShowProductInfo[] spis = this.getProductList(
				type);
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
	public static void main(String[] args) {
		LineService line  = new LineService();
		ProductAllDetail p = line.newLine();
		p.setPid(25);
		p.setProductName("轻奢自驾十人小团：加州一号公路+大峡谷+美西三大经典城市10日美国自驾游");
		
		p.setAliasName("加州一号公路+大峡谷+美西三大经典城市10日美国自驾游");
		p.setDeaprtCity("北京/上海   出发");// 出发城市 上海 出发
		//p.setDeaprtCity("上海 出发");// 出发城市 上海 出发
		p.setDestCity("拉斯维加斯、盐湖城、洛杉矶、怀俄明、美国、犹他州");
		p.setScenics("黄石公园、老忠实泉、大稜镜彩泉、大提顿国家公园、大盐湖、布莱斯布赖斯国家公园、羚羊谷、马蹄湾、大峡谷、威廉姆斯小镇、66号公路、棕榈泉奥特莱斯、星光大道、中国剧院（外观）、杜比剧院");
		p.setShow(true);
		p.setSettle("10天 8晚");
		p.setTraffic("飞机去飞机回");// 交通信息
		p.setSerialName("特惠团");
		p.setRecommend("【冠行之旅】专业自驾向导带您自驾美式巨型肌肉车！ 驰骋在美利坚的大地上，自由随心享受绚丽风光和美式风情！ 我们不是大巴团！ 上车睡觉，下车拍照，什么都玩不到，还可能遭遇消费陷阱！ 我们不是自由行！ 语言不通，操心费力，没人管！ 怎么办？ 那就跟我们出发吧！ “欢迎来到加州旅馆，多么可爱的地方，如此美丽的脸庞……”每当听到《加州旅馆》这首经典的歌曲，那迷茫而颓废的旋律背后，总是伴随着令人不禁想前往美国西部那片热土一探究竟的欲望。");

		
		p.getImages()
				.add("http://guantravel.com/resources/media/201501/20027102e4c82d04d2d86a2e7fa978d6.jpg");
		
			

		// 费用包含
		p.setFeeInclude("* 7x24小时优秀中/英双语自驾向导服务；<br/>"
+"* 国内往返美国经济舱机票、燃油附加税、机票税；<br/>"
+"* 包含好莱坞环球影城+美国大峡谷+17英里海岸门票；<br/>"
+"* 城市繁华区4钻品质酒店，小镇3钻酒店住宿；<br/>"
+"* 免收每人每天8美元的导游小费；<br/>"
+"* 汽车租赁费及随车保险（车损或盗抢不计免赔，第三者100万美金+个人意外伤害保险+各种税费）；<br/>"
+"* 全程燃油/对讲机/中文导航；<br/>"
+"* 中国驾照英文翻译件；<br/>"
+"* 英国太阳联合海外无忧个人旅游保险（高达100万的意外险），详情见保单。");

		// 费用不包含
		p.setFeeNotInclude("* 护照、签证等相关证件的费用；<br/>"
+"* 超出机票包含免费行李的行李托运费；<br/>"
+"* 全程餐食自理（多数酒店早餐包含，午、晚餐走到哪吃到哪儿，丰简由己，午餐约4-8美金，晚餐约10美金+）；<br/>"
+"* 停车费、高速通行费及违章罚款等费用请自理（若产生）；<br/>"
+"* 行程中自由活动时的产生的一切费用请自理；<br/>"
+"* 行程以外的一切私人消费，如酒店内的洗衣费、电话费、饮料、付费电视、行李搬运费以及吸烟罚款等费用，个人消费所引起的小费、出入境的行李海关课税、超重行李的托运费、管理费等；<br/>"
+"* 不可抗拒的客观原因（如天灾、战争、罢工等）或航空公司航班延误、取消、领馆签证延误等非我公司原因引起的特殊情况，我公司有权变更或取消行程，由此产生的相关费用（如在外延期签证、食、宿、行一切费用）请自理。");

		// 费用说明
		p.setFeeIntro("本产品报价根据12人成团核算，如未达到成团人数，而实际报名人数已经达到7人，如全体旅游者同意，亦可成团，需按7人价格补缴3000元/人。或者本社经征得旅游者书面同意后，可以延期出团或者改签其他线路出团，因此增加的费用由旅游者承担，减少的费用出境社予以退还。");

		// 线路特色
		p.setFeature("★ 全程专业自驾向导7x24小时陪同，无超时费用，省心省力、安全无忧！<br/>"
+"★ 包含好莱坞环球影城+大峡谷+17英里海岸门票！<br/>"
+"★ 城市繁华区4钻品质酒店（小镇3钻）！<br/>"
+"★ 轻松纯玩自驾小团，无旅游商店购物或额外收费项目！<br/>"
+"★ 巨无霸越野车+燃油+豪华保险套餐+导航全部包含！<br/>"
+"★ 加州一号公路穿越+大峡谷深度探秘之旅！");

		// 产品详情
		p.setDetail("如需我们代办签证，费用为人民币1250元/人（含签证费及使馆面谈辅导、翻译、填表、快递服务等费用，以上费用为美国使馆收取和旅行社实际支出，拒签恕不退还）。咨询电话：4006-910-800.");
		p.setAssembly("<br/>北京出发:航班起飞3.5小时前到达北京首都机场T3航站4层出发大厅6号门内中国海关处。<br/>"
		+"上海出发：航班起飞3.5小时前到达浦东国际机场出发大厅。");
		// 预订说明
		p.setBookNotice("预订说明");

		// 行程安排
		List<Journey> journey = new ArrayList<Journey>();
		Journey j = new Journey();
		// 基本信息
		j.setDay("第一天");
		j.setTitle("北京/上海—旧金山");

		// 交通信息
		List<Traffic> traffic = new ArrayList<Traffic>();
		Traffic t1 = new Traffic();
		
		t1.setVehicle("第1段 飞机");
		t1.setDetail("国际航班	北京-旧金山（次日到达）	座舱：经济舱");
		traffic.add(t1);
		
		Traffic t2 = new Traffic();
		t2.setVehicle("第1段 飞机");
		t2.setDetail("国际航班	上海-旧金山（次日到达）	座舱：经济舱");
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
		 
		d.add(new DetailContext("text", "搭乘国际航班飞往西部名城-旧金山，抵达后，提车前往市区游览九曲花街、唐人街、联合广场等主要景点，晚上渔人码头逛街，吃海鲜。<br/>"
		+"【行程亮点】旧金山，又称三藩市,位于美国加州西海岸。它三面环水，环境优美，是一座山城。旧金山气候冬暖夏凉，阳光充足，被誉为“最受美国人欢迎的城市”。同时它还是一个崇尚“多元化”的城市。在这里，白人、黑人、黄种人和谐共处；同性恋者泰然自若地在屋顶插上彩虹旗，与异性恋者比邻而居；在这里，你可以看到专以造型取胜的街头艺人，有的把全身漆成五彩斑斓，有的扮成巫婆、小丑甚至黑色幽灵；在这里，你可以看到头顶红绿头发的年轻人招摇过市，公然在街头拥吻的同性恋情侣。在这片土地上，任何的标新立异都不会招来旁人的侧目，每个人都是特立独行的楷模。"));
		d.add(new
		 DetailContext("img","https://imgs.qunarzz.com/vc/ed/cf/1a/a1c23af404abbe407b1ea42108.jpg_92.jpg"));
		 d.add(new
		 DetailContext("img","https://imgs.qunarzz.com/vc/76/e9/60/e0bf49cad6f54ce81d1042b14a.jpg_92.jpg"));
		j.setDetail(d);
		// 结束第一天的数据
		journey.add(j);
		
		

		j = new Journey();
		// 基本信息
		j.setDay("第二天");
		j.setTitle("旧金山-蒙特利（180公里，2小时左右车程）");
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
				"text",
				"上午参观旧金山标志-金门大桥、双峰山鸟瞰旧金山全景。下午前往蒙特利半岛著名的17英里海岸游览（含门票）。<br/>"
		+"【行程亮点】这里是美国西海岸风光的精华-碧海蓝天、鲜花礁石、随处可见松鼠和海豹、悬崖峭壁、古老的的松柏，构成了17英里迷人的画卷。海岸线上最为漂亮的地方叫圆石滩。它被称为“世界上海洋和陆地的最佳连接处”，是一个美得令人窒息的地方。夜宿蒙特利附近。"));
	d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/49/38/91/d3a790e84d8c1221c2455a176b.jpg_92.jpg"));
				d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/f7/30/f9/6bd005893f39671efbd8e65016.jpg_92.jpg"));
		j.setDetail(d);
		// 结束第二天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第三天");
		j.setTitle("蒙特利-文图拉（435公里，6小时左右车程）");
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
				"text",
				"开始令人兴奋的加州一号公路之旅。途经卡梅尔、大苏尔（国家地理杂志推荐人生必去的50个地方之一）等地游览，感受真正的加利福尼亚。傍晚入住文图拉。<br/>"
		+"【行程亮点】加州一号公路从北至南连接着旧金山与洛杉矶，沿着美国西海岸蜿蜒前进，全长超过1000公里。由于得天独厚的地理环境，它一边是海阔天空惊涛拍岸，风帆点点碧波万顷；一边是陡峭悬崖群峦迭翠，牧草如茵牛马成群。"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/3e/c9/7d/3bd9e19682e0cf7ea7b1626071.jpg_92.jpg"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/13/9b/a8/2d541d79be4d4637a14d100a43.jpg_92.jpg"));
		j.setDetail(d);
		// 结束第三天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第四天");
		j.setTitle("文图拉-拉斯维加斯（500公里，5小时左右车程）");
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
				"text",
				" 驱车前往沙漠上的海市蜃楼-拉斯维加斯。晚上拉斯维加斯大道夜游，体验世界赌城各种极尽奢华的酒店赌场。（包含夜游）<br/>"
		+"【行程亮点】在这个沙漠环绕的地方，所有的注意力都集中在热闹非凡的拉斯维加斯大道上。据说世界上十家最大的度假酒店就有九家是在这里。投资商花不惜花费数十亿美元的巨资，打造大批奢华的、标新立异的主题酒店，每家酒店都有自己的赌场、各种高档餐厅以及购物中心，游客可以免费进入参观。每当夜幕降临，拉斯维加斯才会迎来它一天中最辉煌的时刻。除了赌场以外，很多酒店还有丰富多彩的剧场表演秀：歌舞、脱口秀、杂技、催眠、魔术，精彩的你都不知道该看那个。"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/bd/b5/f9/907d2d2faec5e2e402d86f74df.jpg_92.jpg"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/7c/91/59/2433ff0a26d054d9d0c44189dd.jpg_92.jpg"));
		j.setDetail(d);
		// 结束第四天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第五天");
		j.setTitle("拉斯维加斯");
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
				"text",
				"驱车前往世界七大自然奇迹之一的美国大峡谷(西峡)。抵达后参观鹰峡、登顶GUANO POINT 360 度感受大自然的鬼斧神工及在HUALAPAI马场感受西部牛仔的热情!<br/>"
		+"【行程亮点】大峡谷是一处举世闻名的自然奇观，位于亚利桑那州西北部的凯巴布高原上。它全长446公里，平均宽度16公里，最大深度1740米，是联合国教科文组织选为受保护的世界天然遗产之一。其雄伟的地貌、浩瀚的气魄、慑人的神态、奇突的景色，世无其匹！美国总统西奥多-罗斯福来此游览时，感叹地说：“大峡谷使我充满了敬畏，它无可比拟，无法形容，在这辽阔的世界上，绝无仅有。”有人说，在太空唯一可用肉眼看到的自然景观就是大峡谷。"));
		d.add(new DetailContext(
				"img",
				"http://img1.qunarzz.com/vc/96/33/a9/0b339e94f0ba81b4910183c9e9.jpg_92.jpg"));
		d.add(new DetailContext(
				"img",
				"http://img1.qunarzz.com/vc/4b/9d/83/58e2e2f11537e0a99ffa646643.jpg_92.jpg"));
		j.setDetail(d);
		// 结束第五天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第六天");
		j.setTitle("拉斯维加斯-洛杉矶（440公里，车程4.5小时）");
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
				"text",
				"前往天使之城洛杉矶，抵达后自由活动。<br/>"
		+"【行程亮点】洛杉矶是加州第一大城市，美国第二大城市，仅次于纽约。同时它也是全世界的文化、科技、国际贸易和高等教育中心之一。洛杉矶一望无垠的沙滩和明媚的阳光、闻名遐迩的“电影王国”好莱坞、引人入胜的迪斯尼乐园、峰秀地灵的贝佛利山庄使洛杉矶成为一座举世闻名的“电影城”和“旅游城”。洛杉矶市区广阔，布局分散，整座城市是以千千万万栋一家一户的小住宅为基础。绿荫丛中，鳞次栉比的庭院式建筑，色彩淡雅，造型精巧，风格各异，遍布于平地山丘上。"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/e4/73/ee/44764e12344b6a44f931cc23df.jpg_92.jpg"));
		
		j.setDetail(d);
		// 结束第六天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第七天");
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
				"text",
				"全天在环球影城游玩（门票包含）。晚上参观星光大道、中国剧院、奥斯卡金像奖颁奖礼的举行地点-杜比剧院（外观）等好莱坞景点，追寻明星的印记。"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/b6/94/de/1308f254441549587f6eaad68c.jpg_92.jpg"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/42/c1/ac/0231d1e78e5b35ac980336d3e0.jpg_92.jpg"));
		j.setDetail(d);
		// 结束第七天的数据
		journey.add(j);

		j = new Journey();
		// 基本信息
		j.setDay("第八天");
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
				"text",
				"全天在美国西部最大的奥特莱斯Desert Hills Premium Outlets全天自由购物。与其它奥特莱斯相比，这里的国际大品牌更多，包括Bottega Veneta、Burberry、D&G、Ferregamo、Gucci、Prada、Versace、JimmyChoo、Tod'等等，一共有180多个品牌可供选择。"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/ce/46/35/a6eea1b6e181cad1fe1009fafb.png_92.png"));
		d.add(new DetailContext(
				"img",
				"https://imgs.qunarzz.com/vc/45/08/11/4b24767bbd7d5226fd0a746bc3.png_92.png"));
		j.setDetail(d);
		// 结束第八天的数据
		journey.add(j);

		
		
		

		j = new Journey();
		// 基本信息
		j.setDay("第九、十天");
		j.setTitle("洛杉矶-北京/上海");
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
		//d.add(new DetailContext(
		//		"img",
		//		"http://xdujia.com/resources/media/201408/ecdb15f4730506a03531544bba130682.jpg"));
		d.add(new DetailContext(
				"text",
				"搭乘航班回国。"));
		j.setDetail(d);
		// 结束第十一天的数据
		journey.add(j);
		
		

			

		
		
		// 设置所有的行程安排
		p.setJourney(journey);

		p.Add();

	}
}
