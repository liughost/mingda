package com.mdnet.travel.core.controller;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mdnet.travel.core.dao.ProductAllDetail;
import com.mdnet.travel.core.dao.TourInfo;
import com.mdnet.travel.core.model.CityDef;
import com.mdnet.travel.core.model.GroupDate;
import com.mdnet.travel.core.model.GroupDay;
import com.mdnet.travel.core.model.GroupMonth;
import com.mdnet.travel.core.model.GroupWeek;
import com.mdnet.travel.core.model.InviteCode;
import com.mdnet.travel.core.model.PersonalCustom;
import com.mdnet.travel.core.model.ProductType;
import com.mdnet.travel.core.model.ReqMessage;
import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.core.model.Traveler;
import com.mdnet.travel.core.model.WeixinAccount;
import com.mdnet.travel.core.service.ICustomService;
import com.mdnet.travel.core.service.ILeaderService;
import com.mdnet.travel.core.service.IMessageService;
import com.mdnet.travel.core.service.IOrderMgrService;
import com.mdnet.travel.core.service.impl.LineService;
import com.mdnet.travel.core.service.impl.TourService;
import com.mdnet.travel.core.utils.CommonUtils;
import com.mdnet.travel.core.vo.GroupListBean;
import com.mdnet.travel.core.vo.PersonalCustomBean;
import com.mdnet.travel.core.vo.ShowProductInfo;
import com.mdnet.travel.core.vo.SpecialBean;
import com.mdnet.travel.core.vo.SpecialInfo;
import com.mdnet.travel.order.model.OrderInfo;
import com.mdnet.travel.order.model.ProductDetail;
import com.mdnet.travel.order.model.ProductPriceLine;

@Controller
@RequestMapping("/custom")
public class CustomController extends BaseController {
	@Resource(name = ICustomService.SERVICE_NAME)
	protected ICustomService customService;
	@Resource(name = IMessageService.SERVICE_NAME)
	private IMessageService messageService;
	@Resource(name = IOrderMgrService.SERVICE_NAME)
	private IOrderMgrService orderMgrService;
	@Resource(name = ILeaderService.SERVICE_NAME)
	private ILeaderService leaderService;
	@Resource(name = IOrderMgrService.SERVICE_NAME)
	protected IOrderMgrService orderService;

	@Resource(name = LineService.SERVICE_NAME)
	protected LineService lineService;
	@Resource(name = TourService.SERVICE_NAME)
	protected TourService tourService;

