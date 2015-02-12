package com.mdnet.travel.core.controller;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.model.GroupMonth;
import com.mdnet.travel.core.model.InviteCode;
import com.mdnet.travel.core.model.TourOrder;
import com.mdnet.travel.core.model.Traveler;
import com.mdnet.travel.core.service.ICustomService;
import com.mdnet.travel.core.service.ILeaderService;
import com.mdnet.travel.core.service.IOrderMgrService;
import com.mdnet.travel.core.service.impl.LineService;
import com.mdnet.travel.core.vo.ShowProductInfo;
import com.mdnet.travel.order.model.OrderInfo;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	@Resource(name = ICustomService.SERVICE_NAME)
	protected ICustomService customService;
	@Resource(name = IOrderMgrService.SERVICE_NAME)
	private IOrderMgrService orderMgrService;
	@Resource(name = LineService.SERVICE_NAME)
	private LineService lineService;

	/**
	 * 显示会员列表界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView ListUI(HttpServletRequest request) {

		this.getMav(request);
		this.mav.setViewName("member/list");
		return this.mav;
	}

	@RequestMapping(value = { "/invite" }, method = RequestMethod.GET)
	public ModelAndView inviteUI(HttpServletRequest request) {

		this.getMav(request);
		this.mav.setViewName("member/invite");
		return this.mav;
	}

	@RequestMapping(value = { "/order/list" }, method = RequestMethod.GET)
	public ModelAndView orderListUI(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) {

		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "member/order/list");
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Traveler traveler = this.travelerService.findTravelerByUname(uname);
		int pageNo = 0;
		if (page != null)
			pageNo = Integer.parseInt(page);
		List<OrderInfo> orders = this.orderMgrService.getOrders(1,
				traveler.getMobile(), pageNo);
		this.mav.addObject("orderList", orders);
		this.doPager(pageNo, orders == null ? 0 : orders.size());
		return this.mav;
	}

	@RequestMapping(value = { "/invite/resend" }, method = RequestMethod.POST)
	@ResponseBody
	public String resendIinvite(HttpServletRequest request,
			@RequestParam(value = "id", required = true) int id) {
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		InviteCode code = this.travelerService.getInviteCodeObj(id);
		if (code.getCodeStatus() == 0
				&& this.sendInviteSMS(uname, code.getCodeType() == 1 ? "true"
						: "false", code.getInviteCode(), code.getDest_mobile(),
						code.getOffPrice()) > 0)
			return "1";
		else
			return "0";
	}

	protected int sendInviteSMS(String uname, String isBind, String inviteCode,
			String invitedMobile, int offPrice) {

		String smsText = uname + "给您发送冠行旅游邀请码：" + inviteCode
				+ ",凭此码到冠行旅游报名参团，优惠" + offPrice + "元";
		if (isBind != null && isBind.compareTo("true") == 0) {
			smsText += "，转赠无效。";
		} else
			smsText += "，此码转赠有效。";
		// 发送邀请码
		try {
			this.getProxy().sendSMS(smsText, invitedMobile);
			return 1;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = { "/invite/sendReq" }, method = RequestMethod.POST)
	@ResponseBody
	public String sendIinvite(
			HttpServletRequest request,
			@RequestParam(value = "isBind", required = false) String isBind,
			@RequestParam(value = "invitedMobile", required = false) String invitedMobile) {
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		// 生成邀请码
		String ss = UUID.randomUUID().toString();
		int c = ss.hashCode() & 0x7FFFFFFF;
		String inviteCode = String.valueOf(c);
		inviteCode = inviteCode.substring(0, Math.min(6, inviteCode.length()));
		int offPrice = 500;
		// 保存邀请码
		int r = this.travelerService.saveInvite(uname, isBind, invitedMobile,
				inviteCode, offPrice);
		if (r <= 0)
			return "-1";
		if (this.sendInviteSMS(uname, isBind, inviteCode, invitedMobile,
				offPrice) > 0)
			return "1";
		else
			return "0";
	}

	@RequestMapping(value = { "/invite/send" }, method = RequestMethod.GET)
	public ModelAndView sendIinviteUI(HttpServletRequest request,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "sType", required = false) String sType,
			@RequestParam(value = "sText", required = false) String sText,
			@RequestParam(value = "page", required = false) String pageNo) {

		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "member/invite/send");
		int page = 0;
		if (pageNo != null)
			page = Integer.parseInt(pageNo);

		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		Traveler traveler = this.travelerService.findTravelerByUname(uname);

		List<InviteCode> codes = this.travelerService.getInviteList(sType,
				sText, traveler.getMobile(), page, 20);
		this.mav.addObject("inviteList", codes);
		this.mav.addObject("sType", sType == null ? 0 : sType);
		this.mav.addObject("sText", sText);
		this.doPager(page, codes != null ? codes.size() : 0);
		return this.mav;
	}

	@RequestMapping(value = { "/invite/list" }, method = RequestMethod.POST)
	@ResponseBody
	public String inviteList(
			@RequestParam(value = "sType", required = false) String sType,
			@RequestParam(value = "sText", required = false) String sText,
			@RequestParam(value = "page", required = false) String pageNo) {

		int page = 0;
		if (pageNo != null && pageNo.length() > 0)
			page = Integer.parseInt(pageNo);
		List<InviteCode> inCodes = this.travelerService.getInviteList(sType,
				sText, page, 20);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (InviteCode c : inCodes) {
			try {
				c.getMember().setUserName(
						java.net.URLEncoder.encode(c.getMember().getUserName(),
								"UTF-8"));
				c.getMember().setLoginName(
						java.net.URLEncoder.encode(
								c.getMember().getLoginName(), "UTF-8"));

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson g = new Gson();
		return g.toJson(inCodes);
	}

	/**
	 * 更新会员信息
	 * 
	 * @param request
	 * @param uid
	 * @param loginName
	 * @param userMobile
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = { "info/update" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateUserInfo(
			HttpServletRequest request,
			@RequestParam(value = "uid", required = true) String uid,
			@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "userMobile", required = true) String userMobile,
			@RequestParam(value = "userName", required = true) String userName) {
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		Traveler traveler = this.travelerService.findTravelerByUname(uname);
		if (traveler.getTravelerId().compareTo(uid) != 0)
			return "-1";
		traveler.setLoginName(loginName);
		traveler.setUserName(userName);
		traveler.setMobile(userMobile);
		this.travelerService.updateInviteSender(traveler);
		return "1";
	}

	/**
	 * 获取会员列表
	 * 
	 * @param request
	 * @param sType
	 * @param sText
	 * @param page
	 * @return
	 */
	@RequestMapping(value = { "/getList" }, method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,
			@RequestParam(value = "sType", required = true) int sType,
			@RequestParam(value = "sText", required = true) String sText,
			@RequestParam(value = "page", required = true) int page) {
		List<Traveler> ts = this.travelerService.getMembers(sType, sText, page);
		for (Traveler t : ts) {
			try {
				t.setUserName(java.net.URLEncoder.encode(t.getUserName(),
						"UTF-8"));
				t.setLoginName(java.net.URLEncoder.encode(t.getLoginName(),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson g = new Gson();
		return g.toJson(ts);
	}

	/**
	 * 显示新增会员界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView newUI(HttpServletRequest request) {

		this.getMav(request);
		this.mav.setViewName("member/new");
		return this.mav;
	}

	/*
	 * 会员登录首页，展示会员基本信息
	 */
	@RequestMapping(value = { "/info" }, method = RequestMethod.GET)
	public ModelAndView bookInfo(HttpServletRequest request) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "member/info");
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		// this.mav.addObject("invite", this.travelerService.getInviteList(-1,
		// "", page, count));
		this.mav.addObject("info",
				this.travelerService.findTravelerByUname(uname));
		return this.mav;
	}

	/**
	 * 显示会员预订界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/book" }, method = RequestMethod.GET)
	public ModelAndView bookUI(HttpServletRequest request,
			@RequestParam(value = "memId", required = false) String memId) {

		this.getMav(request);
		this.mav.setViewName("member/book");
		ShowProductInfo[] ps = this.lineService.getProductList(3, 0);// .orderMgrService.getProductList(3,
		this.mav.addObject("productList", ps);
		return this.mav;
	}

	/**
	 * 会员积分列表
	 * 
	 * @param request
	 * @param memId
	 * @return
	 */
	@RequestMapping(value = { "/score" }, method = RequestMethod.GET)
	public ModelAndView scoreUI(HttpServletRequest request,
			@RequestParam(value = "memId", required = false) String memId) {

		this.getMav(request);
		this.mav.setViewName("member/score");
		return this.mav;
	}

	/**
	 * 国籍驾照信息
	 * 
	 * @param request
	 * @param memId
	 * @return
	 */
	@RequestMapping(value = { "/driverCard" }, method = RequestMethod.GET)
	public ModelAndView driverCardUI(HttpServletRequest request,
			@RequestParam(value = "memId", required = false) String memId) {

		this.getMav(request);
		this.mav.setViewName("member/driverCard");
		return this.mav;
	}
}
