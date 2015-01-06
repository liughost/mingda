<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${context }/resources/js/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<link href="${context }/resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">

<style type="text/css">
.city {
	border-bottom: 0px;
	z-index: 0;
	position: relative;
	width: 100%;
	/*
	background-color: white;
	border: 1px solid lightgray;
	*/
}

.price {
	position: relative;
	margin-top: -45px;
	background-color: rgba(0, 0, 0, 0.4);
	line-height: 32px;
}

a:hover {
	text-decoration: none;
}

.table td {
	border: 0;
	padding: 5px;
}

.place_item {
	opacity: 0.6;
	height: 20px;
	width: auto;
	padding-right: 8px;
	margin-left: 4px;
}

.item {
	padding-right: 5px;
	opacity: 0.6;
	width: 20px;
}
</style>
<div style="height: 15px;"></div>
<div class="container" style="max-width: 600px;">
	<div class="row-fluid">
		<div class="span12">
			<legend>线路类型</legend>
			<div id="type_zone">

				<div class="row" style="padding-left: 36px">
					<ul class="nav nav-tabs" id="type_seller"
						style="border-bottom: 0 solid black">

						<c:forEach var="items" items="${type_list}">
							<li style="text-align: left; min-width: 70px;"><label
								class="checkbox "><input type="checkbox" name="typeZone"
									value="${items.catalogName }"> ${items.catalogName } </label></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<!-- 搜索栏  -->
		<div class="span12">
			<legend>目的地选择</legend>
			<input type="hidden" name="city" style="margin-bottom: 0px;"
				onclick="clickCitySearch()" id="txtSearchCity" placeholder="请输入城市名称"
				autocomplete="off" value="${cities}">

			<div id="city_zone" class="city" style="display:;">

				<div class="row" style="padding-left: 36px">
					<ul class="nav nav-tabs" id="city_seller"
						style="border-bottom: 0 solid black">

						<c:forEach var="items" items="${city_list}">
							<li style="text-align: left; min-width: 120px;"><label
								class="checkbox "><input type="checkbox"
									<c:if test="${fn:contains(cities,items.cityName) }">checked</c:if>
									name="cityZone" onclick="sel_a_city('${items.cityName }')"
									value="${items.cityName }"> ${items.cityName } </label></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<button class="btn btn-primary" onclick="searchProduct()">搜索</button>

		</div>
	</div>

	<div class="row-fluid">
		<!-- 产品展示  -->

		<!-- 产品列表  -->
		<legend>
			产品搜索结果&nbsp;(<span id="count" style="font-size: 14px;">0</span>)
		</legend>
		<div class="span12" id="productList" style="margin-left: 0;">
			<img style='width: 25px;'
				src='${context}/resources/image/loading.gif'><span>正在努力搜索中，请稍等，马上就好了。</span>
		</div>


	</div>
</div>
<div style="height: 20px"></div>
<div id="guideTmpl" style="display: none">
	&nbsp;<a href="${context }/tour/detail?id=$id"><img src="$headImg"
		class="leader_head img-circle" alt="$nickName" title="$nickName"
		style="width: 30px;">$nickName</a>
