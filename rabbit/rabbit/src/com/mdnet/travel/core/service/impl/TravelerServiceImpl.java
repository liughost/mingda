package com.mdnet.travel.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mdnet.travel.core.dao.InviteCodeDAO;
import com.mdnet.travel.core.dao.MetaKeysDAO;
import com.mdnet.travel.core.dao.TravelerDAO;
import com.mdnet.travel.core.dao.ValidCodeDAO;
import com.mdnet.travel.core.dao.WeixinAccountDAO;
import com.mdnet.travel.core.model.InviteCode;
import com.mdnet.travel.core.model.MetaKeys;
import com.mdnet.travel.core.model.Traveler;
import com.mdnet.travel.core.model.ValidateCode;
import com.mdnet.travel.core.model.WeixinAccount;
import com.mdnet.travel.core.service.ITravelerService;
import com.mdnet.travel.core.utils.EncryptSpring;
import com.mdnet.travel.core.utils.SimpleDateUtil;
import com.mdnet.travel.core.vo.FindPassVO;
import com.mdnet.travel.core.vo.MemberInfoVO;
import com.mdnet.travel.core.vo.ResetMobileVO;
import com.mdnet.travel.core.vo.ResetPassVO;
import com.mdnet.travel.core.vo.UserListBean;

@Service(ITravelerService.SERVICE_NAME)
@Scope("prototype")
@Transactional(readOnly = true)
public class TravelerServiceImpl implements ITravelerService {

	@Resource(name = ValidCodeDAO.DAO_NAME)
	protected ValidCodeDAO validCodeDAO;

	@Resource(name = TravelerDAO.DAO_NAME)
	protected TravelerDAO travelDAO;

	@Resource(name = WeixinAccountDAO.DAO_NAME)
	protected WeixinAccountDAO accountDAO;

	@Resource(name = MetaKeysDAO.DAO_NAME)
	protected MetaKeysDAO metaKeysDAO;

	@Resource(name = InviteCodeDAO.DAO_NAME)
	protected InviteCodeDAO inviteCodeDAO;

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public Integer saveTraveler(String username, String pass, String mobile,
			String openId, String role, String wxFromUserName, String travelerId) {

		Traveler traveler = null;

		if (travelerId != null && travelerId.length() > 0) {
			traveler = this.travelDAO.findUserById(travelerId);
		} else {
			traveler = new Traveler(username, EncryptSpring.md5_32(pass),
					mobile);
		}
		traveler.setLoginName(username);
		traveler.setUserName(username);
		traveler.setPassText(pass);
		traveler.setPassword(EncryptSpring.md5_32(pass));
		traveler.setMobile(mobile);
		traveler.setAuthority(role);
		traveler.setWxFromUserName(wxFromUserName);
		traveler.setToken(pass);
		traveler.setWxOpenId(openId);
		if (travelerId != null && travelerId.length() > 0) {
			this.travelDAO.update(traveler);
		} else {
			int newId = this.travelDAO.save(traveler);
			traveler.setTravelerId(String.valueOf(newId));
		}
		return Integer.parseInt(traveler.getTravelerId());
	}

	public Traveler findFromUserName(String fromUserName) {
		Traveler traveler = this.travelDAO.findUserByWX(fromUserName);
		return traveler;
	}

	public String findMobile(String username) {
		String mobile = null;
		if (StringUtils.isNotBlank(username)) {
			Traveler traveler = this.travelDAO.findByLoginName(username);
			if (null != traveler) {
				mobile = traveler.getMobile();
			}
		}
		return mobile;
	}

	@Override
	public Integer findPrimaryKey(String username) {
		Integer primaryKey = -1;
		if (StringUtils.isNotBlank(username)) {
			Traveler traveler = this.travelDAO.findByLoginName(username);
			if (null != traveler) {
				primaryKey = Integer.parseInt(traveler.getTravelerId());
			}
		}
		return primaryKey;
	}

