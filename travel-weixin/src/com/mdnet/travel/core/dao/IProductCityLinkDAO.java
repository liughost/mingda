package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.ProductCityLink;

public interface IProductCityLinkDAO extends BasicDAO<ProductCityLink>{
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.ProductCityLinkDAOImpl";

	List<ProductCityLink> findByPid(int pid);
	List<ProductCityLink> findBycity(int cid);
	
}
