package com.mdnet.travel.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.mdnet.travel.core.model.Traveler;

import common.hibernate3.daosupport.SEHibernateDaoSupport;

public class BasicDAOImpl<T> extends SEHibernateDaoSupport implements
		BasicDAO<T> {

	private Class entityClass;

	public BasicDAOImpl() {
		Type type = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	public List<T> getList(String hql, int pageNo, int pageLen) {
		//List<T> tList = this.getHibernateTemplate().find(hql);
		//return tList;
		final String queryString = hql;
		final int length = pageLen;
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

	/**
	 * 根据标识属性来加载T实例
	 * 
	 * @param id
	 *            需要加载的T实例的标识属性值
	 * @return 指定标识属性对应的T实例
	 */
	public T get(Integer id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 持久化指定的T实例
	 * 
	 * @param entity
	 *            需要被持久化的T实例
	 * @return entity实例被持久化后的标识属性值
	 */
	public Integer save(T entity) {
		Serializable r = getHibernateTemplate().save(entity);
		return Integer.parseInt(r.toString());
	}

	/**
	 * 修改指定的T实例
	 * 
	 * @param entity
	 *            需要被修改的T实例
	 */
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 删除指定的T实例
	 * 
	 * @param entity
	 *            需要被删除的T实例
	 */
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据标识属性删除T实例
	 * 
	 * @param id
	 *            需要被删除的T实例的标识属性值
	 */
	public void delete(Integer id) {
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 查询全部的T实例
	 * 
	 * @return 数据库中全部的T实例
	 */
	public List<T> findAll() {
		return (List<T>) getHibernateTemplate().find(
				"from " + entityClass.getSimpleName());
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactoryDI(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
