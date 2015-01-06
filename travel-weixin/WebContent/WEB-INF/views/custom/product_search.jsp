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
	z-index: 10000;
	position: absolute;
	width: 280px;
	background-color: white;
	border: 1px solid lightgray;
}
.price{
position: absolute;
margin-top: -45px;
background-color: rgba(0,0,0,0.4);
line-height: 32px;
width: 270px;

}
a:hover{text-decoration: none;}
.place_item{
opacity: 0.6;
height:20px;
width:auto;
padding-right:8px;
margin-left: 4px;
}
.item{
padding-right: 5px;
opacity: 0.6;
width:20px;
}
</style>
<div class="container"
	style="min-width: ${maxWidth}px;min-height: 400px;">
	<div class="row">
		<!-- 左侧选择栏  -->
		<div class="span3 form-horizontal">

			<div class="row" style="height: 50px;">
				<div class="span1">
					<span style="color: gray">目的地:</span>
				</div>
				<div class="span3">
					<input type="text" class="span2" name="city" style=""
						onclick="clickCitySearch()" id="txtSearchCity"
						placeholder="请输入城市名称" autocomplete="off" value="${cities}">

					<div id="city_zone" class="city" style="display: none;">
						<div class="row">
							<button type="button" class="close" onclick="closeCityZone()">&times;</button>
						</div>
						<div class="row" style="padding-left: 6px">
							<ul class="nav nav-tabs span3" id="city_seller"
								style="border-bottom: 0 solid black">

								<c:forEach var="items" items="${city_list}">
									<li style="text-align: left; width: 90px;"><label
										class="checkbox "><input type="checkbox"
											<c:if test="${fn:contains(cities,items.cityName) }">checked</c:if>
											name="cityZone" onclick="sel_a_city('${items.cityName }')"
											value="${items.cityName }"> ${items.cityName } </label></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="height: 50px;display: none;">
				<div class="span1">
					<span style="color: gray">出行时间:</span>
				</div>
				<div class="span3">
					<div class="input-append date form_datetime">
						<span class="add-on"> <i class="icon-calendar"></i></span><input
							size="16" type="text" id="startDate" value="" name="startDate"
							class="input-medium" readonly style="background-color: white;">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span1">
					<span style="color: gray">线路类型:</span>
				</div>
				<div class="span3">

					<div id="type_zone">

						<div class="row" style="padding-left: 6px">
							<ul class="nav nav-tabs span3" id="type_seller"
								style="border-bottom: 0 solid black">

								<c:forEach var="items" items="${type_list}">
									<li style="text-align: left; width: 90px;"><label
										class="checkbox "><input type="checkbox"
											name="typeZone" value="${items.catalogName }">
											${items.catalogName } </label></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="display:none">
				<div class="span1">
					<span style="color: gray">出行预算:</span>
				</div>
				<div class="span3">

					<ul class="nav nav-tabs" style="border-bottom: 0px;">
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="0" checked /> 不限制 </label></li>
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="1" /> 1万以下 </label></li>
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="2" /> 1万-2万 </label></li>
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="3" /> 2万-3万 </label></li>
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="5" /> 3万-5万 </label></li>
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="10" /> 5万-10万 </label></li>
						<li style="text-align: left; width: 100px;"><label
							class="radio "><input type="radio" name="sel_fee"
								value="11" /> 10万上 </label></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="span1"></div>
				<div class="span3">
					<button class="btn btn-primary" onclick="searchProduct()">搜索</button>
				</div>
			</div>
		</div>
		<!-- 右侧产品列  -->
		<div class="span8" style="background-color: #f5f5f5;">
			<!-- 产品列表  -->
			<div class="row">
				<div class="span7 text-center" id="productList"
					style="padding: 10px 10px 10px 40px; min-height: 400px;">
					<img style='width: 25px;'
						src='${context}/resources/image/loading.gif'><span>正在努力搜索中，请稍等，马上就好了。</span>
				</div>
			</div>
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
	<div class="row" style="height: 190px;">
		<div class="span3">
			<a href="${context }/custom/detail_all?id=$productId"><img
				class="img-rounded" style="width: 270px; height: 179px" src="$img">
				</a>
				<div class="price">
				<a href="${context }/custom/order?productID=$productId&gdate=$groupDate"
									 title="在线报名"><span
										style="color: white; font-size: 20px">￥$lowPrice/</span><span
										style="color: white">人起 </span></a>
										</div>
		</div>
		<div class="span4" style="color: gray">
			<table style="height: 180px; width: 100%; text-align: left;">
				<tr>
					<td><span style="font-size: 16px; color: black;">$name</span></td>
				</tr>
				<!-- 
				<tr>
					<td><span style="color: gray;">$introduce</span></td>
				</tr>
				 -->
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
					<td>
						<table style="width: 100%">
							<tr>
								<td><img class="item"
									src="${context }/resources/image/groupdate_logo.png"><span>&nbsp;最近团期：$groupDate&nbsp;<a href="${context }/custom/detail_all?id=$productId">更多</a></span>
									<a href="${context }/custom/order?productID=$productId&gdate=$groupDate"
									style="text-decoration: none;display:none;" title="在线报名"><span
										style="color: rgb(255, 145, 0); font-size: 20px">￥$lowPrice/</span><span
										style="color: rgb(255, 145, 0)">人起 </span></a></td>
								<td style="text-align: right;"><a
									href="${context }/custom/order?productID=$productId&gdate=$groupDate"
									style="margin-right: 20px; margin-top: 0px; display: ;"
									class="btn btn-warning pull-right">报名</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>

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
		city_zone.style.display = "none";
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
		var feeObj = document.getElementsByName("sel_fee");
		var fee_str = "";
		for ( var i = 0; i < feeObj.length; i++) {
			if (feeObj[i].checked) {
				fee_str = feeObj[i].value;
				break;
			}
		}
		productList.innerHTML = "<img style='width:25px;' src='${context}/resources/image/loading.gif'><span>正在努力搜索中，请稍等，马上就好了。</span>";
		var url = "${context}/custom/product/smart";
		$
				.post(
						url,
						{
							startDate : startDate.value,
							city : city_str,
							fee : fee_str,
							type : type_str,
						},
						function(result, status) {

							if (status == 'success') {
								// debugger;
								var ps = eval(result);
								if (ps != null && ps.length > 0)
									fillPage(ps);
								else
									productList.innerHTML = "<span>对不起，没有符合条件的产品，请放宽条件重新试一下。</span>";
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
			var types =  ps[i].lineType;
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
			productTmpl_str = productTmpl_str.replace('$days', decodeURIComponent(ps[i].days));
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
							else
								productList.innerHTML = "<span>对不起，没有符合条件的产品，请放宽条件重新试一下。</span>";
						}

					});
</script>