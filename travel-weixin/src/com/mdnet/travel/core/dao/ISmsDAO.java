package com.mdnet.travel.core.dao;

import java.util.List;

import net.zhinet.travel.pojo.basepojo.SendSMS;

public interface ISmsDAO extends BasicDAO<SendSMS> {
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.SmsDAOImpl";
	
	public List<SendSMS> findSms(String query, int pageNo, int pageCount);
	public int changeState(int id, int state);
}
