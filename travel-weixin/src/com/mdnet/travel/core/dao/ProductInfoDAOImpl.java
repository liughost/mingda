package com.mdnet.travel.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.order.model.ProductDetail;
import com.mdnet.travel.order.model.ProductInfo;

@Repository(IProductInfoDAO.DAO_NAME)
@Scope("prototype")
public class ProductInfoDAOImpl extends BasicDAOImpl<ProductInfo> implements
		IProductInfoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> find(String where, int page) {

		final String queryString = "from ProductInfo " + where
				+ " order by id desc ";
		final int length = 20;
		final int offset = page * length;
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(queryString);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				List list = query.list();
				return list;
			}
		});

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> findByCity(String cids) {
		List<ProductInfo> pis = new ArrayList<ProductInfo>();
		List<Object[]> ps = getHibernateTemplate()
				.find("from ProductInfo p, ProductCityLink l where p.id = l.productId and l.cityId in ("
						+ cids + ") ");
		for (int i = 0; i < ps.size(); i++) {
			for (int j = 0; j < ps.get(i).length; j++) {
				if (ps.get(i)[j].getClass() == ProductInfo.class)
					pis.add((ProductInfo) ps.get(i)[j]);
			}
		}
		return pis;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> findSQL(String query, int page, int pageCount) {
		List<ProductInfo> pis = new ArrayList<ProductInfo>();
		List<Object[]> ps = getHibernateTemplate()
				.find(query);
		for (int i = 0; i < ps.size(); i++) {
			for (int j = 0; j < ps.get(i).length; j++) {
				if (ps.get(i)[j].getClass() == ProductInfo.class)
					pis.add((ProductInfo) ps.get(i)[j]);
			}
		}
		return pis;
	}

}
