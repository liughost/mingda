package com.mdnet.travel.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.zhinet.travel.pojo.basepojo.TicketInfo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mdnet.travel.core.dao.ICityDefDAO;
import com.mdnet.travel.core.dao.IProductCityLinkDAO;
import com.mdnet.travel.core.dao.IProductInfoDAO;
import com.mdnet.travel.core.dao.IProductTypeDAO;
import com.mdnet.travel.core.dao.ITourCommentsDAO;
import com.mdnet.travel.core.dao.ITourDAO;
import com.mdnet.travel.core.dao.ITourItemsDAO;
import com.mdnet.travel.core.dao.ITourOrderDAO;
import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.ProductCityLink;
import com.mdnet.travel.core.model.ProductType;
import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.core.model.TourComments;
import com.mdnet.travel.core.model.TourItems;
import com.mdnet.travel.core.model.TourOrder;
import com.mdnet.travel.core.service.ILeaderService;
import com.mdnet.travel.core.service.ProxyService;
import com.mdnet.travel.core.vo.ShowProductInfo;
import com.mdnet.travel.order.model.ProductInfo;

@Service(ILeaderService.SERVICE_NAME)
@Scope("prototype")
@Transactional(readOnly = false)
public class LeaderServiceImpl implements ILeaderService {

	@Resource(name = ITourDAO.DAO_NAME)
	protected ITourDAO tourDAO;
	@Resource(name = ITourItemsDAO.DAO_NAME)
	protected ITourItemsDAO tourItemsDAO;
	@Resource(name = ITourOrderDAO.DAO_NAME)
	protected ITourOrderDAO tourOrderDAO;
	@Resource(name = ITourCommentsDAO.DAO_NAME)
	protected ITourCommentsDAO tourCommentsDAO;
	@Resource(name = ICityDefDAO.DAO_NAME)
	protected ICityDefDAO cityDefDAO;
	@Resource(name = IProductInfoDAO.DAO_NAME)
	protected IProductInfoDAO productDAO;
	@Resource(name = IProductCityLinkDAO.DAO_NAME)
	protected IProductCityLinkDAO productCityLinkDAO;

	@Resource(name = IProductTypeDAO.DAO_NAME)
	protected IProductTypeDAO productTypeDAO;

	@Override
	public ShowProductInfo[] getProductList(ProxyService proxyService,
			int pageNo) {

		TicketInfo[] ti = proxyService.getTicketsByCatalog(23, pageNo);
		ShowProductInfo[] ps = null;
		if (ti != null) {
			ps = new ShowProductInfo[ti.length];
			for (int i = 0; i < ti.length; i++) {
				ps[i] = new ShowProductInfo();
				ps[i].setName(ti[i].getTicketName());
				ps[i].setImg(ti[i].getImgUrl());
				ps[i].setMediaID(Integer.parseInt(ti[i].getTicketMemo()));
				ps[i].setProductID(ti[i].getTicketId());
			}
		}
		/*
		 * ShowProductInfo[] ps = new ShowProductInfo[4]; ps[0] = new
		 * ShowProductInfo(); ps[0].setName("美国西部大环线野之旅"); ps[0].setImg(
		 * "http://xdujia.com/resources/media/201408/0138b0e776f1c74f71b9e8d24c576408.jpg"
		 * ); ps[0].setMediaID(116); ps[0].setProductID(0);
		 * 
		 * ps[1] = new ShowProductInfo(); ps[1].setName("夏威夷7日精彩之旅");
		 * ps[1].setImg(
		 * "http://xdujia.com/resources/media/201408/712a924eabc77f4eef3271c6df766a7a.jpg"
		 * ); ps[1].setProductID(0); ps[1].setMediaID(117);
		 * 
		 * ps[2] = new ShowProductInfo(); ps[2].setName("黄石国家公园探秘之旅");
		 * ps[2].setImg(
		 * "http://xdujia.com/resources/media/201408/a003339ae5bec34ef45924e74c09ead7.jpg"
		 * ); ps[2].setMediaID(118); ps[2].setProductID(0);
		 * 
		 * ps[3] = new ShowProductInfo(); ps[3].setName("美国西海岸、夏威夷12日浪漫之旅");
		 * ps[3].setImg(
		 * "http://xdujia.com/resources/media/201408/e99253109f1fc2467d783b2263eaeeca.jpg"
		 * ); ps[3].setMediaID(119); ps[3].setProductID(0);
		 */
		return ps;
	}

	@Override
	public Tour findTour(int custId) {
		return this.tourDAO.findByCustId(custId);
	}

