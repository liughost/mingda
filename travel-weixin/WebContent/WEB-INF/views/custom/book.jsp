<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="${context }/resources/custom/js/bootstrap-carousel.js"></script>

<s:url var="orderURL" value="/custom/order?productID="></s:url>
<c:choose>
	<c:when test="${isMobile==true }">
		<s:url var="detailURL" value="/custom/detail_all?id="></s:url>
	</c:when>
	<c:otherwise>
		<s:url var="detailURL" value="/custom/detail_all?id="></s:url>
	</c:otherwise>
</c:choose>
<style type="text/css">
body {
	padding-top: 0px;
}

.alert {
	height: 20px;
	font-size: 18px;
	overflow: hidden;
	border: 0px solid #333;
	/*
	width: 500px;
	margin: 50px auto 0;
	*/
}

.alert li {
	width: 600px;
	height: 25px;
	line-height: 25px;
	overflow: hidden;
}

.mobile_alert {
	height: 24px;
	overflow: hidden;
	border: 0px solid #333;
	/*
	width: 500px;
	margin: 50px auto 0;
	*/
}

.mobile_alert li { //
	width: 500px;
	height: 25px; //
	font-size: 18px;
	line-height: 25px;
	overflow: hidden;
	margin-left: -25px;
}

.product {
	width: 270px;
	position: absolute;
	z-index: 9;
	margin-top: -34px;
	background-color: rgba(0, 0, 0, 0.5);
}

.hotCity {
	text-decoration: none;
	padding-left: 10px;
}

.hotCityText {
	color: white;
}

.table td {
	border-top: 0;
	border-bottom: 1px solid lightgray;
}

.firstHead {
	position: absolute;
	z-index: 1300;
	margin-top: 15px;
	margin-left: 20px;
}

.firstHead a {
	color: white;
}

p {
	margin: 0 0 10px;
	line-height: 24px;
	color: gray;
	padding-top: 10px;
}
</style>

<!-- 产品大图区 -->
<div
	style="position: absolute; z-index: 10; width: 100%; margin-top: 3%;"
	class="text-center">

	<div style="width: 500px; margin-left: auto; margin-right: auto;">
		<h2 style="color: white;">达人陪你去旅行</h2>
		<form action="${context }/custom/product/search" method="POST"
			style="margin-bottom: 0; background-color: rgba(0, 0, 0, 0.6); padding: 10px 20px 20px 10px;">
			<button type="button" class="btn btn-lg btn-warning"
				onclick="tour()"
				style="line-height: 24px; height: 40px; font-size: 18px; color: white; width: 100px;">找人陪</button>
			<input type="text" class="span2" name="city"
				style="margin-top: 10px; height: 30px; width: 250px; font-size: 18px;"
				onclick="clickCity()" placeholder="输入目的地城市名称" autocomplete="off">
			<button type="submit"
				style="height: 40px; width: 80px; font-size: 18px;" class="btn">搜索</button>
			<div class="text-left">
				<span style="color: white;" class="text-left">&nbsp;&nbsp;&nbsp;热点城市：
					<a class="hotCity" href="javascript:void(0);"
					onclick="citySearch('拉斯维加斯')"><span class="hotCityText">拉斯维加斯</span></a>
					<a class="hotCity" href="javascript:void(0);"
					onclick="citySearch('迈阿密')"><span class="hotCityText">迈阿密</span></a>
					<a class="hotCity" href="javascript:void(0);"
					onclick="citySearch('洛杉矶')"><span class="hotCityText">洛杉矶</span></a>
					<a class="hotCity" href="javascript:void(0);"
					onclick="citySearch('奥兰多')"><span class="hotCityText">奥兰多</span></a>
					<a class="hotCity" href="javascript:void(0);"
					onclick="citySearch('盐湖城')"><span class="hotCityText">盐湖城</span></a>
					<a class="hotCity" href="javascript:void(0);"
					onclick="citySearch('旧金山')"><span class="hotCityText">旧金山</span></a>
				</span>
			</div>
		</form>

	</div>

