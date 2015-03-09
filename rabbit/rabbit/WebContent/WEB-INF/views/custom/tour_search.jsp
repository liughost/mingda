<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.word a {
	min-width: 55px;
}

.car-lbr a {
	min-width: 60px;
	line-height: 30px;
}

.car-lb .word a {
	width: 90px;
}
</style>

<!-- banner STA -->
<div class="bannerk">
	<div class="banner">
		<img src="${context }/resources/rabbit/images/banner-1.jpg" alt="" />
	</div>
</div>
<!-- banner End -->

<!-- 主体内容区 STA -->
<div class="maink">
	<div class="main">
		<div class="car">
			<div class="car-l">
				<div class="car-lt">服务筛选</div>
				<div class="car-lb">
					<ul>
						<li>
							<div class="title">目的地</div>
							<div class="cont">
								<div class="cont-bar">
									<div class="car-lbl">大洲</div>
									<div class="car-lbr">
										<a href="#">北美洲</a>
										<!-- <a href="#">大洋洲</a>  -->
									</div>
								</div>
								<div class="cont-bar">
									<div class="car-lbl">国家</div>
									<div class="car-lbr">
										<a href="#" class="on">美国</a>
										<!-- 
										<a href="#">加拿大</a>
										 -->
									</div>
								</div>
								<div class="cont-bar">
									<div class="car-lbl">目的地</div>
									<div class="car-lbr" id="selCity">
										<a href="javascript:void();"
											onclick="javascript:sel_city(this,'拉斯维加斯')" class="">拉斯维加斯</a>
										<a href="javascript:void();"
											onclick="javascript:sel_city(this,'洛杉矶')" class="">洛杉矶</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'旧金山')" class="">旧金山</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'弗雷斯诺')" class="">弗雷斯诺</a>
										<a href="javascript:void();"
											onclick="javascript:sel_city(this,'佩吉')" class="">佩吉</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'夏威夷')" class="">夏威夷</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'奥兰多')" class="">奥兰多</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'盐湖城')" class="">盐湖城</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'蒙特利')" class="">蒙特利</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'迈阿密')" class="">迈阿密</a> <a
											href="javascript:void();"
											onclick="javascript:sel_city(this,'芝加哥')" class="">芝加哥</a>

									</div>
								</div>
							</div>
						</li>

						<li>
							<div class="title">景点</div>
							<div class="word" id="selScenic">
								<a href="javascript:void();"
									onclick="javascript:sel_scenic(this,'17英里海岸')">17英里海岸</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'66号公路')">66号公路</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'一号公路')">一号公路</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'七哩桥')">七哩桥</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'大峡谷')">大峡谷</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'大提顿国家公园')">大提顿国家公园</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'大沼泽国家公园')">大沼泽国家公园</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'奥特莱斯')">奥特莱斯</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'好莱坞')">好莱坞</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'黄石公园')">黄石公园</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'布莱斯')">布莱斯</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'拉斯维加斯大道')">拉斯维加斯大道</a> <a
									href="javascript:void();"
									onclick="javascript:sel_scenic(this,'拱门国家公园')">拱门国家公园</a>
							</div>
						</li>

						<li>
							<div class="title">语言能力</div>
							<div class="word" id="selLang">
								<a href="javascript:void();"
									onclick="javascript:sel_lang(this,'普通话')">普通话</a><a
									href="javascript:void();" class="on"
									onclick="javascript:sel_lang(this,'英语')">英语</a>

							</div>
						</li>
						<li>
							<div class="title">其他服务</div>
							<div class="word" id="selService">
								<a href="javascript:void();"
									onclick="javascript:sel_service(this, '接机')">接机</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '人文历史')">熟悉人文历史</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '体育')">熟悉体育项目</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '音乐')">熟悉音乐</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '科学')">熟悉自然科学</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '红灯区')">熟悉红灯区</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '美术')">熟悉美术</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '建筑')">熟悉建筑</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '美食')">熟悉当地美食</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '酒店')">代订酒店</a> <a
									href="javascript:void();"
									onclick="javascript:sel_service(this, '票')">代订机票</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="car-r">
				<div class="car-rt">
					<div class="car-rtl">
						是否带车 <input type="radio" value="" name="" checked /> 是 <input
							type="radio" value="" name="" /> 否
					</div>
					<div class="car-rtr">
						<input type="text" class="inputstyle1" value="其它附加条件" /> <input
							type="button" class="inputbg1" value="搜索" />
					</div>
				</div>
				<div class="car-rb" id="tourList">
					<ul>
						<c:forEach var="tour" items="${tourList}" varStatus="status">
							<li>
								<div class="m-car">
									<div class="m-car-img">
										<a href="${context }/tour/detail?id=${tour.id }"><img
											src="http://guantravel.com:5984/tours/${tour.id }/intro.jpg"
											alt="" /></a>
										<div class="icon">
											<div class="icon-dy">导游</div>
											<c:if test="${fn:length(tour.carType)>0}">
												<div class="icon-zc">包车</div>
											</c:if>
										</div>
									</div>
									<div class="m-car-cont">
										<div class="l">
											<img
												src="http://guantravel.com:5984/tours/${tour.id }/head.jpg"
												alt="" />
										</div>
										<div class="r">
											<div class="title">
												<a href="${context }/tour/detail?id=${tour.id }">${tour.nickName }</a>
											</div>
											<div class="cont">${tour.introduce }</div>
										</div>
									</div>
									<div class="m-car-bot">
										<div class="l">
											服务
											<c:forEach var="i" begin="1" end="${tour.serviceLevel}"
												step="1">
												<span class="star"></span>
											</c:forEach>
											<c:forEach var="i" begin="${tour.serviceLevel}" end="4"
												step="1">
												<span class="star-no"></span>
											</c:forEach>
										</div>
										<div class="r">
											<em>￥${tour.unitPrice }</em>/天
										</div>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<div class="page" style="display: none;">
						<a href="#">&lt;</a><a href="#">1</a><a href="#">2</a><a href="#">3</a><a
							href="#" class="on">4</a><a href="#">5</a><a href="#">6</a><a
							href="#">…</a><a href="#">&gt;</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 包车、导游模板定义 begin -->
