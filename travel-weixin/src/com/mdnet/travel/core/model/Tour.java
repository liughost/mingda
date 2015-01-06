package com.mdnet.travel.core.model;

public class Tour extends BaseModel {
	private static final long serialVersionUID = 61L;

	// 标识属性
	private Integer id;
	// 发送方账号(OpenID)

	private String headImg;// 如果是领队则是领队的头像
	private String nickName;// 领队的昵称
	private int gender;// 性别，0：保密，1：男性；2：女性
	private String introduce;// 个人简介
	private String location;// 所在地
	private int custId;// 关联到客户表（customer_table）中的id字段
	private String introImg;// 领队介绍的大图，height: 380px;width: 1140px;
	private int serviceLevel;// 服务水平，星级，从0-5级
	private String signFooter;// 页脚签名
	private String language;
	private int unitPrice;
	private String weixinId;// 微信号
	private String QQ;// QQ号
	private String EMail;// 邮箱
	private String Mobile;// 移动电话
	private String weixinQR;//二维码
	private String feeDesc;
	private String feeExcept;
	private String supplement;
	private int commentCount;
	private String cities;
	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getSupplement() {
		return supplement;
	}

	public void setSupplement(String supplement) {
		this.supplement = supplement;
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

	public String getWeixinQR() {
		return weixinQR;
	}

	public void setWeixinQR(String weixinQR) {
		this.weixinQR = weixinQR;
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
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getIntroImg() {
		return introImg;
	}

	public void setIntroImg(String introImg) {
		this.introImg = introImg;
	}

	public int getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(int serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getSignFooter() {
		return signFooter;
	}

	public void setSignFooter(String signFooter) {
		this.signFooter = signFooter;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
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

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
