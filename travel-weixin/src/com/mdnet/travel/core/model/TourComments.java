package com.mdnet.travel.core.model;

public class TourComments extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int custId;// 关联到客户表（customer_table）中的id字段
	private String commentDate;// 评论日期
	private String commentIP;// 评论者IP地址
	public String getCommentIP() {
		return commentIP;
	}
	public void setCommentIP(String commentIP) {
		this.commentIP = commentIP;
	}

	private String nickName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	private String context;// 评论内容
	private int approveCount;// 点赞的数量
	private int criticizeCount;// 批评数量
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
	public int getApproveCount() {
		return approveCount;
	}
	public void setApproveCount(int approveCount) {
		this.approveCount = approveCount;
	}
	public int getCriticizeCount() {
		return criticizeCount;
	}
	public void setCriticizeCount(int criticizeCount) {
		this.criticizeCount = criticizeCount;
	}
}
