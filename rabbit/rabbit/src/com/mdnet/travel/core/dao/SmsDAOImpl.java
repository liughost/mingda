package com.mdnet.travel.core.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.zhinet.travel.pojo.basepojo.SendSMS;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository(ISmsDAO.DAO_NAME)
@Scope("prototype")
public class SmsDAOImpl extends BasicDAOImpl<SendSMS> implements ISmsDAO {

	@Override
	public List<SendSMS> findSms(String query, int pageNo, int pageCount) {

		final String queryString = "from SendSMS s " + query 
				+ " order by s.id desc";
		final int offset = pageNo * pageCount;
		final int length = pageCount;
		
		//List list  = getHibernateTemplate().find(queryString, SendSMS.class);
		
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(queryString);
				//query.setLockMode("SendSMS", LockMode.READ);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	@Override
	public int changeState(int id, int state) {
		@SuppressWarnings("unchecked")
		List<SendSMS> smss = getHibernateTemplate().find(
				"from SendSMS where id= ? ", id);
		if (smss != null && smss.size() > 0) {
			SendSMS sms = smss.get(0);
			if(state==1)
			{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sms.setSendTime(sdf.format(new Date()));
			}
			sms.setSmsState(state);
			this.update(sms);
			return 0;
		}
		return -1;// 未找到
	}

}
