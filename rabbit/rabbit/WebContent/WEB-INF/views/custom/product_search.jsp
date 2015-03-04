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
		<img src="${context }/resources/rabbit/images/banner-2.jpg" alt="" />
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
										<a href="javascript:void();" class="on">北美洲</a>
										<!-- <a href="javascript:void();">大洋洲</a> -->
									</div>
								</div>
								<div class="cont-bar">
									<div class="car-lbl">国家</div>
									<div class="car-lbr">
										<a href="javascript:void();" class="on">美国</a>
										<!-- <a href="javascript:void();">加拿大</a>  -->
									</div>
								</div>
								<div class="cont-bar">
									<div class="car-lbl">目的地</div>
									<div class="car-lbr" id="selCity">
									<a href="javascript:void();" onclick="javascript:sel_city(this,'拉斯维加斯')"
											class="">拉斯维加斯</a>
									<a href="javascript:void();" onclick="javascript:sel_city(this,'洛杉矶')" class="">洛杉矶</a>
									<a href="javascript:void();" onclick="javascript:sel_city(this,'旧金山')" class="">旧金山</a>
									
									<a
											href="javascript:void();" onclick="javascript:sel_city(this,'弗雷斯诺')" class="">弗雷斯诺</a>
									 <a href="javascript:void();"
											onclick="javascript:sel_city(this,'佩吉')" class="">佩吉</a>
													
									<a href="javascript:void();" onclick="javascript:sel_city(this,'夏威夷')" class="">夏威夷</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'奥兰多')" class="">奥兰多</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'盐湖城')" class="">盐湖城</a>
										<a
											href="javascript:void();" onclick="javascript:sel_city(this,'蒙特利')" class="">蒙特利</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'迈阿密')" class="">迈阿密</a>
										<a href="javascript:void();"
											onclick="javascript:sel_city(this,'芝加哥')" class="">芝加哥</a>		
									<!--
									<a href="javascript:void();" onclick="javascript:sel_city(this,'丹麦小镇')"
											class="">丹麦小镇</a>
									<a href="javascript:void();" onclick="javascript:sel_city(this,'卡梅尔小镇')"
											class="">卡梅尔小镇</a>
									<a href="javascript:void();" onclick="javascript:sel_city(this,'都柏林')" class="">都柏林</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'隆波克')" class="">隆波克</a> 		
										 <a
											href="javascript:void();" onclick="javascript:sel_city(this,'华盛顿')" class="">华盛顿</a>
										 <a href="javascript:void();"
											onclick="javascript:sel_city(this,'圣路易斯')" class="">圣路易斯</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'埃尔克城')"
											class="">埃尔克城</a> <a href="javascript:void();"
											onclick="javascript:sel_city(this,'基韦斯特')" class="">基韦斯特</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'塔尔萨')" class="">塔尔萨</a>
										
										<a href="javascript:void();" onclick="javascript:sel_city(this,'威廉姆斯')"
											class="">威廉姆斯</a> <a href="javascript:void();"
											onclick="javascript:sel_city(this,'布莱斯')" class="">布莱斯</a> 
										 <a href="javascript:void();"
											onclick="javascript:sel_city(this,'摩押')" class="">摩押</a> <a
											href="javascript:void();" onclick="javascript:sel_city(this,'文图拉')" class="">文图拉</a>
										
										<a href="javascript:void();" onclick="javascript:sel_city(this,'檀香山')" class="">檀香山</a>
										
										
										<a href="javascript:void();" onclick="javascript:sel_city(this,'科特斯')" class="">科特斯</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'纽约')" class="">纽约</a>
										<a href="javascript:void();" onclick="javascript:sel_city(this,'罗德岱堡')"
											class="">罗德岱堡</a>  
										
										<a href="javascript:void();" onclick="javascript:sel_city(this,'雷顿')" class="">雷顿</a>
 									-->
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="title">景点</div>
							<div class="word" id="selScenic">
							<a href="javascript:void();" onclick="javascript:sel_scenic(this,'17英里海岸')">17英里海岸</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'66号公路')">66号公路</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'一号公路')">一号公路</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'七哩桥')">七哩桥</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大峡谷')">大峡谷</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大提顿国家公园')">大提顿国家公园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大沼泽国家公园')">大沼泽国家公园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'奥特莱斯')">奥特莱斯</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'好莱坞')">好莱坞</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'黄石公园')">黄石公园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'布莱斯')">布莱斯</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'奥特莱斯')">奥特莱斯</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'拉斯维加斯大道')">拉斯维加斯大道</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'拱门国家公园')">拱门国家公园</a>
							<!-- 
								
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'中国剧院')">中国剧院</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'丹麦小镇')">丹麦小镇</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'九曲花街')">九曲花街</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'优胜美地')">优胜美地</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'侏罗纪公园')">侏罗纪公园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'冒险岛')">冒险岛</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'千禧公园')">千禧公园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'华丽一英里')">华丽一英里</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'华盛顿纪念碑')">华盛顿纪念碑</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'卡拉卡瓦大道')">卡拉卡瓦大道</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'卡梅尔小镇')">卡梅尔小镇</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'双峰山')">双峰山</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'史密森协会')">史密森协会</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'哈利波特主题乐园')">哈利波特主题乐园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'唐人街')">唐人街</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'国会山庄')">国会山庄</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'西部遗产博物馆')">西部遗产博物馆</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'圆石滩')">圆石滩</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'圣路易斯大拱门')">圣路易斯大拱门</a>
								
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大盐湖')">大盐湖</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大稜镜彩泉')">大稜镜彩泉</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大苏尔')">大苏尔</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'大赛普里斯沼泽地')">大赛普里斯沼泽地</a>
								
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'威基基海滩')">威基基海滩</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'威廉姆斯小镇')">威廉姆斯小镇</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'宰恩国家公园')">宰恩国家公园</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'小哈瓦那')">小哈瓦那</a>
								
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'星光大道')">星光大道</a>
								<a href="javascript:void();" onclick="javascript:sel_scenic(this,'曼哈顿')">曼哈顿</a>
								 -->
							</div>
						</li>
						<li>
							<div class="title">
								旅行目的<a href="">X</a>
							</div>
							<div class="word" id="selType">
								<a href="javascript:void();" onclick="javascript:sel_type(this,'亲子')">亲子</a><a
									href="javascript:void();" onclick="javascript:sel_type(this,'购物')">购物</a><a
									href="javascript:void();" onclick="javascript:sel_type(this,'休闲')">休闲</a><a
									href="javascript:void();" onclick="javascript:sel_type(this,'摄影')">摄影</a><a
									href="javascript:void();" onclick="javascript:sel_type(this,'美食')">美食</a><a
									href="javascript:void();" onclick="javascript:sel_type(this,'度假')">度假</a><a
									href="javascript:void();" onclick="javascript:sel_type(this,'自驾')">自驾</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="car-r">
				<div class="car-rt">
					<div class="car-rtr">
						<input type="text" class="inputstyle1" id="attach"
							placeholder="其它附加条件" /> <input type="button" onclick="reload()"
							class="inputbg1" value="搜索" />
					</div>
				</div>
				<div class="groom-list" id="productList">
					<ul>
						<c:forEach var="product" items="${productList}" varStatus="status">
							<li>
								<div class="glist">
									<div class="glist-t">
										<a
											href="${context }/custom/detail_all?id=${product.productID}"><img
											src="${product.img }" alt=""
											style="width: 371px; height: 371px;" /></a>
										<div class="icon">
											<div class="icon-xl">线路</div>
										</div>
										<div class="gbg-k">
											<div class="gbg">
												<a
													href="${context }/custom/detail_all?id=${product.productID}">${product.name }</a>
											</div>
										</div>
									</div>
									<div class="glist-b">
										<div class="l">
											<span>￥${product.lowPrice }</span>/人
										</div>
										<div class="r">56</div>
									</div>
								</div>
							</li>
						</c:forEach>

					</ul>
					<div class="page" style="display: none;">
						<a href="javascript:void();">&lt;</a><a href="javascript:void();">1</a><a href="javascript:void();">2</a><a href="javascript:void();">3</a><a
							href="javascript:void();" class="on">4</a><a href="javascript:void();">5</a><a href="javascript:void();">6</a><a
							href="javascript:void();">…</a><a href="javascript:void();">&gt;</a>
					</div>
				</div>
			</div>
		</div>

		<!-- 线路推荐 STA -->
		<div class="groom">
			<div class="groom-t">线路推荐</div>
			<div class="groom-b">
				<ul>
					<li>
						<div class="groom-bt">
							<a target="_blank" href="${context }/custom/detail_all?id=10"><img src="${context}/resources/rabbit/images/photo-5.jpg" alt="" /></a>
						</div>
						<div class="groom-bb">
							<div class="groom-bb-t">
								<a target="_blank" href="${context }/custom/detail_all?id=10">城市：芝加哥、圣路易斯、塔尔萨、埃尔克城、雷顿、科特斯、佩吉、威廉姆斯、拉斯维加斯、洛杉矶</a>
							</div>
							<div class="groom-bb-b">出行天数：14天12晚</div>
						</div>
					</li>
					<li>
						<div class="groom-bt">
							<a target="_blank" href="${context }/custom/detail_all?id=15"><img src="${context}/resources/rabbit/images/photo-6.jpg" alt="" /></a>
						</div>
						<div class="groom-bb">
							<div class="groom-bb-t">
								<a target="_blank" href="${context }/custom/detail_all?id=15">城市：洛杉矶、拉斯维加斯、布莱斯、摩押、科特斯、佩吉</a>
							</div>
							<div class="groom-bb-b">出行天数：12天10晚</div>
						</div>
					</li>
					<li>
						<div class="groom-bt">
							<a target="_blank" href="${context }/custom/detail_all?id=9"><img src="${context}/resources/rabbit/images/photo-07.jpg" alt="" /></a>
						</div>
						<div class="groom-bb">
							<div class="groom-bb-t">
								<a target="_blank" href="${context }/custom/detail_all?id=9">城市：洛杉矶、拉斯维加斯、弗雷斯诺、都柏林、旧金山、蒙特利、卡梅尔小镇、隆波克、丹麦小镇</a>
							</div>
							<div class="groom-bb-b">出行天数：12天10晚</div>
						</div>
					</li>
					<li>
						<div class="groom-bt">
							<a target="_blank" href="${context }/custom/detail_all?id=4"><img src="${context}/resources/rabbit/images/photo-08.jpg" alt="" /></a>
						</div>
						<div class="groom-bb">
							<div class="groom-bb-t">
								<a target="_blank" href="${context }/custom/detail_all?id=4">城市：拉斯维加斯、盐湖城、洛杉矶、怀俄明、犹他州</a>
							</div>
							<div class="groom-bb-b">出行天数：12天10晚</div>
						</div>
					</li>
					<li>
						<div class="groom-bt">
							<a target="_blank" href="${context }/custom/detail_all?id=22"><img src="${context}/resources/rabbit/images/photo-9.jpg" alt="" /></a>
						</div>
						<div class="groom-bb">
							<div class="groom-bb-t">
								<a target="_blank" href="${context }/custom/detail_all?id=22">城市：奥兰多、罗德岱堡、迈阿密、基韦斯特、拉斯维加斯、洛杉矶</a>
							</div>
							<div class="groom-bb-b">出行天数：12天10晚</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<!-- 线路推荐 End -->
	</div>
