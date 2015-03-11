package com.mdnet.travel.core.dao;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

public class TourComments extends MyBaseDocument {

	private String commentDate;// 评论日期
	private String context;// 评论内容
	private String tourKey;// 导游在couch数据库中的key

	// 评论者信息
	// private Commenter commenter;
	private String commentIP;// 评论者IP地址
	private int custId;// 发表关联到客户表（customer_table）中的id字段
	private String nickName;
	private String headImg;

	// private int approveCount;// 点赞的数量
	// private int criticizeCount;// 批评数量

	public TourComments() {

	}

	public TourComments(Database db) {
		this.db = db;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTourKey() {
		return tourKey;
	}

	public void setTourKey(String tourKey) {
		this.tourKey = tourKey;
	}

	public String getCommentIP() {
		return commentIP;
	}

	public void setCommentIP(String commentIP) {
		this.commentIP = commentIP;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public TourComments Unseriable(@SuppressWarnings("rawtypes") ValueRow<Map> result) {
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
			}

		}
		return this;
	}

	public int Add() {

		db.createDocument(this);
		return 0;
	}
}
