package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.TourItems;

public interface ITourItemsDAO extends BasicDAO<TourItems>{
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.TourItemsDAOImpl";

	TourItems findByCustId(int custId);

	List<TourItems> findAll(String where, String orderField, int page,
			int pageCount);

}
