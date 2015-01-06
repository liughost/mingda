package com.mdnet.travel.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.Customer;

@Repository(ICityDefDAO.DAO_NAME)
@Scope("prototype")
public class CityDefDAOImpl extends BasicDAOImpl<CityDef> implements
		ICityDefDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<CityDef> findProductCity(String cids) {
		if (cids == null || cids.length() == 0)
			return null;
		List<CityDef> cities = getHibernateTemplate().find(
				"from CityDef where id in (" + cids + ") ");
		return cities;
	}

	@Override
	public List<CityDef> findCityBypid(int pid) {
		List<CityDef> cities = new ArrayList<CityDef>();
		@SuppressWarnings("unchecked")
		List<Object[]> cs = getHibernateTemplate()
				.find("from CityDef c, ProductCityLink l where c.id = l.cityId and l.productId=? ",
						pid);
		for (int i = 0; i < cs.size(); i++) {
			for (int j = 0; j < cs.get(i).length; j++) {
				if (cs.get(i)[j].getClass() == CityDef.class)
					cities.add((CityDef) cs.get(i)[j]);
			}
		}
		// @SuppressWarnings("unchecked")
		// List<CityDef> cities = getHibernateTemplate().find(
		// "from CityDef c inner join ProductCityLink l with c.id = l.cityId where l.productId=? ",
		// pid);
		return cities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDef> findCity(String cName) {
		List<CityDef> cs = getHibernateTemplate().find(
				"from CityDef where cityName in (" + cName + ")");
		return cs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDef> findSQL(String query) {
		List<CityDef> cities = new ArrayList<CityDef>();
		List<Object[]> cs = getHibernateTemplate().find(query);
		for (int i = 0; i < cs.size(); i++) {
			for (int j = 0; j < cs.get(i).length; j++) {
				if (cs.get(i)[j].getClass() == CityDef.class)
					cities.add((CityDef) cs.get(i)[j]);
			}
		}
		return cities;
	}
}
