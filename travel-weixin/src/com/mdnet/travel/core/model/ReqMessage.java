package com.mdnet.travel.core.model;

public class ReqMessage extends BaseModel {
	private static final long serialVersionUID = 464L;
	private Integer messageId;
	// 消息创建的时间
	private String createTime;
	// 消息的类型(text/image/voice/video/location)
	private String msgType;
	// 文本消息内容(主要是为了分析用户行为)
	private String content;

	// 哪个账号发过来的消息
	private Customer customer;
	
	//消息状态，1：制作中，0：一般显示，当某帐号标记为制作中时，设置为1，结束后设置为0参与共享消息
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ReqMessage() {
	}

	public ReqMessage(String createTime, String msgType, String content) {
		super();
		this.createTime = createTime;
		this.msgType = msgType;
		this.content = content;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
