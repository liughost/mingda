package com.mdnet.travel.core.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mdnet.travel.core.common.CatchImage;
import com.mdnet.travel.core.model.Media;
import com.mdnet.travel.core.model.WeixinAccount;
import com.mdnet.travel.core.service.IMessageService;
import com.mdnet.travel.core.utils.MD5;
import com.mdnet.travel.core.vo.SpecialInfo;

@Controller
@RequestMapping("/media")
public class MediaController extends BaseController {
	@Resource(name = IMessageService.SERVICE_NAME)
	private IMessageService messageService;

	/**
	 * 上载文件并入库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("title") String title,
	/* @RequestParam("mediaFile") MultipartFile file, */
	HttpServletRequest request) {
		String path = request.getSession().getServletContext()
				.getRealPath("/resources/media");
		MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = mhs.getFiles("mediaFile");
		for (int i = 0; i < files.size(); i++) {
			// 保存文件，并入库
			WeixinAccount acc = this.getWeixinAccount();
			int id = this.messageService.upload((title.length() > 0 ? title
					: files.get(i).getOriginalFilename()), files.get(i), path,
					acc.getWxFromUserName());
		}
		return this.REDIRECT + "list?page=0&t=id&s=";
	}

	/**
	 * 删除媒体记录及实体文件
	 * 
	 * @param page
	 *            页号，从0开始
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@RequestParam(value = "id", required = true) int id) {
		int ret = this.messageService.deleteMedia(id);
		return String.valueOf(ret);
	}

	/**
	 * 浏览媒体
	 * 
	 * @param page
	 *            页号，从0开始
	 * @param t
	 *            搜索类型；id标识search填写的id号，title标识search为标题中的关键字
	 * @param search
	 *            搜索的内容
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "t", required = true) String t,
			@RequestParam(value = "s", required = true) String s) {
		String search = "";
		try {
			search = new String(s.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.mav = new ModelAndView();
		WeixinAccount acc = this.getWeixinAccount();
		List<Media> medias = this.messageService.findMedia(page, 20, t, search,
				acc.getWxFromUserName());
		this.mav.addObject("media_list", medias);

		int prevPage = page - 1;
		if (page <= 0) {
			page = 0;
			prevPage = 0;
		}
		int nextPage = page;
		if (medias != null && medias.size() > 0)
			nextPage = page + 1;
		this.mav.addObject("pageNo", page);
		this.mav.addObject("prevPage", prevPage);
		this.mav.addObject("nextPage", nextPage);
		this.mav.addObject("search", search);
		this.mav.setViewName("media/list");

		return mav;// 回到页面编辑
	}

	/**
	 * 媒体新增页面
	 * 
	 * @param page
	 *            页号，从0开始
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUI() {
		this.mav = new ModelAndView();
		this.mav.setViewName("media/add");
		return mav;// 回到页面编辑
	}

	/**
	 * 搜索媒体，返回媒体列表
	 * 
	 * @param page
	 * @param search
	 * @return 包含标题和媒体地址的列表
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public String search(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "s", required = true) String search) {
		String s = "";
		try {
			s = new String(search.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		search = s;
		List<Media> medias = null;
		try {
			WeixinAccount acc = this.getWeixinAccount();
			medias = this.messageService.findMedia(page, 8, "title",
					java.net.URLDecoder.decode(search, "UTF-8"),
					acc.getWxFromUserName());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (medias != null && medias.size() > 0) {
			for (int i = 0; i < medias.size(); i++) {
				try {
					medias.get(i).setTitle(
							java.net.URLEncoder.encode(
									medias.get(i).getTitle(), "UTF-8"));
					medias.get(i).setOriginalFileName(
							java.net.URLEncoder.encode(medias.get(i)
									.getOriginalFileName(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Gson g = new Gson();
			return g.toJson(medias);
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/update/action", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "title", required = true) String title) {

		List<Media> media = this.messageService.findMedia(0, 1, "id", id, null);

		String img_url = "";
		String makeTime = "";
		if (media != null && media.size() > 0) {

			img_url = media.get(0).getMedia_url();
			makeTime = media.get(0).getMaketime();
		}
		int ret = this.messageService.UpdateMedia(id, title);

		this.mav = new ModelAndView();
		this.mav.addObject("makeTime", makeTime);
		this.mav.addObject("ret", ret);
		this.mav.addObject("msg", ret == 0 ? "更新成功" : "更新失败");
		this.mav.addObject("id", id);
		this.mav.addObject("title", title);
		this.mav.addObject("img_url", img_url);
		this.mav.setViewName("media/update");

		return mav;// 回到页面编辑
	}

	@RequestMapping(value = "/fromPage", method = RequestMethod.GET)
	public ModelAndView fromPageUI() {
		this.mav = new ModelAndView();
		this.mav.setViewName("media/fromPage");
		return this.mav;
	}

	@RequestMapping(value = "/downloadImg", method = RequestMethod.POST)
	@ResponseBody
	public String downloadImg(HttpServletRequest request,
			@RequestParam(value = "urls", required = true) String[] urls,
			@RequestParam(value = "title", required = true) String title) {
		String path = request.getSession().getServletContext()
				.getRealPath("/resources/media");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String mediaPath = sdf.format(new Date());
		path = path + "/" + mediaPath;
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
		WeixinAccount acc = this.getWeixinAccount();
		String weixinId = acc.getWxFromUserName();
		CatchImage cm = new CatchImage();
		int count = 0;
		for (String url : urls) {
			String md5 = MD5.getMD5(url.getBytes());
			String fileName = path + "/" + md5;
			int length = cm.Download(url, fileName);
			// 保存到数据库
			this.messageService.savePageImage(mediaPath, md5, fileName, length,
					title, weixinId);
			count++;
		}
		return String.valueOf(count);
	}

	@RequestMapping(value = "/createArticle", method = RequestMethod.POST)
	@ResponseBody
	public String createArticle(
			@RequestParam(value = "urls", required = true) String[] urls,
			@RequestParam(value = "title", required = true) String title) {
		WeixinAccount acc = this.getWeixinAccount();
		String weixinId = acc.getWxFromUserName();
		int id = this.messageService.AddSpecial(title, "", "left", weixinId);

		SpecialInfo[] content_list = new SpecialInfo[urls.length+1];
		content_list[0] = new SpecialInfo();
		content_list[0].setSmart("");
		content_list[0].setHashCode("".hashCode());
		content_list[0].setIs_show(1);
		content_list[0].setSmart_text("");
		for (int i = 1; i <= urls.length; i++) {
			content_list[i] = new SpecialInfo();
			content_list[i].setSmart(urls[i-1]);
			content_list[i].setHashCode(urls[i-1].hashCode());
			content_list[i].setIs_show(1);
			content_list[i].setSmart_text("");
		}
		Gson g = new Gson();
		String content = g.toJson(content_list);
		this.messageService.updateSpecial(id, title, "", "left", content);
		return String.valueOf(id);
	}

	@RequestMapping(value = "/getImg", method = RequestMethod.POST)
	@ResponseBody
	public String GetImgfromPage(
			@RequestParam(value = "url", required = true) String url) {
		CatchImage cm = new CatchImage();
		String HTML = "";
		try {
			HTML = cm.getHTML(url);
			// while (true) {
			// int inx = HTML.indexOf(" src");
			// if (inx < 0)
			// break;
			// else {
			// String tag = HTML.substring(inx,inx+4);
			// System.out.println(tag);
			// String first = HTML.substring(0, inx);
			// String second = HTML.substring(inx + 4);
			// HTML = first +" mysrc"+ second;
			// }
			// }
			// HTML= HTML.replace('-', 'x');
			// HTML= HTML.replaceAll("dataxsrc=", "xxx=");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取图片标签
		List<String> imgUrl = cm.getImageUrl(HTML);
		// 获取图片src地址
		List<String> imgSrc = cm.getImageSrc(imgUrl);

		Gson g = new Gson();
		return g.toJson(imgSrc);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView updateUI(
			@RequestParam(value = "id", required = true) String id) {

		String title = "";
		String img_url = "";
		String makeTime = "";
		List<Media> media = this.messageService.findMedia(0, 1, "id", id, null);
		if (media != null && media.size() > 0) {
			title = media.get(0).getTitle();
			img_url = media.get(0).getMedia_url();
			makeTime = media.get(0).getMaketime();
		}
		this.mav = new ModelAndView();
		this.mav.addObject("makeTime", makeTime);
		this.mav.addObject("id", id);
		this.mav.addObject("title", title);
		this.mav.addObject("msg", "");
		this.mav.addObject("img_url", img_url);
		this.mav.setViewName("media/update");
		return mav;// 回到页面编辑
	}
}
