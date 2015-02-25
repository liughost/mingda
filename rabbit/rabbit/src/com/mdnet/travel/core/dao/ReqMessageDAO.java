package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.ReqMessage;

public interface ReqMessageDAO extends BasicDAO<ReqMessage> {

	public final static String DAO_NAME = "com.mdnet.travel.core.dao.ReqMessageDAOImpl";

	List<ReqMessage> find(String string, int pageNo, int pageCount);

}
