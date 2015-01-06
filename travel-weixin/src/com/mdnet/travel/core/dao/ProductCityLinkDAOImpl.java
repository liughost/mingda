package com.mdnet.travel.core.dao;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.ProductCityLink;

@Repository(IProductCityLinkDAO.DAO_NAME)
@Scope("singleton")
public class ProductCityLinkDAOImpl extends BasicDAOImpl<ProductCityLink> implements
		IProductCityLinkDAO {

	@Override
	public List<ProductCityLink> findByPid(int pid) {
		List<ProductCityLink> links = getHibernateTemplate().find(
				"from ProductCityLink where productId= ?", pid);
		return links;
	}

	@Override
	public List<ProductCityLink> findBycity(int cid) {
		List<ProductCityLink> links = getHibernateTemplate().find(
				"from ProductCityLink where cityId = ?", cid);
		return links;
	}
}
