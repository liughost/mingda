package com.mdnet.travel.core.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jcouchdb.document.Attachment;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mdnet.travel.core.dao.LangCapability;
import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.dao.TourInfo;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.model.GroupMonth;
import com.mdnet.travel.core.model.InviteCode;
import com.mdnet.travel.core.model.TourOrder;
import com.mdnet.travel.core.model.Traveler;
import com.mdnet.travel.core.model.WeixinAccount;
import com.mdnet.travel.core.service.ICustomService;
import com.mdnet.travel.core.service.ILeaderService;
import com.mdnet.travel.core.service.IOrderMgrService;
import com.mdnet.travel.core.service.impl.LineService;
import com.mdnet.travel.core.service.impl.TourService;
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
	@Resource(name = TourService.SERVICE_NAME)
	private TourService tourService;

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

		this.mav.addObject("tourKey", traveler.getTourKey());
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
			return "['1','" + code.getInviteCode() + "']";
		else
			return "['0','" + code.getInviteCode() + "']";
	}

	protected int sendInviteSMS(String uname, String isBind, String inviteCode,
			String invitedMobile, int offPrice) {

		String smsText = uname + "给您发送" + this.myConstant.getCompanyName()
				+ "邀请码：" + inviteCode + ",凭此码到"
				+ this.myConstant.getCompanyName() + "报名参团，优惠" + offPrice + "元";
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

		this.mav.addObject("tourKey", traveler.getTourKey());

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
		Traveler t = this.travelerService.findTravelerByUname(uname);
		if (t != null)
			this.mav.addObject("tourKey", t.getTourKey());
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

	@RequestMapping(value = { "tour/images" }, method = RequestMethod.GET)
	public ModelAndView tourImages(HttpServletRequest request) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "member/tour/images");
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		Traveler traveler = this.travelerService.findTravelerByUname(uname);

		this.mav.addObject("tourKey", traveler.getTourKey());
		return this.mav;
	}

	@RequestMapping(value = { "tour/online" }, method = RequestMethod.POST)
	@ResponseBody
	public String tourOnline(HttpServletRequest request,
			@RequestParam(value = "tourKey", required = false) String tourKey,
			@RequestParam(value = "online", required = false) String online)
			throws UnsupportedEncodingException {
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		Traveler traveler = this.travelerService.findTravelerByUname(uname);
		if (traveler != null && traveler.getTourKey() != null
				&& traveler.getTourKey().length() > 0) {
			String id = traveler.getTourKey();
			// String id = "65ee0915c289090bd637c421a200c1bc";
			TourInfo ti = this.tourService.takeTour(id);

			if (online.compareTo("online") == 0)
				ti.chageOnlineState(true);
			else
				ti.chageOnlineState(false);

			return java.net.URLEncoder.encode("导游状态改为:"
					+ (ti.isOnline() ? "上线" : "草稿"), "UTF-8");
		} else
			return java.net.URLEncoder.encode("导游资料为找到", "UTF-8");
	}

	@RequestMapping(value = { "tour/info" }, method = RequestMethod.GET)
	public ModelAndView tourInfo(HttpServletRequest request) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "member/tour/info");
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		Traveler traveler = this.travelerService.findTravelerByUname(uname);
		if (traveler != null && traveler.getTourKey() != null
				&& traveler.getTourKey().length() > 0) {
			String id = traveler.getTourKey();
			// String id = "65ee0915c289090bd637c421a200c1bc";
			TourInfo ti = this.tourService.takeTour(id);
			this.mav.addObject("tour", ti);
		}
		return this.mav;
	}

	@RequestMapping(value = { "tour/info/update" }, method = RequestMethod.POST)
	@ResponseBody
	public String tourInfoUpdate(
			HttpServletRequest request,
			@RequestParam(value = "tourKey", required = false) String tourKey,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "EMail", required = false) String EMail,
			@RequestParam(value = "QQ", required = false) String QQ,
			@RequestParam(value = "carType", required = false) String carType,
			@RequestParam(value = "career", required = false) String career,
			@RequestParam(value = "feeDesc", required = false) String feeDesc,
			@RequestParam(value = "feeExcept", required = false) String feeExcept,
			@RequestParam(value = "gender", required = false) int gender,
			@RequestParam(value = "introduce", required = false) String introduce,
			@RequestParam(value = "foreignLang", required = false) String foreign,
			@RequestParam(value = "motherLang", required = false) String motherLang,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "planning", required = false) int planning,
			@RequestParam(value = "scenic", required = false) String scenic,
			@RequestParam(value = "serviceCities", required = false) String serviceCities,
			@RequestParam(value = "serviceItem", required = false) String serviceItem,
			@RequestParam(value = "supplement", required = false) String supplement,
			@RequestParam(value = "unitPrice", required = false) int unitPrice) {
		TourInfo ti = this.tourService.newTour();
		ti.setCareer(career);
		ti.setCarType(carType);
		ti.setEMail(EMail);
		ti.setFeeDesc(feeDesc);
		ti.setFeeExcept(feeExcept);
		ti.setGender(gender);
		ti.setHeadImg("");
		ti.setIntroduce(introduce);
		ti.setIntroImg("");
		LangCapability lang = new LangCapability();
		lang.setMotherLang(motherLang);
		lang.setForeignLang(foreign);
		ti.setLanguage(lang);
		ti.setLocation(location);
		ti.setMobile(mobile);
		ti.setNickName(nickName);
		ti.setPlanning(planning);
		ti.setQQ(QQ);
		ti.setScenic(scenic);
		ti.setWeixinId(weixinId);
		ti.setServiceCities(serviceCities);
		List<String> item = new ArrayList<String>();
		String[] strItem = serviceItem.split("\n");
		for (String str : strItem) {
			item.add(str);
		}
		ti.setServiceItem(item);
		ti.setServiceLevel(3);
		ti.setSupplement(supplement);
		ti.setUnitPrice(unitPrice);
		ti.Add();
		// 关联导游库和登录信息表
		String uname = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Traveler traveler = this.travelerService.findTravelerByUname(uname);
		traveler.setTourKey(ti.getId());
		this.travelerService.updateTraveler(traveler);
		return ti.getId();
	}

	@RequestMapping(value = "/upload/head", method = RequestMethod.POST)
	// @ResponseBody
	public String uploadHeadFile(@RequestParam("tourKey") String tourKey,
			@RequestParam("uploadImg") String imgType,
			HttpServletRequest request) throws IOException {

		TourInfo ti = this.tourService.takeTour(tourKey);
		MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
		List<MultipartFile> fileHead = mhs.getFiles("headImg");
		Attachment attHead = new Attachment(MediaType.IMAGE_JPEG_VALUE,
				fileHead.get(0).getBytes());
		List<MultipartFile> fileIntro = mhs.getFiles("introImg");
		Attachment attIntro = new Attachment(MediaType.IMAGE_JPEG_VALUE,
				fileIntro.get(0).getBytes());
		List<MultipartFile> fileWeixin = mhs.getFiles("weixinImg");
		Attachment attWeixin = new Attachment(MediaType.IMAGE_JPEG_VALUE,
				fileWeixin.get(0).getBytes());

		// 获取原来微信
		try {
			byte[] bb = ti.takeDbObj().getAttachment(tourKey, "weixinQR.jpg");
			if (bb != null) {
				Attachment att0 = new Attachment(MediaType.IMAGE_JPEG_VALUE, bb);
				ti.addAttachment("weixinQR.jpg", att0);
			}
		} catch (Exception e) {
		}
		// 获取原来宣传照
		try {
			byte[] bb = ti.takeDbObj().getAttachment(tourKey, "intro.jpg");
			if (bb != null) {
				Attachment att0 = new Attachment(MediaType.IMAGE_JPEG_VALUE, bb);
				ti.addAttachment("intro.jpg", att0);
			}
		} catch (Exception e) {
		}
		// 获取原来头像
		try {
			byte[] bb = ti.takeDbObj().getAttachment(tourKey, "head.jpg");
			if (bb != null) {
				Attachment att0 = new Attachment(MediaType.IMAGE_JPEG_VALUE, bb);
				ti.addAttachment("head.jpg", att0);
			}
		} catch (Exception e) {
		}

		if (attHead.getData().length() > 0) {
			ti.addAttachment("head.jpg", attHead);
		}
		if (attWeixin.getData().length() > 0) {
			ti.addAttachment("weixinQR.jpg", attWeixin);
		}
		if (attIntro.getData().length() > 0) {
			ti.addAttachment("intro.jpg", attIntro);
		}
		ti.updateAttachment();
		// this.tourService;
		return this.REDIRECT + "../tour/images";
		// return String.valueOf(files.get(0).getSize());
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
