package com.mdnet.travel.core.dao;

import java.util.List;

import com.mdnet.travel.core.model.CityDef;

public interface ICityDefDAO extends BasicDAO<CityDef>{
	public final static String DAO_NAME = "com.mdnet.travel.core.dao.CityDefDAOImpl";
	List<CityDef> findProductCity(String cids);
	List<CityDef> findCityBypid(int pid);
	List<CityDef> findCity(String cName);
	List<CityDef> findSQL(String string);
}
