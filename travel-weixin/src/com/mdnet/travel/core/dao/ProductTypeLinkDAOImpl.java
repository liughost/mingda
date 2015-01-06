package com.mdnet.travel.core.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.ProductTypeLink;

@Repository(IProductTypeLinkDAO.DAO_NAME)
@Scope("prototype")
public class ProductTypeLinkDAOImpl extends BasicDAOImpl<ProductTypeLink> implements
IProductTypeLinkDAO{

}
