package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.Attachment;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.springframework.http.MediaType;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class TourInfo extends MyBaseDocument {
	// protected static TourInfo _tourInfo = null;

	// 图片
	protected String headImg;// 头像图片名称
	protected String introImg;// 领队介绍的大图;
	// 基本信息
	protected String nickName;// 领队的昵称
	protected int gender;// 性别，0：保密，1：男性；2：女性
	protected String location;// 所在地，常驻城市
	protected int serviceLevel;// 服务水平，星级，从0-5级
	protected String serviceCities;// 服务的城市
	protected String scenic;// 服务景点
	protected int planning;// 规划师，0不是；1是
	protected String carType;// 车型描述，空为不带车，例如，宝马SUV7座

	protected String introduce;// 个人简介
	protected LangCapability language = new LangCapability();// 语言能力
	protected String career;// 职业

	// 报价
	protected int unitPrice;// 基础报价
	protected List<TourPriceCanlendar> price = new ArrayList<TourPriceCanlendar>();

	// 联系方式
	protected String weixinId;// 微信号
	protected String QQ;// QQ号
	protected String EMail;// 邮箱
	protected String mobile;// 移动电话,手机号码作为唯一的标识
	protected String weixinQR;// 二维码

	// 服务说明
	protected String feeDesc;// 费用包含
	protected String feeExcept;// 费用不包含
	protected String supplement;// 其他说明

	// 管理字段
	protected boolean commend = false;// 是否推荐，true为推荐
	protected boolean online = false;// 是否上线，true为上线，其他为草稿

	public boolean isCommend() {
		return commend;
	}

	public void setCommend(boolean commend) {
		this.commend = commend;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	protected List<String> serviceItem = new ArrayList<String>();

	public TourInfo() {

	}

	public TourInfo(Database db) {
		this.db = db;
	}

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

	// public TourInfo() {
	// db = new Database("guantravel.com", 5984, "tours");
	// db = new Database("deanx.cn", 5984, "mydatabase");
	// AuthScope authScope = new AuthScope("guantravel.com", 5984);
	// Credentials credentials = new UsernamePasswordCredentials("mingda",
	// "mingdaNET");
	// db.getServer().setCredentials(authScope, credentials);
	// className = this.getClass().getName();
	// }

	/*
	 * public static TourInfo instance() { if (TourInfo._tourInfo == null)
	 * TourInfo._tourInfo = new TourInfo(); return TourInfo._tourInfo; }
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TourInfo Unseriable(ValueRow<Map> result) {
		this.price.clear();
		this.serviceItem.clear();

		this.setId((String) result.getValue().get("_id"));
		this.setRevision((String) result.getValue().get("_rev"));
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
			} else if (field.getType() == boolean.class) {
				try {
					field.set(this, Boolean.parseBoolean(value));
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

	public void test() {
		// http://deanx.cn:5984/?mydatabase/_design/export/list/all/all?asd=333
		Object ret = db.queryView("export/list/all/all?asd=333", Map.class,
				null, null);
		System.out.println(ret.toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TourInfo Unseriable(Map result) {
		this.price.clear();
		this.serviceItem.clear();

		this.setId((String) result.get("_id"));
		this.setRevision((String) result.get("_rev"));
		Field[] fields = this.getClass().getDeclaredFields();// 反射类字段
		for (Field field : fields) {
			String vName = field.getName();
			if (result.get(vName) == null)
				continue;
			String value = result.get(vName).toString();
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
			} else if (field.getType() == boolean.class) {
				try {
					field.set(this, Boolean.parseBoolean(value));
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
				Map lang = (Map) result.get(vName);
				this.setLanguage(LangCapability.Unseriable(lang));

			} else if (field.getType() == List.class
					&& vName.compareTo("price") == 0) {

				List<Map> ps = (List<Map>) result.get(vName);
				for (Map j : ps) {
					TourPriceCanlendar p = TourPriceCanlendar.Unseriable(j);
					this.price.add(p);
				}
			} else if (field.getType() == List.class
					&& vName.compareTo("serviceItem") == 0) {
				// String img = (String) ;
				this.serviceItem = (List<String>) result.get(vName);

			}

		}
		return this;
	}

	public int Add() {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.mobile != null && doc.mobile == "
				+ this.mobile + "){emit(doc._rev, null);}}\"}";
		@SuppressWarnings("rawtypes")
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
			// db.updateDocument(this);

			updateTour(id);
		}
		return 0;
	}

	public void updateTour(String id) {
		// 获取原来微信
		try {
			byte[] bb = this.takeDbObj().getAttachment(id, "weixinQR.jpg");
			if (bb != null) {
				Attachment att0 = new Attachment(MediaType.IMAGE_JPEG_VALUE, bb);
				this.addAttachment("weixinQR.jpg", att0);
			}
		} catch (Exception e) {
		}
		// 获取原来宣传照
		try {
			byte[] bb = this.takeDbObj().getAttachment(id, "intro.jpg");
			if (bb != null) {
				Attachment att0 = new Attachment(MediaType.IMAGE_JPEG_VALUE, bb);
				this.addAttachment("intro.jpg", att0);
			}
		} catch (Exception e) {
		}
		// 获取原来头像
		try {
			byte[] bb = this.takeDbObj().getAttachment(id, "head.jpg");
			if (bb != null) {
				Attachment att0 = new Attachment(MediaType.IMAGE_JPEG_VALUE, bb);
				this.addAttachment("head.jpg", att0);
			}
		} catch (Exception e) {
		}
		this.updateAttachment();
	}

	public void updateAttachment() {
		db.updateDocument(this);
	}

	public void chageOnlineState(boolean b) {
		this.setOnline(b);
		this.updateTour(this.getId());
	}

}
