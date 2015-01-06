package com.mdnet.travel.core.service;

import java.util.List;

import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.ProductType;
import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.core.model.TourComments;
import com.mdnet.travel.core.model.TourItems;
import com.mdnet.travel.core.model.TourOrder;
import com.mdnet.travel.core.vo.ShowProductInfo;
import com.mdnet.travel.order.model.ProductInfo;

public interface ILeaderService {
	public final static String SERVICE_NAME = "com.mdnet.travel.core.service.impl.LeaderServiceImpl";

	ShowProductInfo[] getProductList(ProxyService proxyService, int pageNo);

	Tour findTour(int custId);

	TourItems findTourItems(int custId);

	void saveTourOrder(TourOrder to);

	int addComments(String remoteHost, int custId, String nick, String context);

	int approve(int commentId);

	List<TourComments> findComments(int custId, int pageNo);

	List<CityDef> findCityByPid(int pid);

	List<CityDef> findCities();

	List<ProductType> findTypes();

	List<ProductType> findTypesByPid(int productID);

	List<Tour> findTourByPid(int productID);

	List<CityDef> findCitiesByName(String cities);

	List<ProductType> findTypesByName(String pType);

	List<Tour> findTour(String cities, String startDate);
	
}
