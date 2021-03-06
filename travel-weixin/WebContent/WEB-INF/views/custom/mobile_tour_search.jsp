<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${context }/resources/js/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<link href="${context }/resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<style>
.city {
	border-bottom: 0px;
	z-index: 10000;
	position: absolute;
	width: 280px;
	background-color: white;
	border: 1px solid lightgray;
}
</style>
<div style="height: 25px"></div>
<div class="container" style="max-width: 600px; min-height: 400px;">
	<div class="row-fluid">
		<!-- 左侧选择栏  -->
		<div class="span12">
			<legend>目的地选择</legend>
			<input type="hidden" class="span2" name="city" style=""
				onclick="clickCitySearch()" id="txtSearchCity"
				placeholder="请选择目的地城市" autocomplete="off" value="${cities}">
			<div id="city_zone">
				<ul class="nav nav-tabs" id="city_seller"
					style="border-bottom: 0 solid black;">

					<c:forEach var="items" items="${city_list}">
						<li style="text-align: left; min-width: 110px;"><label
							class="checkbox "><input type="checkbox"
								<c:if test="${fn:contains(cities,items.cityName) }">checked</c:if>
								name="cityZone" onclick="sel_a_city('${items.cityName }')"
								value="${items.cityName }"> ${items.cityName } </label></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<legend>计划出行时间</legend>
			<div class="input-append date form_datetime">
				<span class="add-on"> <i class="icon-calendar"></i></span><input
					size="16" type="text" id="startDate" value="" name="startDate"
					class="input-medium" readonly style="background-color: white;">
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">

			<button class="btn btn-primary" onclick="searchTour()">搜索</button>
		</div>
	</div>
	<div class="row-fluid">
		<!-- 导游列  -->
		<div class="span12">
			<legend>
				搜索结果(<span id="count" style="font-size: 12px;">33</span>)
			</legend>
			<!-- 导游列表  -->
			<div id="tourList"
				style="padding: 0px 0px 0px 0px; min-height: 400px;">
				<img style='width: 25px;'
					src='${context}/resources/image/loading.gif'><span>正在努力搜索中，请稍等，马上就好了。</span>
			</div>
		</div>
	</div>
</div>
<div style="height: 20px"></div>
<!-- 右侧导游列表的模板 -->
<div id="guideTmpl" style="display: none">
	&nbsp;<a href="${context }/tour/detail?id=$id"><img src="$headImg"
		class="leader_head img-circle" alt="$nickName" title="$nickName"
		style="width: 30px;">$nickName</a>
</div>
<div id="tourTmpl" style="display: none">
	<table style="width:100%;">
		<tr style="width:31%;">
			<td><a href="${context }/tour/detail?id=$tourId"><img
					class="img-rounded"
					style="width: 90px; height: 90px; margin-top: 10px;" src="$img"></a>
			</td>

			<td>
				<table style="min-height: 120px; text-align: left;">
					<tr>
						<td><h4 style="font-size: 18px; color: black;">
								$name&nbsp;&nbsp; &nbsp;<span
									style="color: gray; font-size: 14px">性别：&nbsp;$gender</span>
								&nbsp;$serviceLevel
							</h4></td>
					</tr>
					<!-- 
				<tr>
					<td><span style="color: gray;">$introduce</span></td>
				</tr>
				 -->
					<tr>
						<td><img style="height: 20px; padding: 0px 4px 0px 4px;opacity: 0.8;"
							src="${context }/resources/image/place_logo.png">&nbsp;服务城市：&nbsp;$cities</td>
					</tr>
					<tr>
						<td><img style="width:16px;height: 18px;opacity: 0.8;padding-right:4px;margin-left:2px;"
							src="${context }/resources/image/heart_logo.png">&nbsp;语言：&nbsp;$language</td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;</td>
					</tr>

				</table>
			</td>
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
	function searchTour() {
		// type
		/*var typeObj = document.getElementsByName("typeZone");
		var type_str = "";
		for ( var i = 0; i < typeObj.length; i++) {
			if (typeObj[i].checked)
				type_str += typeObj[i].value + ",";
		}*/
		// city
		var cityObj = document.getElementsByName("cityZone");
		var city_str = "";
		for ( var i = 0; i < cityObj.length; i++) {
			if (cityObj[i].checked)
				city_str += cityObj[i].value + ",";
		}
		// fee
		/*var feeObj = document.getElementsByName("sel_fee");
		var fee_str = "";
		for ( var i = 0; i < feeObj.length; i++) {
			if (feeObj[i].checked) {
				fee_str = feeObj[i].value;
				break;
			}
		}*/
		tourList.innerHTML = "<img style='width:25px;' src='${context}/resources/image/loading.gif'><span>找人陪进行时，马上到。</span>";
		var url = "${context}/custom/tour/smart";
		$
				.post(
						url,
						{
							startDate : startDate.value,
							city : city_str,

						},
						function(result, status) {

							if (status == 'success') {
								// debugger;
								var ps = eval(result);
								if (ps != null && ps.length > 0){
									fillPage(ps);
									
								}
								else{
									count.innerHTML = "0";
									tourList.innerHTML = "<span>对不起，没有找到合适的导游，请放宽条件试一下。</span>";
								}
							} else {
								tourList.innerHTML = "<span>对不起，访问失败，请刷新后重试！。</span>";
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
	function fillPage(ts) {
		count.innerHTML = ts.length;
		var tour_str = "";
		//debugger;
		var tourTmpl = document.getElementById("tourTmpl").innerHTML;
		for ( var i = 0; i < ts.length; i++) {
			// 导游列表
			var tourTmpl_str = tourTmpl;
			tourTmpl_str = tourTmpl_str.replace('$img', ts[i].headImg);
			tourTmpl_str = tourTmpl_str.replace('$name',
					decodeURIComponent(ts[i].nickName));
			tourTmpl_str = tourTmpl_str.replace('$tourId', ts[i].custId);
			var g = "保密";
			if (parseInt(ts[i].gender) == 1)
				g = '女';
			else if (parseInt(ts[i].gender) == 2)
				g = '男';
			tourTmpl_str = tourTmpl_str.replace('$gender', g);
			tourTmpl_str = tourTmpl_str.replace('$language',
					decodeURIComponent(ts[i].language));
			var star = '<img src="${context}/resources/image/red-star.png" style="width: 22px; margin-top: -5px;">';
			var level = "";
			//debugger;
			for ( var n = 0; n < parseInt(ts[i].serviceLevel); n++) {
				level += star;
			}
			tourTmpl_str = tourTmpl_str.replace('$serviceLevel', level);
			tourTmpl_str = tourTmpl_str.replace('$cities',
					decodeURIComponent(ts[i].cities));

			tour_str += tourTmpl_str;
		}
		tourList.innerHTML = tour_str;
	}
	// 默认加载全部产品

	var url = "${context}/custom/tour/smart";
	$.post(url, {
		startDate : null,
		city : null,

	}, function(result, status) {

		if (status == 'success') {
			// debugger;
			var ts = eval(result);
			if (ts != null && ts.length > 0)
				fillPage(ts);
			else{
				count.innerHTML = "0";
				tourList.innerHTML = "<span>对不起，没有找到合适的人来陪，请放宽条件试一下。</span>";
			}
		}

	});
</script>