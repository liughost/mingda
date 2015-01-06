package com.mdnet.travel.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.order.model.ProductInfo;

@Repository(ITourDAO.DAO_NAME)
@Scope("prototype")
public class TourDAOImpl extends BasicDAOImpl<Tour> implements ITourDAO {
	@Override
	public Tour findByCustId(int custId) {
		String hql = "from Tour where custId=" + custId;
		List<Tour> tours = getHibernateTemplate().find(hql);
		if (tours != null && tours.size() > 0)
			return tours.get(0);
		else
			return null;

	}

	@Override
	public List<Tour> findAll(String where, String orderField, int page,
			int pageCount) {
		final String queryString = "from Tour " + where + " order by "
				+ (orderField == null ? "id" : orderField) + " desc ";
		final int length = pageCount;
		final int offset = page * length;
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

	@Override
	public List<Tour> findByPid(int productID) {
		List<Tour> tours = new ArrayList<Tour>();
		@SuppressWarnings("unchecked")
		List<Object[]> cs = getHibernateTemplate()
				.find("from Tour t, ProductTourLink l where t.custId = l.tourId and l.productId=? ",
						productID);
		for (int i = 0; i < cs.size(); i++) {
			for (int j = 0; j < cs.get(i).length; j++) {
				if (cs.get(i)[j].getClass() == Tour.class)
					tours.add((Tour) cs.get(i)[j]);
			}
		}
		return tours;
	}

	@Override
	public List<Tour> findSQL(String query, int page, int count) {
		List<Tour> ts = new ArrayList<Tour>();
		List<Object[]> tours = getHibernateTemplate().find(query);

		for (int i = 0; i < tours.size(); i++) {
			for (int j = 0; j < tours.get(i).length; j++) {
				if (tours.get(i)[j].getClass() == Tour.class)
					ts.add((Tour) tours.get(i)[j]);
			}
		}
		return ts;
	}
}