	@Override
	public TourItems findTourItems(int custId) {
		return this.tourItemsDAO.findByCustId(custId);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void saveTourOrder(TourOrder to) {
		this.tourOrderDAO.save(to);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public int addComments(String remoteHost, int custId, String nick,
			String context) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		TourComments entity = new TourComments();
		entity.setApproveCount(0);
		entity.setCommentDate(sdf.format(new Date()));
		entity.setCommentIP(remoteHost);
		entity.setContext(context);
		entity.setCriticizeCount(0);
		entity.setCustId(custId);
		entity.setNickName(nick);

		this.tourCommentsDAO.save(entity);
		if (entity.getId() > 0) {
			Tour t = this.tourDAO.findByCustId(custId);
			t.setCommentCount(t.getCommentCount() + 1);
			this.tourDAO.update(t);
		}
		return entity.getId();
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public int approve(int commentId) {
		TourComments entity = this.tourCommentsDAO.get(commentId);
		if (entity != null) {
			entity.setApproveCount(entity.getApproveCount() + 1);
			this.tourCommentsDAO.update(entity);
			return entity.getApproveCount();
		} else
			return -1;
	}

	@Override
	public List<TourComments> findComments(int custId, int pageNo) {
		return this.tourCommentsDAO.findByCustId(custId, pageNo);
	}

	@Override
	public List<CityDef> findCityByPid(int pid) {
		// String cids ="";
		// List<ProductCityLink> cityLinks =
		// this.productCityLinkDAO.findByPid(pid);
		// for(ProductCityLink l:cityLinks ){
		// cids+=l.getProductId()+",";
		// }
		// return this.cityDefDAO.findProductCity(cids);
		return this.cityDefDAO.findCityBypid(pid);
	}

	@Override
	public List<CityDef> findCities() {
		return this.cityDefDAO.findAll();
	}

	@Override
	public List<ProductType> findTypes() {
		return this.productTypeDAO.findAll();
	}

	@Override
	public List<ProductType> findTypesByPid(int productID) {
		return this.productTypeDAO.findByPid(productID);
	}

	@Override
	public List<Tour> findTourByPid(int productID) {
		return this.tourDAO.findByPid(productID);
	}

	@Override
	public List<CityDef> findCitiesByName(String cities) {
		String[] cs = cities.split(",");
		String where = "";
		for (String cn : cs) {
			where += "'" + cn + "',";
		}
		return this.cityDefDAO.findCity(where.substring(0, where.length() - 1));
	}

	@Override
	public List<ProductType> findTypesByName(String pType) {
		String[] ps = pType.split(",");
		String where = "";
		for (String pn : ps) {
			where += "'" + pn + "',";
		}
		return this.productTypeDAO.findByName(where.substring(0,
				where.length() - 1));
	}

	@Override
	public List<Tour> findTour(String cities, String startDate) {
		List<Tour> ts = new ArrayList<Tour>();
		if ((cities == null || cities.length() == 0)
				&& (startDate == null || startDate.length() == 0)) {
			// 默认值
			ts = this.tourDAO.findAll();
		} else {
			// 根据日期查产品
			String where = "";
			// String gWhere = "";
			// if (startDate != null && startDate.length() > 0) {
			// String query =
			// "from ProductInfo p, GroupDate g where g.productId=p.id and substring(g.startDate, 1, 7) = '"
			// + startDate + "'";
			// List<ProductInfo> pp = this.productDAO.findSQL(query, 0, 20);
			// if (pp == null && pp.size() == 0) {
			// return new ArrayList<Tour>();
			// }
			// String gp = "";
			// for (ProductInfo p : pp) {
			// gp += p.getId() + ",";
			// }
			// }
			// 根据城市查找导游，城市-产品-导游
			List<CityDef> cs = this.findCitiesByName(cities);

			if (cs == null || cs.size() == 0) {
				return new ArrayList<Tour>();
			}
			for (CityDef c : cs) {
				where += c.getId() + ",";
			}

			String query = "from Tour t, ProductInfo p, ProductTourLink tl, ProductCityLink cl, GroupDate g where ";
			query += " g.productId=p.id ";
			if (startDate != null && startDate.length() > 0) {// 城市和日期不能同时为空，日期可以是空
				query += " and substring(g.startDate, 1, 7) = '" + startDate
						+ "'";
			}
			query += " and cl.cityId in ("
					+ where.substring(0, where.length() - 1)
					+ ") and p.id=cl.productId and p.id = tl.productId and t.custId=tl.tourId ";
			ts = this.tourDAO.findSQL(query + "group by t.id ", 0, 50);
		}
		// 找出产品对应的城市
		for (Tour t : ts) {
			List<CityDef> cs = this.cityDefDAO
					.findSQL("from CityDef c, ProductCityLink cl, ProductInfo p, ProductTourLink tl where tl.productId=p.id and cl.productId=p.id and cl.cityId=c.id and tl.tourId="
							+ t.getCustId() + " group by c.id");
			for (CityDef c : cs) {
				t.setCities((t.getCities() == null ? "" : t.getCities())
						+ c.getCityName() + ";");
			}
		}
		return ts;

	}
}
