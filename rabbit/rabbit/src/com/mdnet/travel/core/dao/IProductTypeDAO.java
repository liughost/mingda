package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.ProductType;

public interface IProductTypeDAO extends BasicDAO<ProductType> {
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.ProductTypeDAOImpl";

	List<ProductType> findByPid(int productID);

	List<ProductType> findByName(String pType);

}
