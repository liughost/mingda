<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<script type="text/javascript"
	src="${context }/resources/js/typeahead.bundle.js"></script>
<script type="text/javascript">
	function g(objName) {
		return document.getElementById(objName);
	}
	function chgNumber(objName, num) {
		numberObj = g(objName);
		var newDays = parseInt(numberObj.value);
		newDays += parseInt(num);
		if (newDays < 0) {
			alert("对不起，数量不能小于0，请重新设置");
			return;
		}
		numberObj.value = newDays;
	}
	function sel_count(objName) {
		var count = 0;
		var obj = document.getElementsByName(objName);
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked)
				count++;
		}
		return count;
	}
	function CurentTime(dateSpan) {
		var span = 60;//默认提前60天
		if (dateSpan != null)
			span = dateSpan;
		var now = new Date();
		now = now.valueOf();
		now = now + span * 24 * 60 * 60 * 1000;
		now = new Date(now);

		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate(); //日
		var clock = year + "-";

		if (month < 10)
			clock += "0";

		clock += month + "-";

		if (day < 10)
			clock += "0";

		clock += day

		return (clock);
	}
	function clickCity() {
		/*var cities = document.getElementsByName("sel_city");
		for ( var i = 0; i < cities.length; i++) {
			cities[i].checked = false;
		}*/
		$('#sel_city').modal("show");

	}
	function closeSelCity() {
		$('#sel_city').modal("hide");
		txtSelCity.focus();
	}
	function sel_all(allObj) {
		var cities = document.getElementsByName("sel_city");
		for (var i = 0; i < cities.length; i++) {
			cities[i].checked = allObj.checked;
		}
	}
	function SelCityOk() {
		$('#sel_city').modal("hide");
		//debugger;
		var sels = "";
		var cities = document.getElementsByName("sel_city");
		for (var i = 0; i < cities.length; i++) {
			if (cities[i].checked)
				sels += cities[i].value + ",";
		}
		txtSelCity.value = sels;
		frmCitySearch.submit();
	}
	function getCities() {
		var url = "${context}/custom/city/list";

		$
				.get(
						url,
						function(data, status) {
							if (status == 'success') {
								//debugger;
								var cities = eval(data);
								var c_str = "";
								for (var i = 0; i < cities.length; i++) {
									var cName = decodeURIComponent(cities[i].cityName);
									/*if (i % 3 == 0)
										c_str += "<tr>";
									c_str += '<td><label class="checkbox "><input type="checkbox" name="sel_city" value="'+cName+'"  />'
											+ cName + '</label></td>';
									+"</td>";
									if (i % 3 == 0 && i != 0)
										c_str += "</tr>";*/
									c_str += '<li style="text-align: left; width:150px;">';
									c_str += '<label class="checkbox "><input type="checkbox" name="sel_city" value="'+cName+'"  />'
											+ cName + '</label>';
									c_str += '</li>';
								}
								cities_list.innerHTML = c_str;
							}
						});
	}
	getCities();