</div>
<!-- Carousel
    ================================================== -->

<div id="myCarousel" class="container carousel slide"
	style="max-width: ${maxWidth}px;">
	<div class="carousel-inner">
		<div class="item ">
			<a href="${context }/custom/detail_all?id=16"><img
				src="${context }/resources/custom/images/slide-01.jpg" alt=""></a>
			<div class="container" style="display: none;">
				<div class="carousel-caption">
					<h1 style="color: white;">美国西海岸、夏威夷12日浪漫之旅</h1>
					<p class="lead" style="color: gray;">真正的加州一号公路自驾路线；夏威夷欧胡岛大环岛游；热闹非凡的拉斯维加斯大道;</p>
					<!--
			  <a class="btn btn-large btn-primary" href="${context }/resources/custom/images/Carousel Template · Bootstrap.htm">Sign up today</a>
			  -->
				</div>
			</div>
		</div>
		<div class="item ">
			<a href="${context }/custom/detail_all?id=10"><img
				src="${context }/resources/custom/images/slide-09.jpg" alt=""></a>
			<div class="container" style="display: none;">
				<div class="carousel-caption">
					<h1>美国西部大环线</h1>
					<p class="lead" style="color: gray;">大环线七大国家/部落公园观光；探索美国传奇公路-66号；美式巨无霸越野车自驾！专业自驾顾问全程陪同安全无忧。</p>

				</div>
			</div>
		</div>

		<div class="item ">
			<a href="${context }/custom/detail_all?id=9"><img
				src="${context }/resources/custom/images/slide-08.jpg" alt=""></a>
			<div class="container" style="display: none;">
				<div class="carousel-caption">
					<h1>美国西海岸三城9日自驾体验之旅</h1>
					<p class="lead" style="color: gray;">旧金山-十七英里海岸-卡梅尔-大苏尔-丹麦城-拉斯维加斯-洛杉矶</p>

				</div>
			</div>
		</div>

		<div class="item active">
			<a href="${context }/custom/detail_all?id=8"><img
				src="${context }/resources/custom/images/slide-07.jpg" alt=""></a>
			<div class="container" style="display: none;">
				<div class="carousel-caption">
					<h1>美东12日完美之旅去佛罗里达吧</h1>
					<p class="lead" style="color: gray;">佛罗里达双城及大沼泽国家公园+纽约曼哈顿岛住宿</p>

				</div>
			</div>
		</div>

		<div class="item">
			<a href="${context }/custom/detail_all?id=3"><img
				src="${context }/resources/custom/images/slide-10.jpg" alt=""></a>
			<div class="container" style="display: none;">
				<div class="carousel-caption">
					<h1>夏威夷7日精彩之旅</h1>
					<p class="lead" style="color: gray;">太平洋上的明珠-夏威夷</p>

				</div>
			</div>
		</div>
		<!-- 
		<div class="item">
			<a href="${context }/custom/detail_all?id=4"><img
				src="${context }/resources/custom/images/huangshi05.jpg" alt=""></a>
			<div class="container" style="display: none;">
				<div class="carousel-caption">
					<h1>黄石国家公园探秘之旅</h1>
					<p class="lead" style="color: gray;">著名的老忠实喷泉、大棱镜彩泉、黄石湖、黄石峡谷等多处著名景观。</p>

				</div>
			</div>
		</div>
		 -->
	</div>
	<!-- 
		<a class="left carousel-control"
			href="http://v2.bootcss.com/examples/carousel.html#myCarousel"
			data-slide="prev">‹</a> <a class="right carousel-control"
			href="http://v2.bootcss.com/examples/carousel.html#myCarousel"
			data-slide="next">›</a>
			 -->
</div>

