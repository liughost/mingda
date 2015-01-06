package com.mdnet.travel.core.controller;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mdnet.travel.core.model.Tour;
import com.mdnet.travel.core.model.TourItems;
import com.mdnet.travel.core.model.TourOrder;
import com.mdnet.travel.core.service.ILeaderService;

@Controller
@RequestMapping("/tour")
public class TourController extends BaseController {
	@Resource(name = ILeaderService.SERVICE_NAME)
	private ILeaderService leaderService;

	@RequestMapping(value = { "detail" }, method = RequestMethod.GET)
	public ModelAndView leaderDetail(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String custId) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "leader/detail");
		Tour tour = leaderService.findTour(Integer.parseInt(custId));
		TourItems tourItems = leaderService.findTourItems(Integer
				.parseInt(custId));
		this.mav.addObject("tour", tour);
		this.mav.addObject("tourItems", tourItems);
		this.mav.addObject("commentsList",
				this.leaderService.findComments(Integer.parseInt(custId), 0));
		return this.mav;
	}

	@RequestMapping(value = { "addComments" }, method = RequestMethod.POST)
	@ResponseBody
	public String addComments(HttpServletRequest request,
			@RequestParam(value = "custId", required = true) int custId,
			@RequestParam(value = "nick", required = true) String nick,
			@RequestParam(value = "context", required = true) String context) {
		int ret = this.leaderService.addComments(getIpAddr(request), custId,
				nick, context);
		return String.valueOf(ret);
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
