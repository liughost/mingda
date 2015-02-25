package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

import com.mdnet.travel.core.vo.ShowProductInfo;

public class ProductAllDetail extends MyBaseDocument {

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

	private String className = this.getClass().getName();// 类名称
	private String recommend; // 推荐理由

	private String detail;// 产品详情
	private String feature;// 产品特色
	private String assembly;// 集合地点说明
	private List<String> images = new ArrayList<String>();
	private int aheadDays = 10;// 团期显示提前天数

	private String scenics;
	private boolean show;

	public ProductAllDetail(Database couch) {
		this.db = couch;
	}
	public ProductAllDetail() {
	}

	public String getScenics() {
		return scenics;
	}

	public void setScenics(String scenics) {
		this.scenics = scenics;
	}

	public boolean getShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

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
	//protected static ProductAllDetail _product = null;
	//protected Database db;

//	public static ProductAllDetail instance() {
//		if (_product == null)
//			_product = new ProductAllDetail();
//		return _product;
//	}

//	protected ProductAllDetail() {
//		db = new Database("guantravel.com", 5984, "travel");
//		// AuthScope authScope = new AuthScope("guantravel.com", 5984);
//		// Credentials credentials = new UsernamePasswordCredentials("mingda",
//		// "mingdaNET");
//		// db.getServer().setCredentials(authScope, credentials);
//		className = this.getClass().getName();
//	}

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	@SuppressWarnings("rawtypes")
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

	

	

	
}
