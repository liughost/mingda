package com.mdnet.travel.core.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * java抓取网络图片
 * 
 * @author swinglife
 * 
 */
public class CatchImage {

	// 地址
	private static final String URL = "http://www.csdn.net";
	// 编码
	private static final String ECODING = "UTF-8";
	// 获取img标签正则
	private static final String IMGURL_REG = "<img(.*)src=(.*?)[^>]*?>";
	// 获取src路径的正则
	private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";

	public static void main(String[] args) throws Exception {
		CatchImage cm = new CatchImage();
		// 获得html文本内容
		String HTML = cm.getHTML(URL);
		// 获取图片标签
		// HTML = HTML.replaceAll("data-src=", "xxx=");
		List<String> imgUrl = cm.getImageUrl(HTML);
		// 获取图片src地址
		List<String> imgSrc = cm.getImageSrc(imgUrl);
		// 下载图片
		cm.Download(imgSrc.get(0), "d:/aa");
	}

	/***
	 * 获取HTML内容
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getHTML(String url) throws Exception {
		URL uri = new URL(url);
		URLConnection connection = uri.openConnection();
		InputStream in = connection.getInputStream();
		byte[] buf = new byte[1024];
		int length = 0;
		StringBuffer sb = new StringBuffer();
		while ((length = in.read(buf, 0, buf.length)) > 0) {
			sb.append(new String(buf, ECODING));
		}
		in.close();
		return sb.toString();
	}

	/***
	 * 获取ImageUrl地址
	 * 
	 * @param HTML
	 * @return
	 */
	public List<String> getImageUrl(String HTML) {
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
		List<String> listImgUrl = new ArrayList<String>();
		while (matcher.find()) {
			String imgTag = matcher.group();
			listImgUrl.add(imgTag);
		}
		return listImgUrl;
	}

	/***
	 * 获取ImageSrc地址
	 * 
	 * @param listImageUrl
	 * @return
	 */
	public List<String> getImageSrc(List<String> listImageUrl) {
		List<String> listImgSrc = new ArrayList<String>();
		for (String image : listImageUrl) {
			Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
			boolean isAppend = false;
			String img = "";
			while (matcher.find()) {
				String imgUrl = matcher.group().substring(0,
						matcher.group().length() - 1);
				if (imgUrl.substring(0, 7).compareTo("http://") == 0
						&& !isAppend) {
					listImgSrc.add(imgUrl);
					// isAppend = true;
				}
			}
		}
		return listImgSrc;
	}

	/***
	 * 下载图片
	 * 
	 * @param listImgSrc
	 */
	public int Download(String url, String fileName) {
		try {

			URL uri = new URL(url);
			URLConnection connection = uri.openConnection();

			InputStream in = connection.getInputStream();

			FileOutputStream fo = new FileOutputStream(new File(fileName));
			byte[] buf = new byte[1024];
			int length = 0;
			int total_size = 0;
			System.out.println("开始下载:" + url);
			while ((length = in.read(buf, 0, buf.length)) != -1) {
				fo.write(buf, 0, length);
				total_size += length;
			}
			in.close();
			fo.close();
			System.out.println(fileName + "下载完成");
			return total_size;

		} catch (Exception e) {
			System.out.println("下载失败");
			return 0;
		}
	}

	/*
	 * public int Download(String url, String fileName) { try {
	 * 
	 * // String imageName = url.substring(url.lastIndexOf("/") + 1, //
	 * url.length()); URL uri = new URL(url);
	 * 
	 * InputStream in = uri.openStream(); FileOutputStream fo = new
	 * FileOutputStream(new File(fileName)); byte[] buf = new byte[1024]; int
	 * length = 0; int total_size = 0; System.out.println("开始下载:" + url); while
	 * ((length = in.read(buf, 0, buf.length)) != -1) { fo.write(buf, 0,
	 * length); total_size += length; } in.close(); fo.close();
	 * System.out.println(fileName + "下载完成"); return total_size;
	 * 
	 * } catch (Exception e) { System.out.println("下载失败"); return 0; } }
	 */

}