	@Resource
	ProcessEngine engine;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		// return this.REDIRECT + "/" + this.preMobile(request) + "custom/book";
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "custom/home");
		return this.mav;
	}

	private String searchGroupKey(String txt) {
		while (true) {
			int inx0 = txt.indexOf("#G");
			if (inx0 < 0)
				break;
			int inx1 = txt.substring(inx0 + 1).indexOf('#');
			if (inx0 >= 0 && inx1 > 0) {
				inx1++;
				// 替换
				String code = txt.substring(inx0, inx0 + inx1 + 1);
				// 获取团期
				String pid = code.substring(2, inx1);
				List<GroupDate> gs = customService.getGroupList(
						"where productId=" + pid, 10, 0, 200);
				String gList = "<table calss='table'>";
				String tmpl = "<tr>";
				// <!-- <h5 style='font-size: 20px; margin-top: 10px;'></h5> -->

				tmpl += "<td>%s</td>";

				tmpl += "<td><a style='background-color: #f60; color: white;' class='btn btn-lg active' ";
				tmpl += " href='http://www.xdujia.com/custom/order?productID=%s&gDate=%s'>%s发团&nbsp;报名";
				tmpl += " </a></td></tr>";
				for (int i = 0; gs != null && i < gs.size(); i++) {
					GroupDate gd = gs.get(i);
					String d[] = gd.getStartDate().split("-");
					gList += String
							.format(tmpl,
									gd.getProductName()
											+ (gd.getBookCount() == gd
													.getTotalCount() ? "（已满）"
													: ""), pid, gd
											.getStartDate(), d[1] + "月" + d[2]
											+ "日");
				}
				txt = txt.replace(code, gList + "</table>");
			} else {
				// txt = txt.replaceAll(code, "");
				break;
			}

		}
		return txt;
	}

	@RequestMapping(value = "/shareData", method = RequestMethod.GET)
	@ResponseBody
	public String shareData(HttpServletRequest request,
			@RequestParam(value = "custId", required = false) String custId,
			@RequestParam(value = "page", required = false) String page) {
		List<ReqMessage> msgs = this.messageService.getShareMsg2(custId == null
				|| custId.length() == 0 ? 0 : Integer.parseInt(custId), page);
		Gson g = new Gson();
		return g.toJson(msgs);
	}

	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public ModelAndView share(HttpServletRequest request,
			@RequestParam(value = "custId", required = false) String custId,
			@RequestParam(value = "page", required = false) String page) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "custom/share");
		List<ReqMessage> msgs = this.messageService.getShareMsg(custId == null
				|| custId.length() == 0 ? 0 : Integer.parseInt(custId), page);
		this.mav.addObject("msgList", msgs);
		this.mav.addObject("custId", custId);
		int itemCount = 0;
		if (msgs != null)
			itemCount = msgs.size();
		this.doPager(page == null ? 0 : Integer.parseInt(page), itemCount, 20);
		return this.mav;
	}

	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public ModelAndView moreActicles(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 1);
		int length = 20;
		int pageNo = 0;
		if (page != null)
			pageNo = Integer.parseInt(page);
		List<SpecialBean> sb = this.messageService.findSpecial(
				" toUserName='mgzj' ", pageNo, length);
		Gson g = new Gson();
		for (SpecialBean s : sb) {
			SpecialInfo[] si = g.fromJson(s.getSpecialBody(),
					SpecialInfo[].class);
			s.setImg("blank.png");
			// 检索json字符串）中的第一个图片
			for (SpecialInfo b : si) {
				if (b.getSmart().length() > 0
						&& !b.getSmart().contains("blank.png")
						&& !b.getSmart().contains(
								"7945f640d6e94778e92653bff9ce9706")) {
					s.setImg(b.getSmart());
					break;
				}
			}
		}
		this.mav.addObject("artileList", sb);
		int itemCount = (sb != null ? sb.size() : 0);
		this.doPager(pageNo, itemCount, length);
		this.mav.setViewName(this.preMobile(request) + "custom/articles");
		return this.mav;
	}

	@RequestMapping(value = "/robot", method = RequestMethod.GET)
	public ModelAndView robotActicle(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) {
		this.getMav(request);
		int length = 200;
		int pageNo = 0;
		if (page != null)
			pageNo = Integer.parseInt(page);
		List<SpecialBean> sb = this.messageService.findSpecial(
				" toUserName='mgzj' ", pageNo, length);
		this.mav.addObject("artileList", sb);
		int itemCount = (sb != null ? sb.size() : 0);
		this.doPager(pageNo, itemCount, length);
		this.mav.setViewName("custom/robot");
		return this.mav;
	}

	@RequestMapping(value = "/sitemap", method = RequestMethod.GET)
	public ModelAndView robotActicle() {
		this.getMav();
		int length = 5000;
		int pageNo = 0;
		List<SpecialBean> sb = this.messageService.findSpecial(
				" toUserName='mgzj' ", pageNo, length);
		this.mav.addObject("artileList", sb);

		this.mav.setViewName("sitemap");
		return this.mav;
	}

	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	public ModelAndView recommend(HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 1);
		this.mav.setViewName(this.preMobile(request) + "custom/recommend");
		return this.mav;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String showDetail(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) {
		if (CommonUtils.IsMobile(request))
			return this.REDIRECT + "/show/spec_detail?id=" + id;
		else
			return this.REDIRECT + "/custom/pdetail?id=" + id;
	}

	@RequestMapping(value = "/detail_all", method = RequestMethod.GET)
	public ModelAndView DetailAll(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "custom/detail_all");
		ProductAllDetail pad = this.lineService.getProductInfo(id);
		this.mav.addObject("info", pad);
		List<SpecialBean> sb = getMoreArticle();
		this.mav.addObject("articleList", sb);

		// 获取当月团期日历
		List<GroupMonth> gms = customService.makeGroupCalendar(id,
				pad.getAheadDays());

		this.mav.addObject("groupCanlendars", gms);

		this.mav.addObject("firstGroup", "2015年02月18日");
		return this.mav;
	}

	@RequestMapping(value = "/date/list", method = RequestMethod.GET)
	@ResponseBody
	public String GroupDateList(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) {
		List<GroupMonth> month = new ArrayList<GroupMonth>();

		// 指定月份获得周数、、周日开始是上个月几号

		int totalDays = 35;
		int day = 28;//
		int lastMaxDay = 31;
		int localMaxDay = 31;
		int seg = 0;// 0,上个月；1本月；2小个月,有时候从1开始

		GroupMonth m = new GroupMonth();
		m.setYear(2015);
		m.setMonth(1);
		// 月循环
		for (int i = 0; i < totalDays; i++) {
			GroupDay d = new GroupDay();
			d.setDay(day++);
			d.setPrice(120);
			d.setLocalMon(seg == 1 ? true : false);
			// 计算下一天
			if (day > lastMaxDay && seg == 0) {
				day = 1;
				seg = 1;
			}
			if (day > localMaxDay && seg == 1) {
				day = 1;
				seg = 2;
			}
			m.getDays().add(d);
		}
		month.add(m);
		Gson g = new Gson();
		return g.toJson(month);

	}

	@RequestMapping(value = "/pdetail", method = RequestMethod.GET)
	public ModelAndView ProductDetail(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 2);

		if (id == null)
			id = "115";
		SpecialBean sb = this.messageService.GetSpecial(Integer.parseInt(id));
		if (sb != null) {

			Gson g = new Gson();
			SpecialInfo[] si = g.fromJson(sb.getSpecialBody(),
					SpecialInfo[].class);
			for (int i = 0; i < si.length; i++) {
				String txt = si[i].getSmart_text();
				if (txt == null)
					txt = "";
				txt = this.searchGroupKey(txt);
				si[i].setSmart_text(txt.replaceAll("\n", "<br/>"));

				// si[i].setSmart_text(txt);
			}
			this.mav.addObject("detailList", si);
			this.mav.addObject("mainTitle", sb.getTitle());
			this.mav.addObject("makeTime", sb.getSpecialMaketime());

			this.mav.setViewName(this.preMobile(request) + "custom/detail");
		} else
			this.mav.setViewName(this.preMobile(request) + "custom/detail" + id);
		WeixinAccount acc = this.wxAccountService
				.getWeixinAccountByFromuserName(sb.getToUserName());
		this.mav.addObject(
				"prevSpecial",
				this.messageService.getPrevSpecial(Integer.parseInt(id),
						acc.getWxFromUserName()));
		this.mav.addObject(
				"nextSpecial",
				this.messageService.getNextSpecial(Integer.parseInt(id),
						acc.getWxFromUserName()));
		String keys = this.travelerService.getMetaKeys(String.valueOf(id));
		keys = keys.replace("，", ",");
		this.mav.addObject("metaKeys", keys);

		this.mav.addObject("articleList", getMoreArticle());
		return this.mav;
	}

	@RequestMapping(value = "/private", method = RequestMethod.GET)
	public ModelAndView PrivateCustom(HttpServletRequest request,
			@RequestParam(value = "step", required = false) String step) {
		this.getMav(request);
		this.mav.addObject("msg", "");
		this.mav.addObject("menuIndex", 3);
		if (step == null)
			step = "0";
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		this.mav.setViewName(this.preMobile(request) + "custom/private" + step);
		return this.mav;
	}

	@RequestMapping(value = "/private/show", method = RequestMethod.GET)
	public ModelAndView privateShow(
			@RequestParam(value = "step", required = false) String strStep,
			HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 3);
		int step = 0;
		if (strStep != null)
			step = Integer.parseInt(strStep);
		String sid = request.getSession().getId();
		switch (step) {
		case 0:
			// 获取存储信息
			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		default:
			break;
		}
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		this.mav.setViewName(this.preMobile(request) + "custom/private" + step);
		return this.mav;
	}

	@RequestMapping(value = "/private/submit", method = RequestMethod.POST)
	public ModelAndView privateSubmit(
			@RequestParam(value = "step", required = false) String strStep,
			HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 3);
		int step = 0;
		if (strStep != null)
			step = Integer.parseInt(strStep);
		String sid = request.getSession().getId();
		// sid = "DC527D7EE88BC09839DDBFD96DE5B6E7";
		PersonalCustom personal = null;
		switch (step) {
		case 0:
			this.customService.savePrivate_Dest(sid,
					request.getParameterValues("radioNational"),
					request.getParameterValues("chkCity"),
					request.getParameterValues("goal"));
			step++;
			break;
		case 1:
			personal = this.customService.getPersonal(sid);
			if (personal != null) {
				personal.setStartDate(request.getParameter("startDate"));
				personal.setTripDays(Integer.parseInt(request
						.getParameter("number")));
				this.customService.updatePersonal(personal);
				step++;
			} else {
				this.mav.addObject("msg", "数据异常，请重新填写");
				step = 0;
			}
			break;
		case 2:
			personal = this.customService.getPersonal(sid);
			if (personal != null) {
				personal.setAdultPerson(Integer.parseInt(request
						.getParameter("adultNumber")));
				personal.setChildrenPerson(Integer.parseInt(request
						.getParameter("childrenNumber")));
				personal.setOldPerson(Integer.parseInt(request
						.getParameter("oldNumber")));
				personal.setHotelLevel(Integer.parseInt(request
						.getParameter("hotelLevel")));
				personal.setHotelGeogrophy(request.getParameter("geogrophy"));
				personal.setStandardNumber(Integer.parseInt(request
						.getParameter("standardNumber")));
				personal.setAdaptmentPerson(Integer.parseInt(request
						.getParameter("adaptmentNumber")));
				personal.setKingNumber(Integer.parseInt(request
						.getParameter("kingNumber")));
				this.customService.updatePersonal(personal);
				step++;
			} else {
				this.mav.addObject("msg", "数据异常，请重新填写");
				step = 0;
			}
			break;
		case 3:
			personal = this.customService.getPersonal(sid);
			if (personal != null) {
				String[] airCompany = request.getParameterValues("airCompany");
				String airCompanies = "";
				for (int i = 0; airCompany != null && i < airCompany.length; i++) {
					airCompanies += airCompany[i] + ",";
				}
				personal.setAirCompany(airCompanies);
				personal.setAirClass(Integer.parseInt(request
						.getParameter("radiosClassLevel")));
				personal.setTripMode(Integer.parseInt(request
						.getParameter("radiosTripMode")));
				personal.setAutoModel(request.getParameter("autoModelType"));
				this.customService.updatePersonal(personal);
				step++;
			} else {
				this.mav.addObject("msg", "数据异常，请重新填写");
				step = 0;
			}
			break;
		case 4:
			personal = this.customService.getPersonal(sid);
			if (personal != null) {
				personal.setMealProvider(Integer.parseInt(request
						.getParameter("meat_sel")));
				personal.setMealType(Integer.parseInt(request
						.getParameter("meat_type")));
				String[] elseService = request
						.getParameterValues("elseService");
				String strService = "";
				for (int i = 0; elseService != null && i < elseService.length; i++) {
					strService += elseService[i] + ",";
				}
				personal.setElseService(strService);
				personal.setMemo(request.getParameter("memo"));
				this.customService.updatePersonal(personal);
				step++;
			} else {
				this.mav.addObject("msg", "数据异常，请重新填写");
				step = 0;
			}
			break;
		case 5:
			personal = this.customService.getPersonal(sid);
			if (personal != null) {
				String userName = request.getParameter("userName");
				personal.setUserName(userName);
				String mobile = request.getParameter("userMobile");
				personal.setMobile(mobile);
				personal.setEmail(request.getParameter("userEmail"));
				this.customService.updatePersonal(personal);
				// 发送短信通知
				try {
					this.getProxy().sendSMS(
							"用户：" + userName + "，填写了境外自驾的定制资料,客户联系电话：" + mobile
									+ ",请登录http://xdujia.com/login查看客户详细资料"
							// + mobile
							, "13911531721");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				step++;
				this.mav.addObject("sid", sid);
			} else {
				this.mav.addObject("msg", "数据异常，请重新填写");
				step = 0;
			}
			break;
		default:
			break;
		}
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		this.mav.setViewName(this.preMobile(request) + "custom/private" + step);
		return this.mav;
	}

	/*
	 * 定制的详情
	 */
	@RequestMapping(value = "/mine", method = RequestMethod.GET)
	public ModelAndView mine(HttpServletRequest request,
			@RequestParam(value = "sid", required = true) String sid) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 4);

		PersonalCustom pc = this.customService.getPersonal(sid);
		this.mav.addObject("p", pc);
		this.mav.setViewName(this.preMobile(request) + "custom/mine");
		return this.mav;
	}

	@RequestMapping(value = "/mine_sel", method = RequestMethod.GET)
	public ModelAndView mine_sel(HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 4);
		this.mav.addObject("mineList", this.customService.getPersonals(request
				.getSession().getId(), 0));
		this.mav.setViewName(this.preMobile(request) + "custom/mine_sel");
		return this.mav;
	}

	/*
	 * js获取定制列表
	 */
	@RequestMapping(value = "/mine/list", method = RequestMethod.POST)
	@ResponseBody
	public String mineList(
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "page", required = true) int page) {

		List<PersonalCustom> pcs = this.customService.getPersonalByMobile(
				mobile, page);

		Gson g = new Gson();
		return g.toJson(pcs);
	}

	@RequestMapping(value = "/group/list", method = RequestMethod.POST)
	@ResponseBody
	public String groupList(HttpServletRequest request,
			@RequestParam(value = "productID", required = true) int pid) {
		List<GroupDate> gs = this.customService.getGroupList("where productId="
				+ pid, 10, 0, 200);
		if (gs != null) {
			for (int i = 0; i < gs.size(); i++) {
				try {
					gs.get(i).setProductName(
							java.net.URLEncoder.encode(gs.get(i)
									.getProductName(), "UTF-8"));
					String startDate = gs.get(i).getStartDate();
					String[] d = startDate.split("-");
					gs.get(i).setStartDate(startDate);
					/*
					 * d[0] + java.net.URLEncoder.encode("年", "UTF-8") + d[1] +
					 * java.net.URLEncoder.encode("月", "UTF-8") + d[2] +
					 * java.net.URLEncoder.encode("日", "UTF-8"));
					 */
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Gson g = new Gson();
			return g.toJson(gs);
		} else
			return "";
	}

	@RequestMapping(value = "/lines", method = RequestMethod.GET)
	public ModelAndView lines(HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 1);
		ShowProductInfo[] ps = this.orderMgrService.getProductList(0, 0);

		this.mav.addObject("productList", ps);
		this.mav.setViewName(this.preMobile(request) + "custom/lines");
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		return this.mav;
	}

	@RequestMapping(value = "/group/list", method = RequestMethod.GET)
	public ModelAndView groupList(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 6);
		int pageNo = 0;
		if (page != null)
			pageNo = Integer.parseInt(page);

		List<GroupDate> gd = this.customService
				.getGroupList("", 10, pageNo, 20);
		List<GroupListBean> gb = new ArrayList<GroupListBean>();
		for (int i = 0; gd != null && i < gd.size(); i++) {
			GroupListBean g = new GroupListBean();
			ProductDetail pd = this.orderMgrService.getProductInfo(gd.get(i)
					.getProductId(), gd.get(i).getStartDate());
			g.setImg(pd.getProductInfo().getImgURL());
			g.setProductName(gd.get(i).getProductName());
			g.setStartDate(gd.get(i).getStartDate());
			g.setProductId(gd.get(i).getProductId());
			g.setBookCount(gd.get(i).getBookCount());
			g.setTotalCount(gd.get(i).getTotalCount());
			gb.add(g);
		}
		this.mav.addObject("groupList", gb);
		this.mav.setViewName(this.preMobile(request) + "custom/groupList");
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		return this.mav;
	}

	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ModelAndView book(HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 4);
		// 获得全部产品
		// ShowProductInfo[] ps = this.customService.getProductList(0, 0);
		// 获得任性1人团
		ShowProductInfo[] ps0 = this.lineService.getProductList(1, 0);
		// 除任性1人团外的
		ShowProductInfo[] ps1 = this.lineService.getProductList(2, 0);
		// 代码测试
		// List<ProductDetail> pds = this.orderMgrService
		// .getProductInfoByCity("1,2");
		// for (int i = 0; i < ps.length; i++) {
		// List<CityDef> cities = this.leaderService.findCityByPid(ps[i]
		// .getProductID());
		// ps[i].setCities(cities);
		// }
		// 代码测试结束
		this.mav.addObject("aloneList", ps0);
		this.mav.addObject("standardList", ps1);
		this.mav.setViewName(this.preMobile(request) + "custom/book");
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		// 导游列表
		List<TourInfo> tours = this.tourService.search(null, null, null, null,
				false, false, null, 6);
		this.mav.addObject("tourLlist", tours);
		// List<GroupDate> gs = this.customService.getGroupList("", 10, 0);
		// for (int i = 0; gs != null && i < gs.size(); i++) {
		// String startDate = gs.get(i).getStartDate();
		// String[] d = startDate.split("-");
		//
		// gs.get(i).setStartDate(
		// /* d[0] + "年" + */d[1] + "月" + d[2] + "日");
		//
		// gs.get(i).setGroupDate(startDate);
		// }
		// this.mav.addObject("groupList", gs);
		//
		List<SpecialBean> sb = getMoreArticle();
		this.mav.addObject("articleList", sb);

		return this.mav;
	}

	public List<SpecialBean> getMoreArticle() {
		List<SpecialBean> sb = this.messageService.findSpecial(
				" toUserName='mgzj' ", 0, 10);
		Gson g = new Gson();
		for (SpecialBean s : sb) {
			SpecialInfo[] si = g.fromJson(s.getSpecialBody(),
					SpecialInfo[].class);
			// 检索json字符串）中的第一个图片
			for (SpecialInfo b : si) {
				System.out.println(b.getSmart());
				if (b.getSmart().length() > 0
						&& !b.getSmart().contains("blank.png")
						&& !b.getSmart().contains(
								"7945f640d6e94778e92653bff9ce9706")) {
					s.setImg(b.getSmart());
					break;
				}
			}
		}
		return sb;
	}

	/**
	 * 获得相关产品团期日历
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = { "/groupDate" }, method = RequestMethod.POST)
	@ResponseBody
	public String groupDate(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) {

		// 获取当月团期日历
		List<GroupMonth> gms = customService.makeGroupCalendar(id, 10);
		Gson g = new Gson();
		return g.toJson(gms);
	}

	@RequestMapping(value = { "/inviteCodeValidate" }, method = RequestMethod.POST)
	@ResponseBody
	public String inviteCodeValidate(HttpServletRequest request,
			@RequestParam(value = "code", required = true) String code) {

		List<InviteCode> codes = this.travelerService.validateCode(code);
		if (codes != null && codes.size() > 0) {
			Gson g = new Gson();
			return "[" + g.toJson(codes.get(0)) + "]";

		}
		return "";
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public ModelAndView order(HttpServletRequest request,
			@RequestParam(value = "productID", required = false) String pid,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "gdate", required = true) String gDate) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "custom/order");

		// 获取产品信息
		ProductAllDetail pd = lineService.getById(pid);
		this.mav.addObject("pid", pid);
		this.mav.addObject("productName", pd.getProductName());
		this.mav.addObject("startDate", gDate);

		this.mav.addObject("adultCount", 2);
		this.mav.addObject("childrenCount", 0);
		this.mav.addObject("offPrice", 0);
		this.mav.addObject("hotelSpan", 0);

		// 根据订单情况，输出订购团期，客户姓名、人数等信息
		if (orderId != null) {
			OrderInfo oi = this.orderMgrService.getOrders(orderId);
			if (oi != null) {
				// 出发日期
				this.mav.addObject("startDate", oi.getArriveDate());

				// 客户信息
				this.mav.addObject("userName", oi.getUserName());
				this.mav.addObject("userMobile", oi.getUserMobile());
				// 人数信息
				this.mav.addObject("adultCount", oi.getAdultCount());
				this.mav.addObject("childrenCount", oi.getChildrenCount());

				//
				this.mav.addObject("offPrice", oi.getOffPrice());
				this.mav.addObject("inviteCode", oi.getInviteCode());
				this.mav.addObject("hotelSpan", oi.getHotelSpan());
			}
		}

		this.mav.addObject("comments", lineService.getProductList(4));
		return mav;
	}

	@RequestMapping(value = { "/book/save" }, method = RequestMethod.POST)
	@ResponseBody
	public String bookSave(
			HttpServletRequest request,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "code", required = true) String inviteCode,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "userMobile", required = true) String userMobile,
			@RequestParam(value = "product", required = true) int productId,
			@RequestParam(value = "selGroupDate", required = true) String selGroupDate,
			@RequestParam(value = "adultCount", required = true) int adultCount,
			@RequestParam(value = "childrenCount", required = true) int childrenCount,
			@RequestParam(value = "agreeValue", required = true) boolean agree) {

		Traveler member = this.travelerService.findtravlerByMobile(userMobile);
		boolean isNew = false;
		if (member == null) {
			isNew = true;
			// 创建会员
			this.travelerService.saveTraveler(userName,
					userMobile.substring(5), userMobile, "", "ROLE_MEM", "",
					null);
			member = this.travelerService.findtravlerByMobile(userMobile);
		}

		// 计算总人数
		int totalCount = adultCount + childrenCount;
		int childrenBedsCount = 0;
		// 获取产品信息
		ProductAllDetail pd = lineService.getById(String.valueOf(productId));
		String productName = pd.getProductName();

		// 获得团期，进一步获得价格信息
		GroupDate gd = this.orderService.getGroupInfo(productId, selGroupDate);
		// 计算价格
		int aPrice = gd.getAdultPrice();
		int cPrice = gd.getChildPrice();
		int hSpan = gd.getHotelSpanPrice();
		int totalPrice = aPrice * adultCount + childrenCount * cPrice;
		if (agree)
			totalPrice += hSpan;
		// 获取优惠码信息
		InviteCode code = null;
		List<InviteCode> codes = this.travelerService.getInviteList("3",
				inviteCode, 0, 1);
		int offPrice = 0;
		if (codes != null && codes.size() > 0) {
			code = codes.get(0);
			if (code.getCodeStatus() == 0) {
				offPrice = codes.get(0).getOffPrice();
				// 减掉优惠价格
				totalPrice -= offPrice;
			}
		}
		// 保存订单
		orderId = this.orderService.saveOrder(orderId, productId, productName,
				aPrice, cPrice, offPrice, userName, userMobile, totalCount,
				childrenCount, 0, adultCount, childrenBedsCount, selGroupDate,
				member.getUserName(), "", "", totalPrice, inviteCode, hSpan);
		// 更改优惠码状态
		if (orderId != null) {
			// 修改优惠码状态
			if (code != null) {
				code.setCodeStatus(1);
				code.setUsedTime(new Date());
				// code.setMember(member);
				this.travelerService.updateInviteCode(code);
				// 累计发码者收益
				Traveler sender = code.getMember();
				sender.setTotalScore(sender.getTotalScore() + offPrice);
				this.travelerService.updateInviteSender(sender);
			}
			// 给客服人员发送短信
			String customerSMS = String.format(
					"客户：%s；联系电话：%s，预订了产品：%s,报名团期：%s.请及时联系客户。", userName,
					userMobile, productName, selGroupDate);
			// 给客户发送短信
			String smsText = "感谢您预订了冠行旅游的产品，订单号：" + orderId;
			if (offPrice > 0) {
				smsText += ",本次预订优惠" + offPrice + "元，使用的优惠码：" + inviteCode;
			}
			smsText += ",冠行的客服人员将在1小时内与您电话联系，请注意保持通信畅通。";

			try {
				this.getProxy().sendSMS(smsText, userMobile);
				this.getProxy().sendSMS(customerSMS,
						this.myConstant.getCustomerServicePhone());
				if (isNew)// 会员信息
				{
					smsText = "您已经成为冠行会员，请使用姓名:" + userName
							+ "登录，默认密码为手机后六位，冠行会员可以给朋友发送优惠码，凭优惠码报名参团享受500元优惠。";
					this.getProxy().sendSMS(smsText, userMobile);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			return "['"
					+ orderId
					+ "','"
					+ java.net.URLEncoder
							.encode(member.getLoginName(), "UTF-8") + "','"
					+ member.getPassText() + "']";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	@RequestMapping(value = { "order/save" }, method = RequestMethod.POST)
	@ResponseBody
	public String orderSave(
			HttpServletRequest request,
			@RequestParam(value = "productID", required = true) int pid,
			@RequestParam(value = "orderId", required = true) String orderId,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "userMobile", required = true) String userMobile,
			@RequestParam(value = "oldCount", required = true) int oldCount,
			@RequestParam(value = "adultCount", required = true) int adultCount,
			@RequestParam(value = "childrenBedsCount", required = true) int childrenBedsCount,
			@RequestParam(value = "childrenCount", required = true) int childrenCount,
			@RequestParam(value = "startDate", required = true) String startDate) {

		String uname = "";
		// SecurityContextHolder.getContext().getAuthentication().getName();
		ProductDetail pd = this.orderMgrService.getProductInfo(pid, startDate);
		if (pd.getProductInfo() != null) {
			int totalCount = oldCount + adultCount + childrenCount;
			orderId = this.orderMgrService.saveOrder(orderId, pid, pd
					.getProductInfo().getProductName(), pd.getPrice()
					.getLowPrice(), 0, 0, userName, userMobile, totalCount,
					childrenCount, oldCount, adultCount, childrenBedsCount,
					startDate, uname, "", "weixin", 0, "", 0);
			try {
				this.getProxy().sendSMS(
						"客户您好，您已经成功预订‘" + pd.getProductInfo().getProductName()
								+ "’，订单号:" + orderId + ",参团人数：" + totalCount
								+ "人，出发日期:" + startDate + "。", userMobile);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				orderId = "-1";
			}
		} else {
			orderId = "0";
		}
		return orderId;
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about(HttpServletRequest request) {
		this.getMav(request);
		this.mav.addObject("menuIndex", 5);
		this.mav.addObject("isMobile", CommonUtils.IsMobile(request));
		this.mav.setViewName(this.preMobile(request) + "custom/about");
		return this.mav;
	}

	@RequestMapping(value = "/product/search", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView ProductSearch(HttpServletRequest request,
			@RequestParam(value = "city", required = false) String cities,
			@RequestParam(value = "t", required = false) String lineType)
			throws UnsupportedEncodingException {

		if (cities == null)
			cities = "";
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "custom/product/search");
		this.mav.addObject("cities", cities);
		List<CityDef> all_cities = this.leaderService.findCities();
		this.mav.addObject("city_list", all_cities);
		List<ProductType> all_types = this.leaderService.findTypes();
		this.mav.addObject("type_list", all_types);
		// 加载默认的产品
		List<ShowProductInfo> products = new ArrayList<ShowProductInfo>();
		if (lineType != null) {
			lineType = new String(lineType.getBytes("iso-8859-1"), "utf-8");
			products = this.lineService.findProductsBycatalog(lineType,
					request.getContextPath());

		} else if (cities.length() > 0) {
			cities = new String(cities.getBytes("iso-8859-1"), "utf-8");
			String[] city_str = cities.split(",");
			products = this.lineService.search(city_str, null, null, null);

		}

		// 获得产品团期
		for (ShowProductInfo p : products) {
			List<GroupDate> gs = this.customService.getGroupList(
					"where productId=" + p.getProductID(), 10, 0, 1);

			p.setGroupDate((gs != null && gs.size() > 0) ? gs.get(0)
					.getStartDate() : "");
			p.setLowPrice((gs != null && gs.size() > 0) ? gs.get(0)
					.getAdultPrice() : 0);
		}
		this.mav.addObject("productList", products);
		return this.mav;
	}

	@RequestMapping(value = "/tour/search", method = RequestMethod.GET)
	public ModelAndView TourSearch(HttpServletRequest request,
			@RequestParam(value = "city", required = false) String cities)
			throws UnsupportedEncodingException {
		engine.getRepositoryService();
		this.getMav(request);
		/*
		 * this.mav.addObject("city", tourService.dataStatic("static/city"));
		 * this.mav.addObject("lang", tourService.dataStatic("static/lang"));
		 * this.mav.addObject("service",
		 * tourService.dataStatic("static/service"));
		 * this.mav.addObject("scenic",
		 * tourService.dataStatic("static/scenic"));
		 */
		if (cities == null)
			cities = "";
		this.mav.setViewName(this.preMobile(request) + "custom/tour/search");

		List<TourInfo> tours = new ArrayList<TourInfo>();
		if (cities.length() > 0) {
			cities = new String(cities.getBytes("iso-8859-1"), "utf-8");
			String[] city_str = cities.split(",");
			this.mav.addObject("cities", city_str);
			tours = this.tourService.search(city_str, null, null, null, false,
					false, null, 50);

		}

		this.mav.addObject("tourList", tours);

		return this.mav;
	}

	@RequestMapping(value = "/tour/smart", method = RequestMethod.POST)
	@ResponseBody
	public String TourSmart(
			HttpServletRequest request,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "scenic", required = false) String scenic,
			@RequestParam(value = "service", required = false) String service,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "auto", required = false) String auto,
			@RequestParam(value = "design", required = false) String design,
			@RequestParam(value = "startKey", required = false) String startKey,
			@RequestParam(value = "count", required = false) String count

	) {

		String[] cities = null;
		if (city != null && city.length() > 0)
			cities = city.split(",");
		String[] scenics = null;
		if (scenic != null && scenic.length() > 0)
			scenics = scenic.split(",");
		String[] services = null;
		if (service != null && service.length() > 0)
			services = service.split(",");
		String[] language = null;
		if (lang != null && lang.length() > 0)
			language = lang.split(",");
		boolean isAuto = Boolean.valueOf(auto);
		boolean isPlan = Boolean.valueOf(design);
		int n = Integer.parseInt(count);
		List<TourInfo> tours = this.tourService.search(cities, scenics,
				services, language, isAuto, isPlan,
				startKey.length() > 0 ? startKey : null, n);
		for (TourInfo t : tours) {
			try {
				t.setNickName(java.net.URLEncoder.encode(t.getNickName(),
						"UTF-8"));
				t.setServiceCities(java.net.URLEncoder.encode(
						t.getServiceCities(), "UTF-8"));
				t.setIntroduce(java.net.URLEncoder.encode(t.getIntroduce(),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson g = new Gson();
		return g.toJson(tours);
	}

	@RequestMapping(value = "/product/smart", method = RequestMethod.POST)
	@ResponseBody
	public String ProductSmart(HttpServletRequest request,

	@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "scenics", required = false) String scenic,
			@RequestParam(value = "attach", required = false) String attach) {

		// List<ShowProductInfo> ps = this.getProduct(cities, startDate, fee,
		// pType, 0, 20);
		String[] cities = null;
		if (city != null && city.length() > 0)
			cities = city.split(",");
		String[] types = null;
		if (type != null && type.length() > 0)
			types = type.split(",");
		String[] scenics = null;
		if (scenic != null && scenic.length() > 0)
			scenics = scenic.split(",");
		List<ShowProductInfo> ps = lineService.search(cities, scenics, types,
				attach);

		for (ShowProductInfo p : ps) {
			// p.setTour(tour);
			try {
				// 获取导游信息
				List<Tour> ts = this.leaderService.findTourByPid(p
						.getProductID());
				for (Tour t : ts) {
					t.setNickName(java.net.URLEncoder.encode(t.getNickName(),
							"UTF-8"));
					t.setIntroduce(java.net.URLEncoder.encode(t.getIntroduce(),
							"UTF-8"));
				}
				p.setTour(ts);
				// 获得产品团期
				List<GroupDate> gs = this.customService.getGroupList(
						"where productId=" + p.getProductID(), 10, 0, 1);

				p.setGroupDate((gs != null && gs.size() > 0) ? gs.get(0)
						.getStartDate() : "");
				p.setLowPrice((gs != null && gs.size() > 0) ? gs.get(0)
						.getAdultPrice() : 0);
				// 获取城市信息
				// List<CityDef> cityList = this.leaderService.findCityByPid(p
				// .getProductID());
				// for (CityDef c : cityList) {
				// c.setCityName(java.net.URLEncoder.encode(c.getCityName(),
				// "UTF-8"));
				// }
				// p.setCities(cityList);
				if (p.getCities_str() != null)
					p.setCities_str(java.net.URLEncoder.encode(
							p.getCities_str(), "UTF-8"));
				else
					p.setCities_str("");

				// 获取产品线路类型信息
				List<ProductType> productTypes = this.leaderService
						.findTypesByPid(p.getProductID());
				for (ProductType t : productTypes) {
					t.setCatalogName(java.net.URLEncoder.encode(
							t.getCatalogName(), "UTF-8"));
				}
				p.setProductType(productTypes);
				// 将产品名称和介绍进行转码
				p.setName(java.net.URLEncoder.encode(p.getName(), "UTF-8"));
				if (p.getIntroduce() != null)
					p.setIntroduce(java.net.URLEncoder.encode(p.getIntroduce(),
							"UTF-8"));
				String d = p.getDays();
				d = d.replace(" ", "&nbsp;");
				p.setDays(java.net.URLEncoder.encode(d, "UTF-8"));
				p.setLineType(java.net.URLEncoder.encode(p.getLineType(),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson g = new Gson();
		return g.toJson(ps);
	}

	private List<ShowProductInfo> getProduct(String cities, String startDate,
			String fee, String pType, int page, int pageCount) {
		// //this.orderMgrService.getProductList(3, 0);

		String where = "";
		String query = "from ProductInfo p, ProductTypeLink tl, ProductCityLink cl, ProductTourLink tourl";
		// 费用
		if (fee != null && fee.length() > 0 && fee.compareTo("0") != 0) {
			List<ProductPriceLine> prices = this.orderMgrService
					.getProductByPrice(fee);
			String pids = "";
			for (ProductPriceLine p : prices) {
				pids += p.getProductId() + ",";
			}
			if (pids.length() > 0) {
				where += (where.length() > 0 ? " and " : " where ")
						+ " p.id in (" + pids.substring(0, pids.length() - 1)
						+ ")";
			}
		}
		// 城市
		if (cities != null && cities.length() > 0) {
			// 获取城市列表ID
			List<CityDef> cs = this.leaderService.findCitiesByName(cities);
			String cityIds = "";
			for (CityDef c : cs) {
				cityIds += c.getId() + ",";
			}
			if (cityIds.length() > 0) {
				where += (where.length() > 0 ? " and " : " where ")
						+ "cl.productId=p.id and cl.cityId in ("
						+ cityIds.substring(0, cityIds.length() - 1) + ")";
			}
		}
		// 线路类型
		if (pType != null && pType.length() > 0) {
			// 获取城市列表ID
			List<ProductType> ts = this.leaderService.findTypesByName(pType);
			String tIds = "";
			for (ProductType t : ts) {
				tIds += "" + t.getId() + ",";
			}
			if (tIds.length() > 0) {
				where += (where.length() > 0 ? " and " : " where ")
						+ "tl.productId=p.id and tl.typeId in ("
						+ tIds.substring(0, tIds.length() - 1) + ")";
			}
		}
		// 出发日期
		if (startDate != null && startDate.length() > 0) {
			// 获取城市列表ID
			List<GroupDate> gds = this.customService.getGroupList(
					" where substring(startDate, 1, 7) = '" + startDate + "'",
					10, 0, 200);
			String pIds = "";
			for (GroupDate d : gds) {
				pIds += d.getProductId() + ",";
			}
			if (pIds.length() > 0) {
				where += (where.length() > 0 ? " and " : " where ")
						+ " p.id in (" + pIds.substring(0, pIds.length() - 1)
						+ ")";
			}
		}
		if (true/* where.length() > 0 */) {
			query += where + " group by p.id ";
			return this.orderMgrService.ProductList(query, page, pageCount);
		} else
			return new ArrayList<ShowProductInfo>();
	}

	@RequestMapping(value = "/city/list", method = RequestMethod.GET)
	@ResponseBody
	public String getCity(HttpServletRequest request) {
		List<CityDef> cities = this.leaderService.findCities();
		for (CityDef c : cities) {
			try {
				c.setCityName(java.net.URLEncoder.encode(c.getCityName(),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson g = new Gson();
		return g.toJson(cities);
	}
}
