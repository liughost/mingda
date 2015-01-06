package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.TourComments;

public interface ITourCommentsDAO extends BasicDAO<TourComments>{
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.TourCommentsDAOImpl";
	List<TourComments> findByCustId(int custId, int pageNo);
}