<script type="text/javascript">
	function tour() {
		location.href = '${context}/custom/tour/search';
	}
	function citySearch(cName) {
		txtSelCity.value = cName;
		frmCitySearch.submit();
	}
	$('#myCarousel').carousel();

	/*	!function($) {
	 $(function() {
	 // carousel demo
	
	 })
	 }(window.jQuery)*/
</script>
<!-- /.carousel -->
<div class="container" style="max-width: ${maxWidth}px;">

	<c:choose>
		<c:when test="${isMobile == true}">
			<h3>热门自驾团:</h3>
			<div class="mobile_alert">

				<ul class="list" onmouseover="javascript:isContinue=false;"
					onmouseleave="javascript:isContinue=true;">
					<c:forEach var="items" items="${groupList}">
						<li><a
							href="${context }/custom/order?productID=${items.productId}&gdate=${items.startDate}"
							title="${items.productName }">${items.startDate}&nbsp;${items.productName
								}</a></li>
					</c:forEach>
				</ul>
			</div>
		</c:when>
		<c:otherwise>

			<div class="alert" style="display: none">
				<ul class="list" onmouseover="javascript:isContinue=false;"
					onmouseleave="javascript:isContinue=true;">
					<c:forEach var="items" items="${groupList}">
						<li><strong>热门自驾团:</strong> <a
							href="${context }/custom/order?productID=${items.productId}&gdate=${items.groupDate}"
							title="${items.productName }">${items.startDate}&nbsp;&nbsp;${items.productName
								}</a></li>
					</c:forEach>
				</ul>

			</div>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
		var isContinue = true;
		function autoScroll(obj) {
			if (!isContinue)
				return;
			$(obj).find(".list").animate({
				marginTop : "-25px"
			}, 500, function() {
				$(this).css({
					marginTop : "0px"
				}).find("li:first").appendTo(this);
			})
		}

		<c:choose>
		<c:when test="${isMobile == true}">
		$(function() {
			setInterval('autoScroll(".mobile_alert")', 3000)
		});
		</c:when>
		<c:otherwise>
		$(function() {
			setInterval('autoScroll(".alert")', 3000)
		});
		</c:otherwise>
		</c:choose>
	</script>

	<!-- 产品列表 -->
	<div class="row">
		<div class="span9">
			<legend>精品小团</legend>
			<!-- 产品列表  -->
			<ul class="nav nav-tabs" style="border-bottom: 0px;">
				<c:forEach var="items" items="${standardList}">
					<li id="img_sel_0" style="text-align: left; min-height: 160px;">
						<div style="width: 285px; min-height: 255px;">
							<a target="_blank" href="${detailURL}${items.productID }"><img
								class="img-rounded" src="${items.img}" style="width: 270px;"
								title="${items.name}"></a>

							<table style="" class="product">
								<tr>
									<td colspan="5"
										style="color: white; font-size: 16px; padding-left: 5px; display: none;">${items.name}</td>
								</tr>
								<tr>
									<td><a href="${detailURL}${items.productID }" title="在线报名"><span
											style="color: white; font-size: 16px">￥${items.lowPrice
												}/</span><span
											style="color: white;">人</span></a></td>
									<td style="text-align: right;"><a
										href="javascript:book(${items.productID });"
										style="margin-right: 10px; margin-top: 0px; display: none;"
										class="btn btn-warning pull-right">报名</a></td>
								</tr>
							</table>
							<p style="">${items.name}</p>
						</div>
					</li>
				</c:forEach>
			</ul>
			<legend>
				私家小团<small>&nbsp;&nbsp;价格、路线、日期、人数均可按需定制，以下为行程参考</small>
			</legend>
			<!-- 产品列表  -->
			<ul class="nav nav-tabs" style="border-bottom: 0px;">
				<c:forEach var="items" items="${aloneList}">
					<li id="img_sel_0" style="text-align: left; min-height: 160px;">
						<div style="width: 285px; min-height: 255px;">
							<a target="_blank" href="${detailURL}${items.productID }"><img
								class="img-rounded" src="${items.img}" style="width: 270px;"
								title="${items.name}"></a>

							<table style="" class="product">
								<tr>
									<td colspan="5"
										style="color: white; font-size: 16px; padding-left: 5px; display: none;">${items.name}</td>
								</tr>
								<tr>
									<td><a href="${detailURL}${items.productID }" title="在线报名"><span
											style="color: white; font-size: 16px">￥${items.lowPrice
												}/</span><span
											style="color: white;">人</span></a></td>
									<td style="text-align: right;"><a
										href="javascript:book(${items.productID });"
										style="margin-right: 10px; margin-top: 0px; display: none;"
										class="btn btn-warning pull-right">报名</a></td>
								</tr>
							</table>
							<p style="">${items.name}</p>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="span2" style="">
			<legend>
				最新动态&nbsp;&nbsp;<a href="${context }/custom/articles"><span
					style="font-size: 14px;">更多</span></a>
			</legend>
			<table class="table">
				<tbody>
					<c:forEach var="items" items="${articleList}">
						<tr>
							<td><a href="${context }/custom/detail?id=${items.id }"><img
									style="max-width: 100px; width: 100px;" src="${items.img }"></a></td>
							<td><a href="${context }/custom/detail?id=${items.id }"><span
									class="tb_title">${items.title }</span></a></td>
						</tr>
						<tr>
							<td colspan="2"
								style="border: 0 solid gray; white-space: nowrap; color: gray;">日期：${items.specialMaketime
								}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="modal hide fade" id="groupDate">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h3>参团日期选择</h3>
	</div>
	<div class="modal-body">
		<p>请选择适合您出发的日期</p>
		<p id="groupList"></p>
	</div>
	<div class="modal-footer">
		<a href="#" onclick="javascript:closeGroup();" class="btn">关闭</a> <a
			id="freeBook" href="#" class="btn btn-warning">个性定制</a>
	</div>
