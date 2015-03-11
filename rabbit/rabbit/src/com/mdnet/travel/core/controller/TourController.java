package com.mdnet.travel.core.controller;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.mdnet.travel.core.dao.TourComments;
import com.mdnet.travel.core.dao.TourInfo;
import com.mdnet.travel.core.model.GroupMonth;
import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.core.model.TourOrder;
import com.mdnet.travel.core.model.Traveler;
import com.mdnet.travel.core.service.ICustomService;
import com.mdnet.travel.core.service.ILeaderService;
import com.mdnet.travel.core.service.impl.CommentsService;
import com.mdnet.travel.core.service.impl.TourService;

@Controller
@RequestMapping("/tour")
public class TourController extends BaseController {
	protected final int commentPageCount = 15;
	@Resource(name = ILeaderService.SERVICE_NAME)
	private ILeaderService leaderService;
	@Resource(name = TourService.SERVICE_NAME)
	private TourService tourService;
	@Resource(name = ICustomService.SERVICE_NAME)
	protected ICustomService customService;
	@Resource(name = CommentsService.SERVICE_NAME)
	protected CommentsService commentsService;

	@RequestMapping(value = { "comment/more" }, method = RequestMethod.POST)
	@ResponseBody
	public String commentMore(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "startKey", required = true) String startKey)
			throws UnsupportedEncodingException {
		List<TourComments> tcs = this.commentsService.search(id, startKey,
				commentPageCount + 1);
		for (TourComments t : tcs) {
			t.setNickName(java.net.URLEncoder.encode(t.getNickName(), "UTF-8"));
			t.setContext(java.net.URLEncoder.encode(t.getContext(), "UTF-8"));
		}
		Gson g = new Gson();
		return g.toJson(tcs);
	}

	@RequestMapping(value = { "detail" }, method = RequestMethod.GET)
	public ModelAndView leaderDetail(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id)
			throws ParseException {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "leader/detail");
		TourInfo tour = this.tourService.takeTour(id);
		tour.setFeeDesc(tour.getFeeDesc().replace("\n", "<br/>"));
		tour.setFeeExcept(tour.getFeeExcept().replace("\n", "<br/>"));
		tour.setSupplement(tour.getSupplement().replace("\n", "<br/>"));
		this.mav.addObject("tour", tour);

		List<GroupMonth> gms = customService.makeGroupCalendar(tour.getId(),
				tour.getNickName(), tour.getPrice());

		this.mav.addObject("groupCanlendars", gms);
		List<TourComments> tcs = this.commentsService.search(id, null,
				commentPageCount + 1);
		if (tcs.size() == commentPageCount + 1) {
			TourComments last = tcs.get(commentPageCount);
			this.mav.addObject("nextKey", last.getId());
			tcs.remove(commentPageCount);
		} else
			this.mav.addObject("nextKey", "");
		this.mav.addObject("commentsList", tcs);
		this.mav.addObject("pageCount", commentPageCount);
		this.mav.addObject("commentCount",
				this.commentsService.commentsCount(id));
		return this.mav;
	}

	@RequestMapping(value = { "addComments" }, method = RequestMethod.POST)
	@ResponseBody
	public String addComments(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "tourKey", required = true) String tourKey,
			@RequestParam(value = "context", required = true) String context) {
		TourComments c = this.commentsService.newComment();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setCommentDate(sdf.format(new Date()));
		c.setCommentIP(getIpAddr(request));
		c.setContext(context);
		Traveler t = this.travelerService.findTravelerByUname(username);
		if (t != null) {
			c.setCustId(Integer.parseInt(t.getTravelerId()));
			c.setHeadImg("");
			c.setNickName(t.getLoginName());
		} else {
			c.setCustId(0);
			c.setHeadImg("");
			c.setNickName("匿名");
		}
		c.setTourKey(tourKey);
		c.Add();
		// int ret = this.leaderService.addComments(getIpAddr(request), custId,
		// nick, context);
		return c.getId();
	}

	@RequestMapping(value = { "approve" }, method = RequestMethod.POST)
	@ResponseBody
	public String approve(HttpServletRequest request,
			@RequestParam(value = "commentId", required = true) int commentId) {
		int ret = this.leaderService.approve(commentId);
		return String.valueOf(ret);
	}

	@RequestMapping(value = { "book" }, method = RequestMethod.POST)
	@ResponseBody
	public String tourBook(
			HttpServletRequest request,
			@RequestParam(value = "custId", required = true) int custId,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "linkPhone", required = true) String linkPhone,
			@RequestParam(value = "linkName", required = true) String linkName,
			@RequestParam(value = "memo", required = true) String memo) {
		TourOrder to = new TourOrder();
		to.setCustId(custId);
		to.setStartDate(startDate);
		to.setEndDate(endDate);
		to.setLinkName(linkName);
		to.setLinkPhone(linkPhone);
		to.setMemo(memo);
		this.leaderService.saveTourOrder(to);
		Tour t = this.leaderService.findTour(custId);
		try {
			// 发送通知短信给客户
			getProxy().sendSMS(
					"您好，接收到您预约的从：" + startDate + "到：" + endDate + "止的导游预约申请,导游"
							+ t.getNickName() + " 将尽快与您联系，请保持通信畅通。导游联系电话："
							+ t.getMobile() + ",感谢您的支持！", linkPhone);
			// 发送短信给领队导游
			getProxy().sendSMS(
					"客户：" + linkName + "预约了行程，起始日期：" + startDate + "，截止日期："
							+ endDate + "，请尽快与客户：" + linkPhone + "联系。",
					t.getMobile());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";
	}
}
