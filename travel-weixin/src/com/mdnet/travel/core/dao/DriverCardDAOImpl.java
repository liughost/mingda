package com.mdnet.travel.core.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.mdnet.travel.core.model.DriverCard;

@Repository(DriverCardDAO.DAO_NAME)
@Scope("prototype")
public class DriverCardDAOImpl extends BasicDAOImpl<DriverCard> implements
		DriverCardDAO {
}
