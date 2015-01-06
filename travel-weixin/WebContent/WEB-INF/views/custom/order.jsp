<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${context }/resources/js/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<link href="${context }/resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<style>
@media ( max-width : 480px) {
	.nav-collapse {
		-webkit-transform: translate3d(0, 0, 0);
	}
}
</style>
<div style="height: 0px;"></div>
<s:url var="orderURL" value="/leader/order?id="></s:url>
<c:choose>
	<c:when test="${isMobile==true }">
		<s:url var="detailURL" value="/show/spec?id="></s:url>
	</c:when>
	<c:otherwise>
		<s:url var="detailURL" value="/custom/detail?id="></s:url>
	</c:otherwise>
</c:choose>
<div class="container" style="width: 100%;background-color:rgb(245, 243, 243);padding:20px;">

	<div class="container" style="max-width: 800px;background-color:#FCFDEF;padding:15px;">
		<legend>产品预订</legend>
		<form class="form-horizontal">
			<input type="hidden" id="orderId" value="${orderId }" /> <input
				type="hidden" id="productID" value="${pid }" />
			<div class="control-group">
				<label class="control-label">产品名称：</label>
				<div class="controls">
					<label style="line-height: 30px;">${productName }</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="inputEmail">出发日期：</label>
				<div class="controls date form_datetime">

					<c:choose>
						<c:when test="${empty startDate}">
							<span class="add-on"> <i class="icon-calendar"></i></span>
							<input size="16" type="text" id="startDate" value="${startDate }"
								name="startDate" class="input-medium" readonly
								style="background-color: white;">
						</c:when>
						<c:otherwise>
							<span id="startDate" style="line-height: 30px;">${startDate
								}</span>
						</c:otherwise>
					</c:choose>
				</div>
				<script type="text/javascript">
					//alert(g("startDate").value);
					<c:if test="${empty startDate}">
					startDate.value = CurentTime(20);
					//alert(g("startDate").value);	
					$(".form_datetime").datetimepicker({
						format : 'yyyy-mm-dd',
						forceParse : 0,
						todayHighlight : 1,
						startView : 2,
						minView : 2,
						todayBtn : 1,
						language : 'zh-CN',
						autoclose : 1
					});
					</c:if>

					function onUserName(uname) {
						txtResult.innerHTML = "";
						if (uname.value.length > 0)
							txtunameResult.innerHTML = "";
					}
					function onUserMobile(mobile) {
						txtResult.innerHTML = "";
						if (mobile.value.length > 0)
							txtMobileResult.innerHTML = "";
					}
					function chgNumber(objName, num) {
						numberObj = document.getElementById(objName);
						var newDays = parseInt(numberObj.value);
						newDays += parseInt(num);
						if (newDays < 0) {
							alert("对不起，数量不能小于0，请重新设置");
							return;
						}
						numberObj.value = newDays;
						txtResult.innerHTML = "";
					}

					function CurentTime(span) {

						var now = new Date();
						now = now.valueOf();
						now = now + span * 24 * 60 * 60 * 1000;
						now = new Date(now);

						var year = now.getFullYear(); //年
						var month = now.getMonth() + 1; //月
						var day = now.getDate(); //日

						//  var hh = now.getHours();            //时
						//  var mm = now.getMinutes();          //分

						var clock = year + "-";

						if (month < 10)
							clock += "0";

						clock += month + "-";

						if (day < 10)
							clock += "0";

						clock += day;
						/*
						clock +=  " ";
						 if(hh < 10)
						     clock += "0";
						    
						 clock += hh + ":";
						 if (mm < 10) clock += '0'; 
						 clock += mm;
						 */
						return (clock);
					}
					function submitBook() {

						//数据校验
						if (userName.value.length <= 0) {
							txtunameResult.innerHTML = "请输入客人姓名。";
							userName.focus();
							return;
						}
						if (userMobile.value.length <= 0) {
							txtMobileResult.innerHTML = "请输入客人联系手机号码。";
							userMobile.focus();
							return;
						}
						txtResult.innerHTML = "请稍候，正在处理中......";
						var url = "${context}/custom/order/save";
						$('#groupDate').modal("show");
						var arriveDate = "";
						if (startDate.tagName == "input")
							arriveDate = startDate.value;
						else
							arriveDate = startDate.innerHTML;
						//alert(startDate.tagName);
						$.post(url, {
							startDate : arriveDate,
							productID : productID.value,
							orderId : orderId.value,
							userName : userName.value,
							userMobile : userMobile.value,
							childrenCount : childrenCount.value,
							adultCount : adultCount.value,
							oldCount : oldCount.value,
							childrenBedsCount : childrenBedsCount.value,
						}, function(result, status) {

							if (status == 'success') {
								if (result == '0') {
									txtResult.innerHTML = "数据保存失败，请重试。";
								} else if (result == '-1') {
									txtResult.innerHTML = "订单已经生成。";
								} else {
									txtResult.innerHTML = "订单已经生成，订单号:"
											+ result + "有疑问请联系客服：18911837159。";
									orderId.value = result;
								}
							}

						});
					}
				</script>
			</div>
			<div class="control-group">
				<label class="control-label">产品售价：</label>
				<div class="controls">
					<label>成人价格：<span
						style="color: orangered; font-size: 18px;">￥${adultPrice }元</span><br />
						儿童价格：<span style="color: orangered; font-size: 18px;">￥${childrenPrice
							}元</span><br /> 单房差价：<span style="color: gold; font-size: 18px;">￥${hotelSpanPrice
							}元</span><br /> <!--  
						<span style="color: gray">(此价格为不含机票价格，实际价格随出发时参团人数略有变动)</span>
						 -->
					</label>

				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="inputEmail">客户姓名：</label>
				<div class="controls">
					<input type="text" id="userName" placeholder="客户姓名"
						onkeyup="onUserName(this);"><br /> <label
						style="color: red" id="txtunameResult"></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">客户联系手机：</label>
				<div class="controls">
					<input type="text" id="userMobile" placeholder="客人联系手机号码"
						onkeyup="onUserMobile(this);"><br /> <label
						style="color: red" id="txtMobileResult"></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">儿童人数：</label>
				<div class="controls inline">
					<input id="del" class="" type="button" value="-"
						onclick="chgNumber('childrenCount',-1)"> <input
						type="text" id="childrenCount" class="input-mini" name="number"
						value="${childrenCount }"> <input id="plus" class=""
						type="button" value="+" onclick="chgNumber('childrenCount',1)"><br />
					<span style="color: gray">12岁以下（含12岁）不占床免房差<br />12岁以下儿童机票根据航空公司实际优惠政策收取（可自行购买）
					</span>
				</div>
			</div>


			<!-- 
		<div class="control-group">
			<label class="control-label">老年人数：</label>
			<div class="controls inline">
				<input id="del" class="" type="button" value="-"
					onclick="chgNumber('oldCount',-1)"> <input type="number"
					id="oldCount" class="input-mini" name="text" value="${oldCount }">
				<input id="plus" class="" type="button" value="+"
					onclick="chgNumber('oldCount',1)">
			</div>
		</div>
		 -->
			<div class="control-group">
				<label class="control-label">成年人数：</label>
				<div class="controls inline">
					<input id="del" class="" type="button" value="-"
						onclick="chgNumber('adultCount',-1)"> <input type="number"
						id="adultCount" class="input-mini" name="text"
						value="${adultCount }"> <input id="plus" class=""
						type="button" value="+" onclick="chgNumber('adultCount',1)">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">占床位数：</label>
				<div class="controls inline">
					<input id="del" class="" type="button" value="-"
						onclick="chgNumber('childrenBedsCount',-1)"> <input
						type="text" id="childrenBedsCount" class="input-mini"
						name="number" value="${childrenBedsCount }"> <input
						id="plus" class="" type="button" value="+"
						onclick="chgNumber('childrenBedsCount',1)"><br /> <span
						style="color: gray"> </span>
				</div>
			</div>



			<div class="control-group">
				<div class="controls">
					<button type="button" class="btn btn-warning"
						onclick="submitBook()">报名</button>

					<!-- <label id="txtResult"></label>  -->
				</div>
			</div>
		</form>
	</div>
</div>
<div class="modal hide fade" id="groupDate">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h3>保存订单</h3>
	</div>
	<div class="modal-body">
		<p id="txtResult"></p>

	</div>
	<div class="modal-footer">
		<a href="#" onclick="javascript:closeGroup();" class="btn">关闭</a>

	</div>
</div>

<script type="text/javascript">
	function closeGroup() {
		$('#groupDate').modal('hide');
	}

	
</script>