</script>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="username" property="principal.username" />
</sec:authorize>
<div class="container" id="head" style="max-width: ${maxWidth}px;">
	<div class="row">
		<div class="span3" style="width: 340px;">
			<a href="${ context}/custom/book"><img
				src="${context }/resources/image/web_logo.png" style="height: 60px;"></a>
		</div>
		<div class="span4 offset4 text-right">

			<form class="form-inline" id="frmCitySearch"
				action="${context }/custom/product/search" method="POST"
				style="margin-bottom: 0;">
				<div class="control-group">
				<!-- 
					<c:if test="${empty username}">

						<lable class="control-label"> <a
							href="${context }/member/info"><span
							style="position: absolute; z-index: 1; margin: 15px -30px 0px -70px;">会员登录</span></a></lable>
					</c:if>
					 -->
					<input type="text" class="input-small" name="city"
						style="margin-top: 10px;" onclick="clickCity()" id="txtSelCity"
						placeholder="请输入目的地城市名称" autocomplete="off">
					<button type="submit" class="btn" style="margin-top: 10px;">搜索</button>
					<c:if test="${empty username}">

						<a class="btn" style="margin-top: 10px;color:gray;" onclick="location.href='${context }/member/info';">登录</a>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<div class="modal hide fade" id="sel_city">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3>目的地城市选择</h3>
		</div>
		<div class="modal-body">
			<!-- p>
				<input type="checkbox" onclick="sel_all(this)">全选
			</p -->
			<ul class="nav nav-tabs" style="border-bottom: 0px;" id="cities_list">
			</ul>
		</div>
		<div class="modal-footer">
			<a href="#" onclick="javascript:closeSelCity();" class="btn">关闭</a> <a
				href="#" onclick="javascript:SelCityOk();" class="btn btn-primary">确认</a>
		</div>
	</div>
	<div id="headMenu" class="navbar  navbar-inverse">
		<!-- navbar-inverse">  -->
		<div class="navbar-inner">
			<div class="nav-collapse collapse">

				<!-- 
					<li <c:if test="${menuIndex==0}"> class="active"</c:if>><a
						href="${context }/custom/share">领队动态</a></li>
					
					<li <c:if test="${menuIndex==1}"> class="active"</c:if>><a
						href="${context}/custom/recommend">爆款产品</a></li>
						  -->
				<sec:authorize access="hasAnyRole('ROLE_LEAD')">
					<li <c:if test="${menuIndex==1}"> class="active"</c:if>><a
						href="${context}/leader/home">旅行线路</a></li>
				</sec:authorize>

				<ul class="nav" style="margin-top: 0px;">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> 旅行线路 <strong class="caret"></strong>
					</a>
						<ul class="dropdown-menu">

							<li><a href="${context }/custom/book">经典线路</a></li>
							<li><a href="${context }/custom/group/list">即刻发团</a></li>
							<li><a href="${context }/custom/share">领队动态</a></li>

						</ul></li>
				</ul>

				<ul class="nav" style="margin-top: 0px;">
					<li class="dropdown <c:if test="${menuIndex==3}"> active</c:if>"><a
						href="#" class="dropdown-toggle" data-toggle="dropdown"> 私人定制<strong
							class="caret"></strong>
					</a>
						<ul class="dropdown-menu">

							<li><a href="${context}/custom/private">私人定制</a></li>
							<li><a href="${context}/custom/mine_sel">我的定制</a></li>
							<!-- 
									<li ><a
										href="${context}/custom/myOrders">我的预订</a></li>
 -->
						</ul></li>
				</ul>



				<!-- 
					<li <c:if test="${menuIndex==5}"> class="active"</c:if>><a
						href="#">冠行动态</a></li>
						 -->
				<ul class="nav" style="margin-top: 0px;">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> 前往美国<strong class="caret"></strong>
					</a>
						<ul class="dropdown-menu">

							<li><a href="${context }/custom/detail?id=123">车型介绍</a></li>
							<li><a href="${context }/custom/detail?id=124">自驾须知</a></li>
							<li><a href="${context }/custom/detail?id=125">准备去美国</a></li>
							<li><a href="${context }/custom/articles">更多精彩</a></li>

						</ul></li>
				</ul>

				<!-- （实时，领队发送内容 游记 经验） -->


				<!-- 
					<li <c:if test="${menuIndex==4}"> class="active"</c:if>><a
						href="${context}/custom/mine">我的定制</a></li>
						 -->
				<!-- 
					<ul class="nav" style="margin-top: 0px;">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">
									领队<strong class="caret"></strong>
								</a>
							<ul class="dropdown-menu">
								<li><a href="/travel-weixin/leader/home">产品列表</a></li>
							</ul></li>
					</ul>
					
					<li <c:if test="${menuIndex==6}"> class="active"</c:if>><a
						href="${context}/leader/home">VIP预订</a></li>
 					-->


				<sec:authorize access="hasAnyRole('ROLE_LEAD')">

					<ul class="nav pull-right" style="margin-top: 0px;">
						<c:choose>
							<c:when test="${empty username}">
							</c:when>
							<c:otherwise>
								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown"> ${username}<strong class="caret"></strong>
								</a>
									<ul class="dropdown-menu">
										<li><a href="${context}/leader/order/list">我的订单</a></li>
										<li><a href="${context}/leader/benifit">我的收益</a></li>
										<li class="divider"></li>
										<li><a href="${context}/static/j_spring_security_logout">退出</a>
										</li>
									</ul></li>
							</c:otherwise>
						</c:choose>

					</ul>

				</sec:authorize>

				<sec:authorize access="hasAnyRole('ROLE_MEM')">

					<ul class="nav pull-right" style="margin-top: 0px;">
						<c:choose>
							<c:when test="${empty username}">
							</c:when>
							<c:otherwise>
								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown"> 您好：${username}<strong class="caret"></strong>
								</a>
									<ul class="dropdown-menu">
										<li><a href="${context}/member/info">我的信息</a></li>
										<li><a href="${context}/member/order/list">我的订单</a></li>
										<li><a href="${context}/member/invite/send">发送邀请</a></li>
										<li class="divider"></li>
										<li><a href="${context}/static/j_spring_security_logout">退出</a>
										</li>
									</ul></li>
							</c:otherwise>
						</c:choose>

					</ul>

				</sec:authorize>

				<c:if test="${empty username}">
					<span style="color: gray; margin-top: 10px;" class="pull-right">
						电话：<a style="color: gold;" href="tel:4006910800">400-6910-800</a>
					</span>
				</c:if>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>