	/**
	 * 重置密码
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String resetPassByUser(ResetPassVO passInfo, String username) {
		String info = "";
		Traveler user = this.travelDAO.findByLoginName(username);
		String oldP = passInfo.getOld_pass().trim();
		String newP = passInfo.getNew_pass().trim();
		String secondP = passInfo.getSecond_pass().trim();
		if (StringUtils.isNotBlank(oldP) && StringUtils.isNotBlank(newP)
				&& StringUtils.isNotBlank(secondP)) {
			if (user.getPassword().equals(EncryptSpring.md5_32(oldP))) {
				if (newP.equals(secondP)) {
					user.setPassword(EncryptSpring.md5_32(newP));
					this.travelDAO.update(user);
					info = "修改成功";
				} else {
					info = "两次输入的新密码不符合";
				}
			} else {
				info = "原始密码错误";
			}
		} else {
			info = "存在未填项";
		}
		return info;
	}

	/**
	 * 保存验证码
	 * 
	 * 根据用户名查找记录 1、没找到 直接存 2、找到 更新记录
	 */
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String saveValidCode(String mobile, String validCode) {
		String saveInfo = "";

		// 根据用户名查找记录
		if (StringUtils.isNotBlank(mobile)) {
			ValidateCode code = this.validCodeDAO
					.getCodeByMobile(mobile.trim());
			if (null != code) {
				code.setStatus(0);
				code.setCreateTime(new Date());
				code.setValidCode(validCode);
				this.validCodeDAO.update(code);
			} else {
				this.validCodeDAO.save(new ValidateCode(new Date(), mobile,
						validCode));
			}
			saveInfo = "保存成功";
		} else {
			saveInfo = "手机号不能为空";
		}

		return saveInfo;
	}

	/**
	 * 根据用户名找到数据库中的验证码记录 先比对时间是否有效 false:“验证码已过期，请重新获取!” 如果时间有效，则比对验证码是否正确
	 * false:“验证码输入错误!” 都过，则找到相应用户名下的用户，重置其手机号码
	 * 程序没有抛出异常，则返回“重置成功”，错误时，“系统繁忙，稍后重试”
	 */

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String resetMobileByUser(ResetMobileVO mobiles, String username) {
		String mobile = mobiles.getMobile().trim();
		String code = mobiles.getValid_code().trim();
		Long curTime = SimpleDateUtil.curTimeMillis();
		String message = "";

		ValidateCode vcode = this.validCodeDAO.getCodeByUser(username);
		if (null != vcode) {
			Long minuteDifference = SimpleDateUtil.getMinuteDifference(
					Long.parseLong(String.valueOf(vcode.getCreateTime())),
					curTime);
			if (3 >= minuteDifference) {
				if (code.equals(vcode.getValidCode().trim())) {
					Traveler traveler = this.travelDAO
							.findByLoginName(username);
					traveler.setMobile(mobile);
					this.travelDAO.update(traveler);
					message = "重置成功";
				} else {
					message = "验证码输入有误!";
				}
			} else {
				message = "验证码已过期，请重新获取!";
			}
		} else {
			message = "请确认是否已发送验证码,如果还不成功请找客服!";
		}

		return message;
	}

	/**
	 * 根据电话号码查找用户
	 */
	public MemberInfoVO findUserByMobile(String mobile) {
		Traveler traveler = this.travelDAO.findUserByMobile(mobile);
		MemberInfoVO memberInfo = null;
		if (null != traveler) {
			memberInfo = new MemberInfoVO(traveler.getLoginName(),
					traveler.getPassword(), mobile, traveler.getAuthority());
		}
		return memberInfo;
	}

	/**
	 * 处理登录时用户名或密码不记得情形： 根据验证的电话号码找到系统用户和验证码实体 首先比对验证码 比对两次密码是否相等 更新系统用户的密码
	 */

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String findPassOrUser(FindPassVO findInfo) {
		String message = "";
		String newPass = findInfo.getNew_pass().trim();
		String secondPass = findInfo.getSecond_pass().trim();
		String mobile = findInfo.getValid_mobile().trim();
		String validCode = findInfo.getValidate_code() + "";
		Traveler traveler = this.travelDAO.findUserByMobile(mobile);

		ValidateCode vc = this.validCodeDAO.getCodeByMobile(mobile);
		if (null != traveler && null != vc) {
			if (validCode.equals(vc.getValidCode())) {
				if (newPass.equals(secondPass)) {

					traveler.setPassword(EncryptSpring.md5_32(newPass));
					this.travelDAO.update(traveler);
					message = "找回成功";
				} else {
					message = "两次输入的密码不符合";
				}
			} else {
				message = "输入验证码不符合";
			}
		} else {
			message = "该手机号未绑定或未发送验证码";
		}

		return message;
	}