<div id="tmpl" style="display: none;">
	<div class="m-car">
		<div class="m-car-img">
			<a href="${context }/tour/detail?id=$id"><img
				src="http://guantravel.com:5984/tours/$id/intro.jpg" alt="" /></a>
			<div class="icon">
				<div class="icon-dy">导游</div>
				$car
			</div>
		</div>
		<div class="m-car-cont">
			<div class="l">
				<img src="http://guantravel.com:5984/tours/$id/head.jpg" alt="" />
			</div>
			<div class="r">
				<div class="title">
					<a href="${context }/tour/detail?id=$id">$nickName</a>
				</div>
				<div class="cont">$introduce</div>
			</div>
		</div>
		<div class="m-car-bot">
			<div class="l">服务 $service</div>
			<div class="r">
				<em>￥$unitPrice</em>/天
			</div>
		</div>
	</div>
</div>
<!-- 包车、导游模板定义  end -->
<script type="text/javascript">
	var city = "";
	var lang = "";
	var scenic = "";
	var service = "";
	function get_a_str(divName) {
		var div = document.getElementById(divName);
		var tag_a = div.getElementsByTagName("a");
		str = "";
		for (var i = 0; i < tag_a.length; i++) {
			if (tag_a[i].className.length > 0)
				str += tag_a[i].innerHTML + ",";
		}
		return str;
	}
	function sel_city(obj, cityName) {

		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";
		//city = cityName;
		city = get_a_str("selCity");
		reload();
	}
	function sel_lang(obj, langName) {
		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";

		lang = get_a_str("selLang");
		reload();
	}

	function sel_scenic(obj, scenicName) {
		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";

		scenic = get_a_str("selScenic");
		reload();
	}

	function sel_service(obj, langName) {
		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";

		service = get_a_str("selService");
		reload();
	}

	function reload() {
		var productList = document.getElementById("tourList");
		debugger;
		var tstr = "";
		tourList.innerHTML = "正在努力获取数据中......";
		var url = "${context}/custom/tour/smart";
		$.post(url, {
			city : city,
			scenic : scenic,
			service : service,
			lang : lang,
			auto : false,
			design : false,
			startKey : null,
			count : 50
		}, function(result, status) {

			if (status == 'success') {
				debugger;
				var ts = eval(result);
				if (ts != null && ts.length > 0)
					tourList.innerHTML = fill(ts);
				else
					tourList.innerHTML = "对不起，没有符合条件的产品，请放宽条件重新试一下。";
			} else {
				tourList.innerHTML = "对不起，访问失败，请刷新后重试！。";
			}

		});

		//tourList.innerHTML = "<ul>" + tstr + "</ul>";
	}
	function fill(ts) {
		var pstr = "";
		var tmpl = document.getElementById("tmpl").innerHTML;
		for (var i = 0; i < ts.length; i++) {

			var gTmpl_str = tmpl;
			gTmpl_str = gTmpl_str.replace(/\\$id/g, ts[i].id);
			gTmpl_str = gTmpl_str.replace(/\\$nickName/g,
					decodeURIComponent(ts[i].nickName));
			gTmpl_str = gTmpl_str.replace(/\\$unitPrice/g, ts[i].unitPrice);
			gTmpl_str = gTmpl_str.replace(/\\$introduce/g,
					decodeURIComponent(ts[i].introduce));
			if (ts[i].carType.length > 0)
				gTmpl_str = gTmpl_str.replace(/\\$car/g,
						'<div class="icon-zc">包车</div>');
			else
				gTmpl_str = gTmpl_str.replace(/\\$car/g, "");

			var s = '';
			for (var n = 1; n <= ts[i].serviceLevel; n++) {
				s += '<span class="star"></span>';
			}
			for (var n = ts[i].serviceLevel; n <= 4; n++) {
				s += '<span class="star-no"></span>';
			}
			gTmpl_str = gTmpl_str.replace(/\\$service/g, s);

			pstr += "<li>" + gTmpl_str + "</li>";
		}
		return "<ul>" + pstr + "</ul>";
	}
</script>