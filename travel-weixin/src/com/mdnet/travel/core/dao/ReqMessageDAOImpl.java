package com.mdnet.travel.core.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.mdnet.travel.core.model.ReqMessage;
@Repository(ReqMessageDAO.DAO_NAME)
@Scope("prototype")
public class ReqMessageDAOImpl extends BasicDAOImpl<ReqMessage> implements ReqMessageDAO{

	@Override
	public List<ReqMessage> find(String where, int pageNo, int pageCount) {
		final String queryString = "from ReqMessage " + where
				+ " order by messageId desc ";
		final int length = pageCount;
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
