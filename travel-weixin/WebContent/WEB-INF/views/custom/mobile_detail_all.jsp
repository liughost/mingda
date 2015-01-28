<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
body {
	padding: 0px 10px 0px 10px;
	font-family: "Microsoft YaHei";
	/*background-color: #CCCCCC;*/
}

.weekend {
	color: red;
	background-color: light-blue;
	
}

.weekhead {
	background-color: light-blue;
}

.main {
	max-width: 960px;
	background-color: white;
	padding: 0 20px 0 20px;
	border: 1px solid #CDCDCD;
}

.baseInfo {
	color: gray;
	font-size: 14px;
	line-height: 30px;
}

.baseRight {
	padding-left: 30px;
}

.tra_ls_new {
	background: url(${context}/resources/image/skin_v22.png) no-repeat;
	background-position: 0 -712px;
	color: lightblue;
	height: 40px;
}

.l_p {
	padding-right: 40px;
	margin-left: 40px;
	font-size: 14px;
	color: lightblue;
	font-size: 14px;
	color: lightblue;
	line-height: 25px;
	color: lightblue;
	font-size: 14px;
	color: lightblue;
	font-size: 14px;
	color: lightblue;
	color: lightblue;
}

.text_context {
	font-size: 14px;
	line-height: 20px;
}

.group_date {
	height: 35px;
	width: 56px;
	color: red;
	cursor: pointer;
	line-heiht: 25px;
	background-color: rgb(250, 250, 177);
	line-heiht: 25px;
}

.non_thismon {
	height: 35px;
	/*width: 64px;*/
	color: gray;
	background-color: rgb(242, 252, 251);
}

.thismon {
	height: 35px;
	/*width: 64px;*/
	color: gray;
	/*background-color: rgb(242, 252, 251);*/
}

.next_mon {
	cursor: pointer;
	background: url(${context}/resources/image/skin_v22.png) no-repeat;
	background-position: -138px -769px;
	text-align: right;
}

.last_mon {
	cursor: pointer;
	background: url(${context}/resources/image/skin_v22.png) no-repeat;
	background-position: -138px -726px;
}

h3 {
	color: black;
}

