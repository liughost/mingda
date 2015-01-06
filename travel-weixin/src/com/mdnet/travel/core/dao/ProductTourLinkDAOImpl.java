package com.mdnet.travel.core.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.ProductTourLink;

@Repository(IProductTourLinkDAO.DAO_NAME)
@Scope("prototype")
public class ProductTourLinkDAOImpl extends BasicDAOImpl<ProductTourLink>
		implements IProductTourLinkDAO {

}