</div>
<script type="text/javascript">
	//设置首页的页头
	//head.className = "container firstHead";
	//frmCitySearch.style.display = "none";
	//headMenu.style.display = "none";

	function closeGroup() {
		$('#groupDate').modal('hide');
	}
	function confirmPay() {

		location.href = "${context}/";
	}
	function book(productId) {
		var url = "${context}/custom/group/list";

		$
				.post(
						url,
						{
							productID : productId
						},
						function(result, status) {

							if (status == 'success') {

								//alert(result);
								var gs = eval(result);
								if (gs.length > 0) {
									var list = "<table class='table'><tr><th>产品名称</th><th>出发日期</th><th></th></tr>";
									for (var i = 0; i < gs.length; i++) {
										//alert(gs[i].bookCount+";"+gs[i].TotalCount); 
										var line = "<tr>";
										line += "<td>";
										if (gs[i].bookCount == gs[i].TotalCount)
											line += "<span style='color:red'>[已满]</span>";
										line += decodeURIComponent(gs[i].productName)
												+ "</td>";
										line += "<td>"
												+ decodeURIComponent(gs[i].startDate)
												+ "</td>";
										line += "<td>";
										if (gs[i].bookCount != gs[i].TotalCount)
											line += "<a class='btn btn-warning' href='${context}/custom/order?productID="
													+ gs[i].productId
													+ "&gdate="
													+ gs[i].startDate
													+ "'>报名</a>";
										line += "</td>";
										line += "</tr>";
										//alert(line);
										list += line;
									}
									groupList.innerHTML = list + "</table>";
									//alert(groupList.innerHTML);
								} else {
									groupList.innerHTML = "目前无发团计划，请选择自由报名，填写您期望的出发日期。";

								}
							}

						});
		//获取团期数据
		$('#groupDate').modal("show");
		freeBook.href = "${context}/custom/order?productID=" + productId;
		//$('#myCarousel').carousel();

	}

	
</script>