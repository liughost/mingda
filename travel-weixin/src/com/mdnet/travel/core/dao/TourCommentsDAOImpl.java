package com.mdnet.travel.core.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.core.model.TourComments;
@Repository(ITourCommentsDAO.DAO_NAME)
@Scope("prototype")
public class TourCommentsDAOImpl extends BasicDAOImpl<TourComments> implements ITourCommentsDAO{

	@Override
	public List<TourComments> findByCustId(int custId, int pageNo) {
		String hql = "from TourComments where custId=" + custId+" order by id desc";
		final String queryString = hql;
		final int length = 20;
		final int offset = pageNo * length;

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
