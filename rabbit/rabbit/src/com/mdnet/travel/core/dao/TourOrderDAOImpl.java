package com.mdnet.travel.core.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.TourOrder;
@Repository(ITourOrderDAO.DAO_NAME)
@Scope("prototype")
public class TourOrderDAOImpl extends BasicDAOImpl<TourOrder> implements ITourOrderDAO{

}