.td_left {
	background-color: lightblue;
	width: 80px;
	text-align: right;
}
</style>
<div class="container" style="max-width: 600px; padding-top: 20px;">

	<!-- START THE FEATURETTES -->
	<h3>${info.productName }</h3>

	<!-- 产品主图 -->
	<div class="row-fluid">
		<div class="span12">
			<img src="${info.images[0] }" style="width: 100%;">

		</div>
	</div>
	<!-- 产品基本资料 -->
	<div class="row-fluid">
		<div class="span12">
			<legend>基本信息</legend>
			<table class="table">
				<tr>
					<td class="td_left">出发城市</td>
					<td style="border-top: 0;">${info.deaprtCity }</td>
				<tr>
					<td class="td_left">目的地</td>
					<td class="baseRight">${info.destCity }</td>
				<tr>
				<tr>
					<td class="td_left">时间安排</td>
					<td class="baseRight">${info.settle }</td>
				<tr>
				<tr>
					<td class="td_left">发团日期</td>
					<td>${firstGroup } <!-- 
					<span
						class="label label-important">查看详细</span>
						 -->
					</td>
				<tr>
				<tr>

					<td class="td_left">交通信息</td>
					<td style="border-bottom: 1px solid lightgray;">${info.traffic
						} <!-- 
					<span
						class="label label-important">查看详情</span> -->
					</td>

				</tr>

			</table>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<legend>发团信息</legend>
			<c:forEach var="groupCanlendar" items="${groupCanlendars}"
				varStatus="g">
				<c:choose>
					<c:when test="${g.index==0}">
						<s:url var="show" value="" />
					</c:when>
					<c:otherwise>
						<s:url var="show" value="none" />
					</c:otherwise>
				</c:choose>
				<table class="table table-bordered " style="display:${show};"
					id="can-${g.index}">
					<tr style="background-color: rgb(124, 211, 239);">
						<td colspan="7">
							<table class="table"
								style="width: 100%; border-radius: 4px; margin-bottom: 0px;">
								<tbody>
									<tr>
										<td class="last_mon" style="width: 15px;"
											onclick="getGroup(0,${g.index})"></td>
										<td style="text-align: center; line-height: 16px;">${groupCanlendar.year
											}年${groupCanlendar.month }月</td>
										<td class="next_mon" style="width: 15px; height: 15px"
											onclick="getGroup(1,${g.index})"></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr style="background-color:lightblue;">
						<td class="weekend">日</td>
						<td>一</td>
						<td>二</td>
						<td>三</td>
						<td>四</td>
						<td>五</td>
						<td class="weekend">六</td>

					</tr>
					<tr>
						<c:forEach var="items" items="${groupCanlendar.days}"
							varStatus="status">

							<c:choose>
								<c:when test="${items.price != 0 }">
									<td class="group_date"
										onclick="group_detail(${info.pid},${groupCanlendar.year}, ${groupCanlendar.month }, ${items.day})">${items.day
										}<br />￥${items.price }
									</td>
								</c:when>
								<c:otherwise>
									<c:if test="${items.localMon }">
										<td class="thismon">${items.day }</td>
									</c:if>
									<c:if test="${items.localMon == false }">
										<td class="non_thismon">${items.day }</td>
									</c:if>
								</c:otherwise>
							</c:choose>

							<c:if test="${(status.index+1)%7==0 }">
					</tr>
					<tr>
						</c:if>


						</c:forEach>
					</tr>
				</table>
			</c:forEach>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<legend>推荐理由</legend>
			<p style="font-size: 15px; line-height: 22px; color: red;">${info.recommend
				}</p>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<legend>产品详情</legend>
			<p>${info.detail}</p>
			<legend>产品特色</legend>
			<p>${info.feature}</p>

			<legend>行程安排</legend>
			<p>集合地信息：${info.assembly}</p>

			<c:forEach var="items" items="${info.journey}">
				<span style="font-size: 24px;">${items.day }</span>
				<span
					style="font-size: 18px; color: rgb(18, 120, 153); padding-left: 20px;">${items.title
					}</span>
				<p>
					<em class="tra_ls_new"><span class="l_p">交</span>通</em>
				</p>
				<table class="table table-bordered">
					<c:forEach var="traffic" items="${items.traffic}">
						<tr>
							<td
								style="background-color: lightblue; width: 10px; text-align: center;">${traffic.vehicle
								}</td>
							<td style="width: 120px;">${traffic.detail }</td>
						</tr>
					</c:forEach>
				</table>
				<p>
					<em class="tra_ls_new" style="background-position: 0 -770px;"><span
						class="l_p">餐</span>饮</em>
				</p>
				<p>早餐：${items.meal.breakfirst } 午餐：${items.meal.lanch }
					晚餐：${items.meal.supper }</p>
				<p>
					<em class="tra_ls_new" style="background-position: 0 -739px;"><span
						class="l_p">住</span>宿</em>
				</p>
				<p>${items.hotel }</p>
				<br />
				<table class="text_context">
					<c:forEach var="detail" items="${items.detail}">
						<tr>
							<td><c:if test="${detail.type == 'img' }">
									<img src="${detail.text }">
								</c:if> <c:if test="${detail.type == 'text' }">
							${detail.text }
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
				<hr>
			</c:forEach>
			<legend>费用说明</legend>
			<div>${info.feeIntro }</div>
			<legend>费用包含</legend>
			<div>${info.feeInclude }</div>
			<legend>费用不含</legend>
			<div>${info.feeNotInclude }</div>
			<br />
		</div>

	</div>

	<!-- /END THE FEATURETTES -->
</div>

<div style="height: 20px;"></div>
<script type="text/javascript">
function group_detail(id, y, m, d)
{
	location.href="${context}/custom/order?productID="+id+"&gdate="+y+"-"+m+"-"+d;
}
function getGroup(t,inx)
{
	var oInx = inx;
	if(t==0)
	{
		inx = parseInt(inx)-1;
	}
	else
	{
		inx = parseInt(inx)+1;
	}
	//alert(inx);
	//设置新选择的日历出现
	var canObj = document.getElementById("can-"+inx);
	if(canObj != null)	{
		canObj.style.display="";
	}
	else		{
		//新日历显示失败，直接退回
		return;
		}
	//隐藏原有日历
	var canObj = document.getElementById("can-"+oInx);
	if(canObj == null){
		return;
	}
	canObj.style.display="none";
}
</script>