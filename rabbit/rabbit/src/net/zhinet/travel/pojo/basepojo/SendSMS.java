package net.zhinet.travel.pojo.basepojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendSMS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String destNumber;// 短信目的号码
	private String content;// <comment>短信发送内容</comment>
	private int smsType;// <comment>短信应用类型,0:客户端；1：分销商；2：供应商；3：客户订单</comment>
	private int smsState;// <comment>短信状态:0:请求发送；1：已经发送</comment>
	private String sendTime;// <comment>发送时间</comment>
	private String makeTime;// <comment>创建时间</comment>
	public SendSMS()
	{
		sendTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.makeTime = sdf.format(new Date());
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDestNumber() {
		return destNumber;
	}
	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSmsType() {
		return smsType;
	}
	public void setSmsType(int smsType) {
		this.smsType = smsType;
	}
	public int getSmsState() {
		return smsState;
	}
	public void setSmsState(int smsState) {
		this.smsState = smsState;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMakeTime() {
		return makeTime;
	}
	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}

}
