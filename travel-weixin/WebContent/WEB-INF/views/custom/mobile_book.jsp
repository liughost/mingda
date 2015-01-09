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
	padding: 0 5px 0px 5px;
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
	width: 80%;
	position: relative;
	z-index: 5;
	margin-top: -34px;
	background-color: rgba(0, 0, 0, 0.5);
	margin-left: 10%;
	margin-top: -34px;
	background-color: rgba(0, 0, 0, 0.5);
	margin-top: -34px;
	background-color: rgba(0, 0, 0, 0.5);
	background-color: rgba(0, 0, 0, 0.5);
}

.hotCity {
	text-decoration: none;
	padding-left: 10px;
}

.hotCityText {
	color: white;
}

.product_name {
	text-overflow: ellipsis;
	line-height: 26px;
	padding: 0 5% 0 5%;
	white-space: nowrap;
	overflow: hidden;
}

.table td {
	border-top: 0;
	border-bottom: 1px solid #e5e5e5;
}

.btn_first {
	line-height: 16px;
	height: 40px;
	font-size: 14px;
	color: white;
	width: 30%;
}

.product_img {
	width: 40%;
}

.zone {
	margin-bottom: 0;
	background-color: rgba(0, 0, 0, 0.6);
	padding: 10px 00px 10px 0px;
	width: 100%;
	height: 50px;
}
</style>

<!-- /.carousel -->
<div class="container" style="max-width: 600px;">
	<!-- 产品大图区 -->
	<div
		style="position: absolute; z-index: 2; max-width: 600px; width: 100%; margin-top: 4%;"
		class="text-center">

		<div style="width: 50%; margin-left: auto; margin-right: auto;">
			<h3 style="color: white; line-height: 20px;">达人陪你去旅行</h3>
			<table class="form-inline zone row-fluid">
				<tr style="height: 10px;">
					<td colspan="2"></td>
				</tr>
				<tr>
					<td><a class="btn btn-small btn-danger btn-shadow"
						href="${context }/custom/tour/search">找人陪</a></td>

					<td><a class="btn btn-small" href="${context }/custom/product/search">目的地</a></td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2"></td>
				</tr>
			</table>

		</div>

	</div>
	<!-- Carousel
    ================================================== -->

	<div id="myCarousel" class="carousel slide" style="margin-bottom: 0;">
		<div class="carousel-inner">
			<!-- 
		<div class="item ">
			<a href="${context }/custom/detail?id=119"><img
				src="${context }/resources/custom/images/slide-01.jpg" alt=""></a>
		</div>
		 
		<div class="item ">
			<a href="${context }/custom/detail?id=116"><img
				src="${context }/resources/custom/images/slide-02.jpg" alt=""></a>
		</div>
-->
			<div class="item ">
				<img src="${context }/resources/custom/images/mobile/team.jpg"
					alt="">
			</div>
			<div class="item active">
				<img src="${context }/resources/custom/images/mobile/maiami.jpg"
					alt="">
			</div>
			<!-- 
		<div class="item">
			<a href="${context }/custom/detail?id=117"><img
				src="${context }/resources/custom/images/hawaii/hawaii_main.jpg"
				alt=""></a>
		</div>
		 -->

			<div class="item">
				<img src="${context }/resources/custom/images/mobile/huangshi05.jpg"
					alt="">
			</div>
		</div>
	</div>


	<!-- 产品列表 -->
	<div class="row-fluid">
		<div class="span12" style="padding-top: 0px;">
		<legend>&nbsp;&nbsp;&nbsp;精品小团</legend>
			<!-- 产品列表  -->
			<table class="table">
				<c:forEach var="items" items="${standardList}">
					<tr>
						<td style="vertical-align: middle;" class="product_img"><a
							target="_blank" href="${detailURL}${items.productID }"><img
								class="img-rounded" style="width: 100%;" src="${items.img}"
								title="${items.name}"></a></td>
						<td>
							<p class="">
								<a href="${detailURL}${items.productID }" title="在线报名"><span
									style="font-size: 16px">￥${items.lowPrice }/</span><span
									style="">人</span></a>
							</p>
							<p class="">${fn:substring(items.name, 0, 50)}</p>
						</td>
					</tr>

				</c:forEach>
			</table>
			<legend>&nbsp;&nbsp;&nbsp;私家小团</legend>
			<!-- 产品列表  -->
			<table class="table">
				<c:forEach var="items" items="${aloneList}">
					<tr>
						<td style="vertical-align: middle;" class="product_img"><a
							target="_blank" href="${detailURL}${items.productID }"><img
								class="img-rounded" style="width: 100%;" src="${items.img}"
								title="${items.name}"></a></td>
						<td>
							<p class="">
								<a href="${detailURL}${items.productID }" title="在线报名"><span
									style="font-size: 16px">￥${items.lowPrice }/</span><span
									style="">人</span></a>
							</p>
							<p class="">${fn:substring(items.name, 0, 50)}</p>
						</td>
					</tr>

				</c:forEach>
			</table>
			<!-- 
			<ul class="nav nav-tabs"
				style="border-bottom: 0px; padding-left: 0px;">
				<c:forEach var="items" items="${productList}">
					<li id="img_sel_0" class="text-center product_item">
						<div>
							<a target="_blank" href="${detailURL}${items.productID }"><img
								class="img-rounded product_img" src="${items.img}"
								title="${items.name}"></a>

							<p class="product">
								<a href="${detailURL}${items.productID }" title="在线报名"><span
									style="color: white; font-size: 16px">￥${items.lowPrice
										}/</span><span style="color: white;">人</span></a>
							</p>
							<p class="product_name">${fn:substring(items.name, 0, 50)}</p>
						</div>
					</li>
				</c:forEach>
			</ul>
			 -->
		</div>

	</div>
	<div class="row-fluid">
		<div class="span12">
			<legend>&nbsp;&nbsp;最新动态</legend>
			<table class="table">
				<tbody>
					<c:forEach var="items" items="${articleList}">
						<tr>
							<td><a href="${context }/custom/detail?id=${items.id }"><img
									style="width: 160px;" src="${items.img }"></a></td>
							<td style="vertical-align: middle;"><a
								href="${context }/custom/detail?id=${items.id }"><span
									class="tb_title">${items.title }</span></a><br /> <span>${items.specialMaketime
									}</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
	$('#myCarousel').carousel();
	headMenu.style.display = "none";
	//设置产品的行高
	var style = document.createElement('style');
	style.type = 'text/css';
	style.styleSheet.cssText = ".product_item {	min-height: " + screen.width
			/ 3 + "px;	width: 50%;	}</style>";
</script>
<style id="pi">
</style>