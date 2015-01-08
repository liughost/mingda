package com.mdnet.travel.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdnet.travel.core.dao.GroupDateDAO;
import com.mdnet.travel.core.dao.IOrderInfoDAO;
import com.mdnet.travel.core.dao.IProductInfoDAO;
import com.mdnet.travel.core.dao.PriceDAO;
import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.dao.ReqMessageDAO;
import com.mdnet.travel.core.dao.SpecialDAO;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.model.Special;
import com.mdnet.travel.core.service.IOrderMgrService;
import com.mdnet.travel.core.vo.ShowProductInfo;
import com.mdnet.travel.order.model.OrderInfo;
import com.mdnet.travel.order.model.ProductDetail;
import com.mdnet.travel.order.model.ProductInfo;
import com.mdnet.travel.order.model.ProductPriceLine;

@Service(IOrderMgrService.SERVICE_NAME)
@Scope("prototype")
public class OrderMgrServiceImpl implements IOrderMgrService {

	@Resource(name = IOrderInfoDAO.DAO_NAME)
	protected IOrderInfoDAO orderDAO;
	@Resource(name = IProductInfoDAO.DAO_NAME)
	protected IProductInfoDAO productDAO;
	@Resource(name = SpecialDAO.DAO_NAME)
	protected SpecialDAO specialDAO;
	@Resource(name = PriceDAO.DAO_NAME)
	protected PriceDAO priceDAO;
	@Resource(name = GroupDateDAO.DAO_NAME)
	protected GroupDateDAO groupDateDAO;

	@Override
	public ShowProductInfo[] getProductList(int productType, int page) {

		ShowProductInfo[] ps = null;
		List<ProductInfo> pis = this.productDAO.find("where productType="
				+ productType, page);
		if (pis != null)
			ps = new ShowProductInfo[pis.size()];
		for (int i = 0; i < pis.size(); i++) {
			ProductInfo pi = pis.get(i);
			ps[i] = new ShowProductInfo();
			ps[i].setName(pi.getProductName());
			ps[i].setImg(pi.getImgURL());

			// 媒体信息
			Special sp = this.specialDAO.findByPid(pi.getId());
			if (sp != null)
				ps[i].setMediaID(sp.getId());
			else
				ps[i].setMediaID(0);
			ps[i].setProductID(pi.getId());
			// 价格信息
			// List<ProductPriceLine> prices = this.priceDAO.findPrice(
			// "productId=" + pi.getId(), 0);
			// 生成团期最小日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			long d = now.getTime() / 1000 + 30 * 24 * 60 * 60;
			Date minDay = new Date(d * 1000);

			String minDate = sdf.format(minDay);

			String where = " where productId=" + pi.getId()
					+ " and  startDate >'" + minDate + "'";

			List<GroupDate> gs = this.groupDateDAO.getList(where, 0);
			if (gs != null && gs.size() > 0)// 单位：元
				ps[i].setLowPrice(gs.get(0).getAdultPrice());
			ps[i].setDays(String.valueOf(pi.getDays()));
			ps[i].setIntroduce(pi.getIntroduce());

		}
		return ps;
	}

	@Override
	public List<ProductDetail> getProductInfoByCity(String cids) {
		List<ProductDetail> pds = new ArrayList<ProductDetail>();
		List<ProductInfo> pis = this.productDAO.findByCity(cids);
		for (ProductInfo p : pis) {
			ProductDetail pd = new ProductDetail();
			pd.setProductInfo(p);
			// 获取价格信息
			List<ProductPriceLine> prices = this.priceDAO.findPrice(
					"productId=" + p.getId(), 0);
			if (prices != null && prices.size() > 0)
				pd.setPrice(prices.get(0));

			pds.add(pd);
		}
		return pds;
	}

	@Override
	public ProductDetail getProductInfo(int pid, String startDate) {
		ProductDetail pd = new ProductDetail();
		// 获取基本信息
		ProductAllDetail pad = ProductAllDetail.instance().getById(
				String.valueOf(pid));

		// List<ProductInfo> pis = this.productDAO.find("where id=" + pid, 0);
		if (pad != null) {
			ProductInfo pi = new ProductInfo();
			pi.setProductName(pad.getProductName());
			pi.setId(pid);
			pi.setImgURL(pad.getImages().get(0));
			pi.setIncludeCity(pad.getDestCity());
			pi.setProductNo(String.valueOf(pid));
			pi.setIntroduce(pad.getRecommend());
			pd.setProductInfo(pi);
		}
		// 从团期中获取价格信息
		String where = " where productId=" + pid;
		if (startDate != null && startDate.length() > 0)
			where += " and  startDate ='" + startDate + "'";

		List<GroupDate> gs = this.groupDateDAO.getList(where, 0);

		// 获取价格信息
		// List<ProductPriceLine> prices = this.priceDAO.findPrice("productId="
		// + pid, 0);
		// if (prices != null && prices.size() > 0)
		// pd.setPrice(prices.get(0));
		if (gs != null && gs.size() > 0) {
			ProductPriceLine p = new ProductPriceLine();
			p.setLowPrice(gs.get(0).getAdultPrice());
			pd.setPrice(p);
		}
		return pd;
	}