	/**
	 * 根据移动手机号查找发送的验证码
	 */
	@Override
	public ValidateCode findValidCodeByMobile(String mobile) {
		return this.validCodeDAO.getCodeByMobile(mobile.trim());
	}

	@Override
	public String findValidateCodeByMobileAndName(String username, String mobile) {

		String validCode = "";
		ValidateCode code = this.validCodeDAO.getCodeByUserAndMobile(username,
				mobile);
		if (null != code) {
			validCode = code.getValidCode();
		}
		return validCode;

	}

	@Override
	public Traveler findTravelByOpenId(String openId) {
		// TODO Auto-generated method stub
		Traveler traveler = this.travelDAO.findUserByOpenId(openId);

		return traveler;
	}

	@Override
	public String findMobileByOpenId(String openId) {
		String mobile = "";

		Traveler traveler = this.travelDAO.findUserByOpenId(openId);
		if (null != traveler) {
			mobile = traveler.getMobile();
		}

		return mobile;
	}

	@Override
	public List<UserListBean> findAdmin(int pageNo, String role) {
		List<UserListBean> userList = new ArrayList<UserListBean>();
		List<Traveler> travelers = this.travelDAO.findAdmin(role, pageNo);
		for (Traveler traveler : travelers) {
			UserListBean user = new UserListBean();
			user.setId(Integer.parseInt(traveler.getTravelerId()));
			user.setMobile(traveler.getMobile());
			user.setName(traveler.getLoginName());
			user.setWeixinid(traveler.getWxFromUserName());
			WeixinAccount account = this.accountDAO.find(traveler
					.getWxFromUserName());
			String weixinName = "未知";
			if (account != null)
				weixinName = account.getWxName();
			user.setWeixinName(weixinName);
			String role0 = "";
			if (traveler.getAuthority().compareTo("ROLE_ADMIN") == 0)
				role0 = "帐号管理员";
			else if (traveler.getAuthority().compareTo("ROLE_SUPER") == 0)
				role0 = "系统管理员";
			user.setRole(role0);
			userList.add(user);
		}
		return userList;
	}

	@Override
	public UserListBean findTravelerById(int travelerId) {
		Traveler traveler = this.travelDAO.findUserById(String
				.valueOf(travelerId));
		if (traveler != null) {
			UserListBean user = new UserListBean();
			user.setId(Integer.parseInt(traveler.getTravelerId()));
			user.setMobile(traveler.getMobile());
			user.setName(traveler.getLoginName());
			user.setWeixinid(traveler.getWxFromUserName());
			WeixinAccount account = this.accountDAO.find(traveler
					.getWxFromUserName());
			String weixinName = "未知";
			if (account != null)
				weixinName = account.getWxName();
			user.setWeixinName(weixinName);
			String role0 = "";
			if (traveler.getAuthority().compareTo("ROLE_ADMIN") == 0)
				role0 = "帐号管理员";
			else if (traveler.getAuthority().compareTo("ROLE_SUPER") == 0)
				role0 = "系统管理员";
			role0 = traveler.getAuthority();
			user.setRole(role0);
			return user;
		} else
			return null;
	}