</div>
<div id="productTmpl" style="display: none">
	<table class="table">
		<tr style="background-color: rgb(224, 245, 252);">
			<td colspan="2"><span style="font-size: 16px; color: black;">$name</span></td>
		</tr>
		<tr>
			<td style="width: 31%; vertical-align: top;"><a
				href="${context }/custom/detail_all?id=$productId"><img
					class="img-rounded" style="width: 100%" src="$img"> </a>
				<div class="price">
					<a
						href="${context }/custom/order?productID=$productId&gdate=$groupDate"
						title="在线报名"><span
						style="width: 31.60%; color: white; font-size: 20px">￥$lowPrice/</span><span
						style="color: white">人起 </span></a>
				</div></td>

			<td>
				<table style="text-align: left;">

					<tr>
						<td><img class="place_item"
							src="${context }/resources/image/place_logo.png">&nbsp;城市:&nbsp;&nbsp;$cities</td>
					</tr>

					<tr>
						<td><img style="height: 18px;" class="item"
							src="${context }/resources/image/heart_logo.png">&nbsp;线路类型:&nbsp;&nbsp;$types</td>
					</tr>

					<tr>
						<td><img class="item"
							src="${context }/resources/image/days_logo.png">&nbsp;出行天数:&nbsp;&nbsp;$days<!-- <img
						style="height: 20px;"
						src="${context }/resources/image/type_logo.png"> &nbsp;导游：$guides --></td>
					</tr>
					<tr>
						<td><img class="item"
							src="${context }/resources/image/groupdate_logo.png"><span>&nbsp;最近团期：$groupDate</span>


						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	function clickTypeSearch() {
		type_zone.style.display = "";
	}
	function clickCitySearch() {
		city_zone.style.display = "";
	}
	function sel_a_city(cityName) {
		var sel_city = "";
		var cities = document.getElementsByName("cityZone");
		for ( var i = 0; i < cities.length; i++) {
			if (cities[i].checked)
				sel_city += cities[i].value + ",";
		}
		//city_zone.style.display = "none";
		txtSearchCity.value = sel_city;
	}
	function closeCityZone() {
		city_zone.style.display = "none";
	}
	function searchProduct() {
		// type
		var typeObj = document.getElementsByName("typeZone");
		var type_str = "";
		for ( var i = 0; i < typeObj.length; i++) {
			if (typeObj[i].checked)
				type_str += typeObj[i].value + ",";
		}
		// city
		var cityObj = document.getElementsByName("cityZone");
		var city_str = "";
		for ( var i = 0; i < cityObj.length; i++) {
			if (cityObj[i].checked)
				city_str += cityObj[i].value + ",";
		}
		// fee
		// city
		/*var feeObj = document.getElementsByName("sel_fee");
		var fee_str = "";
		for ( var i = 0; i < feeObj.length; i++) {
			if (feeObj[i].checked) {
				fee_str = feeObj[i].value;
				break;
			}
		}*/
		productList.innerHTML = "<img style='width:25px;' src='${context}/resources/image/loading.gif'><span>正在努力搜索中，请稍等，马上就好了。</span>";
		var url = "${context}/custom/product/smart";
		$
				.post(
						url,
						{
							startDate : null,
							city : city_str,
							fee : null,
							type : type_str,
						},
						function(result, status) {
							//debugger;
							if (status == 'success') {

								var ps = eval(result);

								if (ps != null && ps.length > 0) {

									fillPage(ps);
								} else {
									count.innerHTML = 0;
									productList.innerHTML = "<span>对不起，没有符合条件的产品，请放宽条件重新试一下。</span>";
								}
							} else {
								productList.innerHTML = "<span>对不起，访问失败，请刷新后重试！。</span>";
							}

						});
	}
	// 初始化时间
	var now = new Date();
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	// 初始化日历
	$(".form_datetime").datetimepicker({
		initialDate : year + '-' + month,
		// viewSelect : 'year',
		format : 'yyyy-mm',
		forceParse : 0,
		todayHighlight : 1,
		startView : 3,
		minView : 3,
		todayBtn : 1,
		language : 'zh-CN',
		autoclose : 1
	});
	function fillPage(ps) {
		count.innerHTML = ps.length;
		var product_str = "";
		var guideTmpl = document.getElementById("guideTmpl").innerHTML;
		var productTmpl = document.getElementById("productTmpl").innerHTML;
		for ( var i = 0; i < ps.length; i++) {
			// 导游列表
			var guideTmpl_str = "";

			for ( var j = 0; ps[i].tour != null && j < ps[i].tour.length; j++) {
				//debugger;
				var gTmpl_str = guideTmpl;
				gTmpl_str = gTmpl_str
						.replace("$headImg", ps[i].tour[j].headImg);
				gTmpl_str = gTmpl_str.replace("$id", ps[i].tour[j].custId);
				//alert("abcabcabc".replace(/\a/g,"ad"));
				gTmpl_str = gTmpl_str.replace(/\\$nickName/g,
						decodeURIComponent(ps[i].tour[j].nickName));
				//gTmpl_str = gTmpl_str.replace("$nickName",
				//		decodeURIComponent(ps[i].tour[j].nickName));
				guideTmpl_str += gTmpl_str;
				//break;//显示第一个
			}
			// 产品列表
			var productTmpl_str = productTmpl;
			productTmpl_str = productTmpl_str.replace('$img', ps[i].img);
			productTmpl_str = productTmpl_str.replace('$name',
					decodeURIComponent(ps[i].name));
			// 线路类型
			var types = ps[i].lineType;
			/*for ( var j = 0; ps[i].productType != null
					&& j < ps[i].productType.length; j++) {
				types += decodeURIComponent(ps[i].productType[j].catalogName)
						+ "&nbsp;";
			}*/
			productTmpl_str = productTmpl_str.replace('$types',
					decodeURIComponent(types));
			// 城市
			var cities = ps[i].cities_str;
			/*for ( var j = 0; ps[i].cities != null && j < ps[i].cities.length; j++) {
				cities += decodeURIComponent(ps[i].cities[j].cityName)
						+ "&nbsp;";
			}*/
			//alert(cities);
			productTmpl_str = productTmpl_str.replace('$cities',
					decodeURIComponent(cities));

			productTmpl_str = productTmpl_str.replace('$detailId',
					ps[i].MediaID);
			productTmpl_str = productTmpl_str.replace('$introduce',
					decodeURIComponent(ps[i].introduce));
			productTmpl_str = productTmpl_str.replace('$days',
					decodeURIComponent(ps[i].days));
			productTmpl_str = productTmpl_str.replace(/\\$groupDate/g,
					ps[i].groupDate);
			productTmpl_str = productTmpl_str.replace('$guides', guideTmpl_str);
			productTmpl_str = productTmpl_str.replace('$lowPrice',
					ps[i].lowPrice);
			productTmpl_str = productTmpl_str.replace(/\\$productId/g,
					ps[i].productID);

			product_str += productTmpl_str;
		}
		productList.innerHTML = product_str;
	}
	// 默认加载全部产品

	var url = "${context}/custom/product/smart";
	$
			.post(
					url,
					{
						startDate : null,
						city : txtSearchCity.value,
						fee : null,
						type : null,
					},
					function(result, status) {

						if (status == 'success') {
							// debugger;
							var ps = eval(result);
							if (ps != null && ps.length > 0)
								fillPage(ps);
							else {
								count.innerHTML = 0;
								productList.innerHTML = "<span>对不起，没有符合条件的产品，请放宽条件重新试一下。</span>";
							}
						}

					});
</script>