	@Override
	public String saveOrder(String orderId, int productId, String productName,
			int salePrice, int childrenPrice, int offPrice, String userName, String userMobile, int totalCount,
			int childrenCount, int oldCount, int adultCount,
			int childrenBedsCount, String startDate, String bookerName,
			String bookerPhone, String channelSource, int payMoney) {
		boolean isNew = false;
		OrderInfo oi = this.getOrders(orderId);
		if (oi == null) {
			isNew = true;
			oi = new OrderInfo();
			SimpleDateFormat sdf = new SimpleDateFormat("d");
			String ss = UUID.randomUUID().toString();
			int c = ss.hashCode() & 0x7FFFFFFF;
			String hash = String.valueOf(c);

			oi.setOrderId(sdf.format(new Date()) + hash);
		}
		oi.setAmount(totalCount);
		oi.setArriveDate(startDate);
		oi.setBookerName(bookerName);
		oi.setBookerPhone(bookerPhone);
		oi.setChannelSourceId(channelSource);
		oi.setChildrenCount(childrenCount);
		oi.setOldCount(oldCount);
		oi.setAdultCount(adultCount);
		oi.setChildrenBedsCount(childrenBedsCount);
		oi.setConsumeCount(0);
		oi.setCustomerPayMoney(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastConsumeTime = sdf.format(new Date());
		oi.setLastConsumeTime(lastConsumeTime);
		oi.setOrderDate(lastConsumeTime);
		oi.setUserMobile(userMobile);
		oi.setUserName(userName);
		oi.setOrderState(1);
		oi.setPayChannel(0);
		oi.setPayDate("");
		oi.setPayMoney(payMoney);
		oi.setPayOrderId("");
		oi.setProductId(productId);
		oi.setProductName(productName);
		oi.setProductPrice(salePrice);
		oi.setChildrenPrice(childrenPrice);
		oi.setOffPrice(offPrice);

		if (isNew)
			this.orderDAO.save(oi);
		else
			this.orderDAO.update(oi);
		return oi.getOrderId();
	}

	@Override
	public OrderInfo getOrders(String orderId) {
		List<OrderInfo> ois = this.orderDAO.find(" where orderId='" + orderId
				+ "'", 0);
		if (ois != null && ois.size() > 0)
			return ois.get(0);
		else
			return null;
	}

	@Override
	public List<OrderInfo> getOrders(int sType, String context, int pageNo) {
		String where = "";
		if (context != null && context.length() > 0) {
			if (sType == 0)
				where += "where  userName like '%" + context + "%'";
			if (sType == 1)
				where += "where  userMobile like '%" + context + "%'";
			else if (sType == 2)
				where += "where  bookerName like '%" + context + "%'";
		}
		List<OrderInfo> ois = this.orderDAO.find(where,// + " order by id desc",
				pageNo);
		return ois;
	}

	@Override
	public void updateOrder(OrderInfo oi) {
		this.orderDAO.update(oi);
	}

	@Override
	public List<ShowProductInfo> ProductList(String query, int page,
			int pageCount) {
		List<ShowProductInfo> ps = new ArrayList<ShowProductInfo>();
		List<ProductInfo> pis = this.productDAO.findSQL(query, page, pageCount);
		for (int i = 0; i < pis.size(); i++) {
			ShowProductInfo p = new ShowProductInfo();
			ProductInfo pi = pis.get(i);

			p.setName(pi.getProductName());
			p.setImg(pi.getImgURL());

			// 媒体信息
			Special sp = this.specialDAO.findByPid(pi.getId());
			if (sp != null)
				p.setMediaID(sp.getId());
			else
				p.setMediaID(0);
			p.setProductID(pi.getId());
			// 价格信息
			List<ProductPriceLine> prices = this.priceDAO.findPrice(
					"productId=" + pi.getId(), 0);
			if (prices != null && prices.size() > 0)// 单位：元
				p.setLowPrice(prices.get(0).getLowPrice() / 100);
			p.setDays(String.valueOf(pi.getDays()));
			p.setIntroduce(pi.getIntroduce());
			ps.add(p);

		}// end for
		return ps;
	}

	@Override
	public List<ProductPriceLine> getProductByPrice(String fee) {
		if (fee == null && fee.length() == 0)
			return null;
		String where = "";
		int f = Integer.parseInt(fee);
		if (f == 0)// 不限
		{
			where = " lowPrice>=0 ";
		} else if (f == 1)// 1万以下
		{
			where = "  lowPrice<1000000";
		} else if (f == 2)// 1万-2万
		{
			where = " lowPrice>=1000000 and lowPrice<2000000 ";
		} else if (f == 3)// 2万-3万
		{
			where = " lowPrice>=2000000 and lowPrice<3000000 ";
		} else if (f == 5)// 3万-5万
		{
			where = " lowPrice>=3000000 and lowPrice<5000000 ";
		} else if (f == 10)// 5万-10万
		{
			where = " lowPrice>=5000000 and lowPrice<10000000 ";
		} else // 10万上
		{
			where = " lowPrice>10000000";
		}
		return this.priceDAO.findPrice(where, 0);
	}

	@Override
	public GroupDate getGroupInfo(int productId, String selGroupDate) {
		String where = " where productId=" + productId + " and  startDate ='"
				+ selGroupDate + "'";

		List<GroupDate> gs = this.groupDateDAO.getList(where, 0);
		if (gs != null && gs.size() > 0)
			return gs.get(0);
		else
			return null;
	}

}
