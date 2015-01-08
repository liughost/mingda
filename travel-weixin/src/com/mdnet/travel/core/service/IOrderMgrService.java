package com.mdnet.travel.core.service;

import java.util.List;

import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.vo.ShowProductInfo;
import com.mdnet.travel.order.model.OrderInfo;
import com.mdnet.travel.order.model.ProductDetail;
import com.mdnet.travel.order.model.ProductPriceLine;

public interface IOrderMgrService {
	public final static String SERVICE_NAME = "com.mdnet.travel.order.service.impl.OrderMgrServiceImpl";

	ShowProductInfo[] getProductList(int productType, int page);

	ProductDetail getProductInfo(int pid, String startDate);

	String saveOrder(String orderId, int productId, String productName,
			int salePrice, int childrenPrice, int offPrice, String userName, String userMobile, int totalCount,
			int childrenCount, int oldCount, int adultCount,
			int childrenBedsCount, String startDate, String bookerName,
			String bookerPhone, String channelSource, int payMoney);

	OrderInfo getOrders(String orderId);

	List<OrderInfo> getOrders(int sType, String context, int pageNo);

	void updateOrder(OrderInfo oi);

	List<ProductDetail> getProductInfoByCity(String cids);

	List<ShowProductInfo> ProductList(String query, int page, int pageCount);

	List<ProductPriceLine> getProductByPrice(String fee);

	GroupDate getGroupInfo(int productId, String selGroupDate);
}
