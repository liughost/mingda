package com.mdnet.travel.core.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.mdnet.travel.core.model.InviteCode;

@Repository(InviteCodeDAO.DAO_NAME)
@Scope("prototype")
public class InviteCodeDAOImpl extends BasicDAOImpl<InviteCode> implements
		InviteCodeDAO {

}
