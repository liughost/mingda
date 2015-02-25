package com.mdnet.travel.core.model;

public class TourItems extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000L;
	private int custId;// 关联到客户表（customer_table）中的id字段
	private int accompany;// 陪同伴游向导，0：不具备此服务；1：具备此服务
	private int pathPlan;// 形成规划0：不具备此服务；1：具备此服务
	private int helpBook;// 代订服务，0：不具备此服务；1：具备此服务
	public int getHelpBook() {
		return helpBook;
	}

	public void setHelpBook(int helpBook) {
		this.helpBook = helpBook;
	}

	private int camera;// 跟随摄像，0：不具备此服务；1：具备此服务
	private int translate;// 翻译，0：不具备此服务；1：具备此服务
	private int drive;// 驾车，0：不具备此服务；1：具备此服务
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getAccompany() {
		return accompany;
	}

	public void setAccompany(int accompany) {
		this.accompany = accompany;
	}

	public int getPathPlan() {
		return pathPlan;
	}

	public void setPathPlan(int pathPlan) {
		this.pathPlan = pathPlan;
	}

	public int getCamera() {
		return camera;
	}

	public void setCamera(int camera) {
		this.camera = camera;
	}

	public int getTranslate() {
		return translate;
	}

	public void setTranslate(int translate) {
		this.translate = translate;
	}

	public int getDrive() {
		return drive;
	}

	public void setDrive(int drive) {
		this.drive = drive;
	}
}