</div>
<!-- 主体内容区 End -->
<div id="tmpl" style="display: none;">
	<li>
		<div class="glist">
			<div class="glist-t">
				<a href="${context }/custom/detail_all?id=$productID"><img
					src="$img" alt="" style="width: 371px; height: 371px;" /></a>
				<div class="icon">
					<div class="icon-xl">线路</div>
				</div>
				<div class="gbg-k">
					<div class="gbg">
						<a href="${context }/custom/detail_all?id=$productID">$name</a>
					</div>
				</div>
			</div>
			<div class="glist-b">
				<div class="l">
					<span>￥$lowPrice</span>/人
				</div>
				<div class="r">56</div>
			</div>
		</div>
	</li>
</div>

<script type="text/javascript">
	var city = "";
	var type = "";
	var scenic = "";
	function sel_scenic(obj, scenicName) {
		var div = document.getElementById("selScenic");
		var scenic_a = div.getElementsByTagName("a");

		scenic = "";
		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";
		for (var i = 0; i < scenic_a.length; i++) {
			if (scenic_a[i].className.length > 0)
				scenic += scenic_a[i].innerHTML + ",";
		}
		reload();
	}
	function sel_type(obj, typeName) {
		var div = document.getElementById("selType");
		var type_a = div.getElementsByTagName("a");

		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";
		type = "";
		for (var i = 0; i < type_a.length; i++) {
			if (type_a[i].className.length > 0)
				type += type_a[i].innerHTML + ",";
		}
		reload();
	}
	function sel_city(obj, cityName) {
		var div = document.getElementById("selCity");
		var city_a = div.getElementsByTagName("a");

		if (obj.className.length == 0)
			obj.className = "on";
		else
			obj.className = "";
		//city = cityName;
		city = "";
		for (var i = 0; i < city_a.length; i++) {
			if (city_a[i].className.length > 0)
				city += city_a[i].innerHTML + ",";
		}
		reload();
	}
	function reload() {
		var productList = document.getElementById("productList");

		var pstr = "";
		productList.innerHTML = "正在努力获取数据中......";
		var url = "${context}/custom/product/smart";
		$.post(url, {
			city : city,
			type : type,
			attach : attach.value,
			scenics : scenic
		}, function(result, status) {

			if (status == 'success') {
				debugger;
				var ps = eval(result);
				if (ps != null && ps.length > 0)
					productList.innerHTML = fill(ps);
				else
					productList.innerHTML = "对不起，没有符合条件的产品，请放宽条件重新试一下。";
			} else {
				productList.innerHTML = "对不起，访问失败，请刷新后重试！。";
			}

		});

		//productList.innerHTML = "<ul>" + pstr + "</ul>";
	}
	function fill(ps) {
		var pstr = "";
		var tmpl = document.getElementById("tmpl").innerHTML;
		for (var i = 0; i < ps.length; i++) {
			var gTmpl_str = tmpl;
			gTmpl_str = gTmpl_str.replace(/\\$productID/g, ps[i].productID);
			gTmpl_str = gTmpl_str.replace(/\\$lowPrice/g, ps[i].lowPrice);
			gTmpl_str = gTmpl_str.replace(/\\$img/g, ps[i].img);
			gTmpl_str = gTmpl_str.replace(/\\$name/g,
					decodeURIComponent(ps[i].name));
			pstr += gTmpl_str;
		}
		return "<ul>" + pstr + "</ul>";
	}
</script>