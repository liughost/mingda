package com.mdnet.travel.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.ProductType;

@Repository(IProductTypeDAO.DAO_NAME)
@Scope("prototype")
public class ProductTypeDAOImpl extends BasicDAOImpl<ProductType> implements
		IProductTypeDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> findByPid(int productID) {
		List<ProductType> types = new ArrayList<ProductType>();
		List<Object[]> cs = getHibernateTemplate()
				.find("from ProductType t, ProductTypeLink l where t.id = l.typeId and l.productId=? ",
						productID);
		for (int i = 0; i < cs.size(); i++) {
			for (int j = 0; j < cs.get(i).length; j++) {
				if (cs.get(i)[j].getClass() == ProductType.class)
					types.add((ProductType) cs.get(i)[j]);
			}
		}
		return types;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> findByName(String pType) {
		return getHibernateTemplate().find(
				"from ProductType t where t.catalogName in (" + pType + ")");

	}

}
