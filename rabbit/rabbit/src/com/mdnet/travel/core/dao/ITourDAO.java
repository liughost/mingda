package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.Tour;

public interface ITourDAO extends BasicDAO<Tour>{
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.TourDAOImpl";
	Tour findByCustId(int custId);
	List<Tour> findAll(String where, String orderField, int page,
			int pageCount);
	List<Tour> findByPid(int productID);
	List<Tour> findSQL(String query, int i, int j);
}
