package com.mdnet.travel.core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zhinet.travel.pojo.basepojo.ScenicInfo;
import net.zhinet.travel.pojo.basepojo.TicketInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.deanx.CouchDB;

import com.mdnet.travel.core.dao.ReqMessageDAO;
import com.mdnet.travel.core.model.Traveler;
import com.mdnet.travel.core.model.ValidateCode;
import com.mdnet.travel.core.utils.CommonUtils;
import com.mdnet.travel.core.utils.EncryptSpring;
import com.mdnet.travel.core.vo.ExceptionInfo;
import com.mdnet.travel.core.vo.RegistBean;
import com.mdnet.travel.core.vo.SignupCheck;

@Controller
public class HomeController extends BaseController {

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request) {

		if (CommonUtils.IsMobile(request))
			return this.REDIRECT + this.myConstant.getMobileHomePage();// /mobilehome";
		else
			return this.REDIRECT + this.myConstant.getPCHomePage();// "/custom/book";
	}

	@RequestMapping(value = { "mobilehome" }, method = RequestMethod.GET)
	public ModelAndView showHomePage() {
		String cityName = "";
		String commentDate = "";

		String provinceName = "";
		List<ScenicInfo> viewList = new ArrayList<ScenicInfo>();
		try {
			viewList = getProxy().getRecommendViews(cityName, commentDate,
					provinceName);
			// viewList.get(0).set
		} catch (RemoteException e) {
			viewList = null;
		}

		for (ScenicInfo view : viewList) {
			int minMoney = getMinValue(view.getScenicTickets());
			view.setScenicSeaLevel(minMoney);
		}

		this.mav = new ModelAndView();
		this.mav.setViewName("home");

		this.mav.addObject("view_list", viewList);
		return mav;
	}

	private int getMinValue(TicketInfo[] ticketInfos) {
		int minMoney = 0;

		if (ticketInfos != null && ticketInfos.length > 0) {

			minMoney = ticketInfos[0].getSalePrice();

			for (int i = 1; i < ticketInfos.length; i++) {
				// System.out.println(ticketInfos[i].getSalePrice());
				if (ticketInfos[i].getSalePrice() < minMoney) {

					int temp = 0;
					temp = minMoney;
					minMoney = ticketInfos[i].getSalePrice();
					ticketInfos[i].setSalePrice(temp);
				}
			}

			// System.out.println(minMoney+"-----------");

		}

		return minMoney;
	}

	@RequestMapping("/signup")
	public ModelAndView signupUI(HttpServletRequest request) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "signup");
		return this.mav;
	}

	@RequestMapping("/login")
	public ModelAndView loginUI(HttpServletRequest request) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "login");
		return this.mav;
	}

	@RequestMapping(value = { "forget" }, method = RequestMethod.GET)
	public ModelAndView forget(HttpServletRequest request) {
		this.getMav(request);
		this.mav.setViewName(this.preMobile(request) + "forget");
		return this.mav;
	}

	@RequestMapping("/resetPwd")
	@ResponseBody
	public String restePwd(
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "validateCode", required = true) String validateCode,
			@RequestParam(value = "pass", required = true) String pass,
			@RequestParam(value = "confirmPass", required = true) String confirmPass)
			throws UnsupportedEncodingException {
		ValidateCode code = this.travelerService.findValidCodeByMobile(mobile
				.trim());
		Date now = new Date();
		long span = now.getTime() / 1000
				- (code == null ? 0 : code.getCreateTime().getTime()) / 1000;
		if (code == null || span > 2 * 60 * 60 || code.getStatus() != 0
				|| !code.getValidCode().equals(validateCode.trim())) {
			return "-1";// java.net.URLEncoder.encode("对不起，验证码错误，请确认后重试!",
						// "UTF-8")

		}

		if (pass.compareTo(confirmPass) != 0) {

			return "-2";// java.net.URLEncoder.encode("对不起，密码和确认密码不一致，请重新输入!",

		}
		Traveler traveler = this.travelerService.findtravlerByMobile(mobile);

		traveler.setPassText(pass);
		traveler.setPassword(EncryptSpring.md5_32(pass));
		this.travelerService.updateTraveler(traveler);
		return "0";// java.net.URLEncoder.encode("密码已经重新设置，请用新密码登录!", "UTF-8")
	}

	@RequestMapping("/dosignup")
	@ResponseBody
	public String signup(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "pass", required = true) String pass,
			@RequestParam(value = "confirmPass", required = true) String confirm_pass,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "validCode", required = true) String validCode)
			throws UnsupportedEncodingException {

		String message = "";

		if (null != this.travelerService.findMobile(username.trim())) {
			return java.net.URLEncoder.encode("对不起，用户名已被占用!", "UTF-8");
		}

		ValidateCode code = this.travelerService.findValidCodeByMobile(mobile
				.trim());
		Date now = new Date();
		long span = now.getTime() / 1000
				- (code == null ? 0 : code.getCreateTime().getTime()) / 1000;
		if (code == null || span > 2 * 60 * 60 || code.getStatus() != 0
				|| !code.getValidCode().equals(validCode.trim())) {
			return java.net.URLEncoder
					.encode("对不起，注册失败,验证码错误，请确认后重试!", "UTF-8");
		}

		if (pass.trim().equals(confirm_pass.trim())) {
			Boolean flag = (this.travelerService.saveTraveler(username, pass,
					mobile, "", Traveler.ROLE_MEM, "", null) > 0 ? true : false);
			if (flag) {
				message = "注册成功,请登入"+this.myConstant.getCompanyName()+"!";
				code.setStatus(1);
				this.travelerService.update(code);
			} else {
				message = "对不起，系统繁忙,请稍后重试哦!";
			}
		} else {
			message = "对不起，注册失败,密码和确认密码不一致，请重新输入！";
		}

		return java.net.URLEncoder.encode(message, "UTF-8");
	}

	@RequestMapping(value = "/check/username", method = RequestMethod.POST)
	@ResponseBody
	public String check_username(
			@RequestParam(value = "username", required = true) String username)
			throws UnsupportedEncodingException {

		if (null != this.travelerService.findMobile(username.trim())) {
			return java.net.URLEncoder.encode("用户名已被占用!", "UTF-8");
		} else {
			return java.net.URLEncoder.encode("", "UTF-8");
		}

	}

	@RequestMapping(value = "/signup/sendValidCode", method = RequestMethod.POST)
	@ResponseBody
	public String sendValidCode(
			@RequestParam(value = "mobile", required = true) String userMobile) {

		String validCode = CommonUtils.getRandomSixNum();
		String validContent = "用户您好。"+this.myConstant.getCompanyName()+"的验证码为:" + validCode
				+ ",此验证码2小时内有效，请妥善保管!";

		this.travelerService.saveValidCode(userMobile, validCode);
		try {
			getProxy().sendSMS(validContent, userMobile.trim());
		} catch (RemoteException e) {
			return "0";
		}
		return "1";
	}

	/**
	 * 404
	 */
	@RequestMapping("/exception/notfound")
	public ModelAndView notfound() {
		this.mav = new ModelAndView();
		this.mav.setViewName("404_or_500");
		this.mav.addObject("exception", new ExceptionInfo("当前页已迁移！",
				"/image/client/404.jpg"));
		return this.mav;
	}

	/**
	 * 500
	 */
	@RequestMapping("/exception/busy")
	public ModelAndView busy() {
		this.mav = new ModelAndView();
		this.mav.setViewName("404_or_500");
		this.mav.addObject("exception", new ExceptionInfo("系统繁忙，请稍后重试！",
				"/image/client/500.jpg"));
		return this.mav;
	}
}