	@Override
	public boolean checkUsename(int travelerId, String username) {

		if (StringUtils.isNotBlank(username)) {
			Traveler traveler = this.travelDAO.findByLoginName(username);
			if (null != traveler) {
				if (travelerId == Integer.parseInt(traveler.getTravelerId()))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteUser(int id) {
		Traveler traveler = this.travelDAO.findUserById(String.valueOf(id));
		if (traveler != null) {
			this.travelDAO.delete(traveler);
			return 0;
		}
		return 0;
	}

	@Override
	public Traveler findTravelerByUname(String uname) {
		return this.travelDAO.findByLoginName(uname);
	}

	@Override
	public String getMetaKeys(String uri) {
		String pageKeys = this.metaKeysDAO.getKeys(uri);
		String generalKeys = this.metaKeysDAO.getGeneral();
		return generalKeys + (pageKeys.length() > 0 ? ("," + pageKeys) : "");
	}

	@Override
	public List<MetaKeys> getAllMetaKeys(int page) {
		return this.metaKeysDAO.getAllKeys(page);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String saveMetaKeys(int id, String newKeys) {
		MetaKeys mk = this.metaKeysDAO.get(id);
		mk.setKeywords(newKeys);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String modifyTime = sdf.format(new Date());
		mk.setModifyTime(modifyTime);

		this.metaKeysDAO.update(mk);
		return modifyTime;
	}

	@Override
	public MetaKeys getMetaKeysByUri(String uri) {
		return this.metaKeysDAO.getMetaKeys(uri);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public MetaKeys updateMetaKeys(String uri, String newKeys) {
		MetaKeys key = this.metaKeysDAO.getMetaKeys(uri);
		if (key == null) {
			key = new MetaKeys();
			key.setPageUri(uri);
			key.setKeywords(newKeys);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			key.setModifyTime(sdf.format(new Date()));
			this.metaKeysDAO.save(key);
		} else {
			key.setKeywords(newKeys);
			this.metaKeysDAO.save(key);
		}
		return key;
	}

	@Override
	public List<Traveler> getMembers(int sType, String sText, int page) {
		String hql = "from Traveler where authority='ROLE_MEM'";
		if (sType == 1) {
			hql += " and mobile='" + sText + "'";
		} else if (sType == 2) {
			hql += " and userName='" + sText + "'";
		} else if (sType == 3) {
			hql += " and inviteCode='" + sText + "'";
		}
		return this.travelDAO.GetTravelers(hql, page, 20);

	}

	@Override
	public List<InviteCode> validateCode(String code) {
		return this.inviteCodeDAO.getList(
				" from InviteCode I where I.inviteCode='" + code + "'", 0, 1);
	}

	@Override
	public List<InviteCode> getInviteList(String sType, String sText, int page,
			int count) {
		if (sType != null) {
			int t = Integer.parseInt(sType);
			String hql = "from InviteCode I";
			if (t == 0)// 全部
				hql += "";
			else if (t == 3) {
				hql += " where I.inviteCode='" + sText + "'";
			} else {
				Traveler travel = null;
				if (t == 1) {
					travel = this.travelDAO.findUserByMobile(sText);
				} else if (t == 2) {
					travel = this.travelDAO.findByUserName(sText);
				}
				if (travel != null)
					hql += " where I.member.travelerId="
							+ travel.getTravelerId();
				else
					hql += " where I.inviteCode=0";
			}

			return this.inviteCodeDAO.getList(hql, page, count);
		} else
			return this.inviteCodeDAO
					.getList("from InviteCode I ", page, count);
	}

	@Override
	public List<InviteCode> getInviteList(String sType, String sText,
			String mobile, int page, int count) {
		String hql = "from InviteCode I where I.member.mobile='" + mobile + "'";
		if (sType != null) {
			int t = Integer.parseInt(sType);

			if (t == 3) {
				hql += " and I.dest_mobile='" + sText + "'";
			} else if (t == 1) {
				hql += " and I.inviteCode='" + sText + "'";
			}

		}
		return this.inviteCodeDAO.getList(hql + " order by id desc", page,
				count);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public int saveInvite(String uname, String isBind, String invitedMobile,
			String inviteCode, int offPrice) {
		Traveler traveler = this.findTravelerByUname(uname);
		if (traveler.getMobile().compareTo(invitedMobile) == 0)
			return -2;
		InviteCode entity = new InviteCode();
		entity.setCodeLevel(0);
		entity.setCodeStatus(0);
		entity.setCodeType(isBind.compareTo("true") == 0 ? 1 : 0);// 1:此号码专属，0：非专属
		entity.setDest_mobile(invitedMobile);
		entity.setInviteCode(inviteCode);
		entity.setMember(traveler);
		entity.setMakeTime(new Date());
		entity.setOffPrice(offPrice);
		entity.setPolicy("");
		entity.setScore(0);
		entity.setUsedTime(new Date());
		return this.inviteCodeDAO.save(entity);
	}

	@Override
	public InviteCode getInviteCodeObj(int id) {

		return this.inviteCodeDAO.get(id);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void updateInviteCode(InviteCode code) {
		this.inviteCodeDAO.update(code);
	}

	@Override
	public Traveler findtravlerByMobile(String mobile) {
		return this.travelDAO.findUserByMobile(mobile);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void updateInviteSender(Traveler sender) {
		this.travelDAO.update(sender);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void update(ValidateCode code) {
		this.validCodeDAO.update(code);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void updateTraveler(Traveler traveler) {
		this.travelDAO.update(traveler);
	}

}
