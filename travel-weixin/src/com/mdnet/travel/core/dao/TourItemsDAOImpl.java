package com.mdnet.travel.core.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.TourItems;
@Repository(ITourItemsDAO.DAO_NAME)
@Scope("prototype")
public class TourItemsDAOImpl extends BasicDAOImpl<TourItems> implements ITourItemsDAO{
	@Override
	public TourItems findByCustId(int custId) {
		String hql = "from TourItems where custId=" + custId;
		List<TourItems> tours = getHibernateTemplate().find(hql);
		if (tours != null && tours.size() > 0)
			return tours.get(0);
		else
			return null;

	}

	@Override
	public List<TourItems> findAll(String where, String orderField, int page,
			int pageCount) {
		final String queryString = "from TourItems " + where + " order by "
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
}
