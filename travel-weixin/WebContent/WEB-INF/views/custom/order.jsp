<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.non_thismon {
	height: 35px;
	width: 64px;
	color: gray;
	background-color: rgb(242, 252, 251);
}

.thismon {
	height: 35px;
	width: 64px;
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

.table td {
	font-size: 16px;
}
</style>
<div class="container"
	style="max-width: 800px; background-color: #FCFDEF; padding: 15px;">
	<legend>产品预订</legend>
	<form id="frmLogin" action="${context }/static/j_spring_security_check" method="POST">
	<input type="hidden" name="j_username" id="loginName">
	<input type="hidden" name="j_password" id="passWD">
	</form>
	<form class="form-horizontal" onsubmit="onSubmitBook()" method="POST">
		<input type="hidden" id="orderId" value="${orderId }" /> <input
			type="hidden" id="productID" value="${pid }" />

		<ul class="breadcrumb">
			<li><a href="#">客户信息</a></li>
		</ul>
		<div class="control-group">
			<label class="control-label" for="inputEmail">客户姓名：</label>
			<div class="controls">
				<input type="text" id="userName" placeholder="客户姓名"><br />
				<label style="color: red" id="txtunameResult"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户联系手机：</label>
			<div class="controls">
				<input type="text" id="userMobile" placeholder="客人联系手机号码"
					maxlength="11"><br /> <label style="color: red"
					id="txtMobileResult"></label>
			</div>
		</div>
		<ul class="breadcrumb">
			<li><a href="#">产品信息</a></li>
		</ul>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<span style="margin-top: 5px; display: block;">${productName }</span><input
					type="hidden" id="product" value="${pid }">
			</div>
		</div>
		<div class="control-group ">
			<div class="date form_datetime" id="divGroupCanlendar"></div>

		</div>
		<div class="control-group">
			<label class="control-label">出行日期：</label>
			<div class="controls">
				<label style="margin-top: 4px;" id="selGroupDate">${startDate }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品售价：</label>
			<div class="controls">
				<label style="margin-top: 4px;">成人价格：<span
					style="color: orangered; font-size: 18px;" id="adultPrice">${adultPrice }</span>&nbsp;&nbsp;
					儿童价格：<span style="color: orangered; font-size: 18px;"
					id="childrenPrice">${childrenPrice }</span>
				</label> <input type="hidden" id="adultPriceValue"> <input
					type="hidden" id="childrenPriceValue"> <input type="hidden"
					id="hotelSpanPriceValue"> <input type="hidden"
					id="offPriceValue" value="0"> 

			</div>
		</div>


		<ul class="breadcrumb">
			<li><a href="#">订购信息</a></li>
		</ul>
		<div class="control-group">
			<label class="control-label" for="inputEmail">优惠码：</label>
			<div class="controls">
				<input type="text" id="inviteCode" placeholder="优惠码">
				<button type="button" id="btnValidateCode" class="btn btn-primary"
					onclick="validateInviteCode()">优惠码校验</button>
				<br /> <label style="color: red" id="offPriceResult"></label> <label
					style="color: red" id="txtInviteCodeResult"></label>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">成年人数：</label>
			<div class="controls inline">
				<input type="number" id="adultCount" class="input"
					onchange="priceChange()" value="2"><br /> <input
					type="radio" name="agreeShare" value="1" onchange="priceChange()"
					checked>同意拼房<input name="agreeShare" type="radio" value="0"
					onchange="priceChange()">不同意拼房，愿意支付房差费用¥<span
					id="hotelSpanPrice">${hotelSpanPrice }</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">儿童人数：</label>
			<div class="controls inline">
				<input type="number" id="childrenCount" class="input"
					onchange="priceChange(this)" value="0"> <br /> <span
					style="color: gray">12岁以下（含12岁）不占床免房差<br />12岁以下儿童机票根据航空公司实际优惠政策收取（可自行购买）
				</span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">房间数：</label>
			<div class="controls inline">
				<input type="number" id="bedsCount" class="input"
					onchange="priceChange(this)" value="0"> <br /> <span
					style="color: gray" id="hotelSpan"> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">价格总计：</label>
			<div class="controls inline" style="margin-top: 6px;">
				<span style="color: red; font-size: 18px;" id="totalPrice"></span>
			</div>
		</div>

		<div class="control-group">
			<div class="controls">
				<button type="button" class="btn btn-warning"
					onclick="onSubmitBook()" id="btnBook">报名</button>

				<!-- <label id="txtResult"></label>  -->
			</div>
		</div>
	</form>
</div>
<div style="display: none" id="groupTableTmpl">
	<table class="table table-bordered "
		style="min-width: 300px; width: 70%; margin-left: auto; margin-right: auto; display: $show;"
		id="can-$index">
		<tr style="background-color: rgb(124, 211, 239);">
			<td colspan="7">
				<table class="table"
					style="width: 100%; border-radius: 4px; margin-bottom: 0px;">
					<tbody>
						<tr>
							<td class="last_mon" style="width: 15px;"
								onclick="getGroup(0,$index)"></td>
							<td style="text-align: center; line-height: 16px;">$year
								年$month月</td>
							<td class="next_mon" style="width: 15px; height: 15px"
								onclick="getGroup(1,$index)"></td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr style="background-color: light-blue;">
			<td class="weekend">日</td>
			<td>一</td>
			<td>二</td>
			<td>三</td>
			<td>四</td>
			<td>五</td>
			<td class="weekend">六</td>

		</tr>
		<!-- body_str -->
	</table>
</div>
<script>
	function onSubmitBook() {
		//数据校验

		if (userName.value.length == 0) {
			alert("请输入客户姓名。");
			userName.focus();
			return;
		}
		if (userMobile.value.length == 0) {
			alert("请输入客户手机号码。");
			userMobile.focus();
			return;
		}

		if (selGroupDate.innerHTML.trim().length < 10) {
			alert("请选择团期。");
			return;
		}
		if (adultCount.value < 1) {
			alert("对不起，承认数量不能小于1人。");
			adultCount.value = 1;
			adultCount.focus();
			return;
		}

		var url = "${context}/custom/book/save";
		console.log(url);
		var codeValue = '';
		if (inviteCode.disabled) {
			codeValue = inviteCode.value;
		}
		$.post(url, {
			orderId : orderId.value,
			code : codeValue,
			userName : userName.value,
			userMobile : userMobile.value,
			product : product.value,
			selGroupDate : selGroupDate.innerHTML,
			adultCount : adultCount.value,
			childrenCount : childrenCount.value,
			agreeValue : agreeValue(),
			inviteCode : inviteCode.value
		}, function(result, status) {
			debugger;
			if (status == 'success') {
				var ret = eval(result);
				if (ret.length > 0) {
					orderId.value = ret[0];
					btnBook.disabled = true;
					alert("报名成功，订单号:" + ret[0]);
					debugger;
					loginName.value = decodeURIComponent(ret[1]);
					passWD.value = ret[2];
					document.getElementById("frmLogin").submit();
				} else
					alert("对不起，预订失败，请检查相关数据。");
			} else {

				alert("对不起，访问失败，请刷新后重试！。");
			}

		});
	}
	function agreeValue() {
		var aObj = document.getElementsByName("agreeShare");
		for (var i = 0; i < aObj.length; i++) {
			if (aObj[i].checked) {
				if (aObj[i].value == 1)
					return false;
			}
		}
		return true;
	}
	function priceChange() {
		if (adultPriceValue.value.length == 0
				|| childrenPriceValue.value.length == 0
				|| hotelSpanPriceValue.value.length == 0) {
			alert("请选择出行团期。");
			return;
		}
		if (adultCount.value < 1) {
			alert("对不起，成年人人数不能少于1个。");
			adultCount.value = 1;
			adultCount.focus();
			return;
		}
		if (childrenCount.value < 0) {
			alert("对不起，儿童人数不能为负数。");
			childrenCount.value = 0;
			childrenCount.focus();
			return;
		}
		if (bedsCount.value < 0) {
			alert("对不起，儿童占床位数不能为负数。");
			bedsCount.focus();
			return;
		}
		var aPrice = parseInt(adultPriceValue.value);
		var cPrice = parseInt(childrenPriceValue.value);
		var hPrice = parseInt(hotelSpanPriceValue.value);
		//debugger;

		//adultCount
		//childrenCount
		//bedsCount
		//计算标间数量
		var aCount = parseInt(adultCount.value);
		var bCount = parseInt(bedsCount.value);
		var cCount = parseInt(childrenCount.value);
		if (bCount > cCount) {
			alert("对不起，儿童占床位数不能多余儿童数量。");
			childrenCount.focus();
			return;
		}
		//var h = adultCount.value / 2;
		//计算占用的床数量
		var beds = aCount;
		var s = 0;
		debugger;
		var selAgree = agreeValue();
		if (aCount % 2 != 0 && selAgree) {
			//占用了多余的标间
			s = hPrice;
		}
		//儿童房差，默认儿童不单独住
		//s += cCount * hPrice;

		//hotelSpan
		debugger;
		var total = aCount * aPrice + cCount * cPrice + s
				- parseInt(offPriceValue.value);
		totalPrice.innerHTML = "￥" + total + "元";
	}
	function validateInviteCode() {
		debugger;
		var url = "${context}/custom/inviteCodeValidate";
		$.post(url, {
			code : inviteCode.value
		}, function(result, status) {
			debugger;
			if (status == 'success') {

				var codeObj = eval(result);

				if (codeObj != null && codeObj.length > 0) {

					if (codeObj[0].codeStatus == 0) {
						btnValidateCode.disabled = true;
						inviteCode.disabled = true;
						offPriceValue.value = codeObj[0].offPrice;
						offPriceResult.innerHTML = "优惠：￥" + codeObj[0].offPrice
								+ "元";
						txtInviteCodeResult.innerHTML = "";
						priceChange();
					} else {
						alert("对不起，此优惠码已经使用或者过期，请确认后重试！");
					}

				} else {
					//count.innerHTML = 0;
					offPriceValue.value = 0;
					offPriceResult.innerHTML = "";
					txtInviteCodeResult.innerHTML = "对不起，没有找到对应的邀请码，请确认后重试。";

				}
			} else {
				offPriceValue.value = 0;
				offPriceResult.innerHTML = "";
				txtInviteCodeResult.innerHTML = "对不起，访问失败，请刷新后重试！。";
			}

		});
	}
	function fillDays(days) {
		var body = '<tr>';
		for (var i = 0; i < days.length; i++) {

			if (days[i].price != 0) {

				if (days[i].date == selGroupDate.innerHTML) {
					debugger;
					sel_group(null, days[i].date, days[i].price,
							days[i].childrenPrice, days[i].hotelSpanPrice);
				}
				body += '<td class="group_date" onclick="sel_group(this, \''
						+ days[i].date + '\',' + days[i].price + ','
						+ days[i].childrenPrice + ',' + days[i].hotelSpanPrice
						+ ')">' + days[i].day + '<br />￥' + days[i].price
						+ '</td>';
			} else {
				if (days[i].localMon) {
					body += '<td class="thismon">' + days[i].day + '</td>';
				} else {
					body += '<td class="non_thismon">' + days[i].day + '</td>';
				}

			}

			if ((i + 1) % 7 == 0) {
				body += '</tr><tr>';
			}

		}
		body += '</tr>';
		return body;
	}
	function fillCanlendar(groupCanlendar) {
		var groupTables = '';
		//debugger;
		var gTmpl = groupTableTmpl.innerHTML;
		for (var i = 0; i < groupCanlendar.length; i++) {
			var gData = gTmpl;
			//设置表头
			if (i != 0)
				gData = gData.replace(/\\$show/g, 'none');
			else
				gData = gData.replace(/\\$show/g, '');
			gData = gData.replace(/\\$index/g, i);
			gData = gData.replace(/\\$year/g, groupCanlendar[i].year);
			gData = gData.replace(/\\$month/g, groupCanlendar[i].month);

			//gData.replace(/\\$show/g, decodeURIComponent(groupCanlendar[i].));
			//设置table body
			//debugger;
			var body = fillDays(groupCanlendar[i].days);
			gData = gData.replace('<!-- body_str -->', body);
			//添加到页面中
			groupTables += gData;
		}
		divGroupCanlendar.innerHTML = groupTables;
	}
	function getGroupDate(id) {
		//产品改变了，对应的团期数据要清除
		//selGroupDate.innerHTML = '';
		adultPrice.innerHTML = '';
		adultPriceValue.value = 0;
		childrenPrice.innerHTML = '';
		childrenPriceValue.value = 0;
		hotelSpanPrice.innerHTML = '';
		hotelSpanPriceValue.value = 0;

		var url = "${context}/custom/groupDate";
		console.log("call getGroupDate function.url=" + url);
		$.post(url, {
			id : id
		}, function(result, status) {
			//debugger;
			if (status == 'success') {

				var gs = eval(result);

				if (gs != null && gs.length > 0) {

					fillCanlendar(gs);

				} else {
					//count.innerHTML = 0;
					divGroupCanlendar.innerHTML = "<span>对不起，没有找到该产品。</span>";

				}
			} else {
				divGroupCanlendar.innerHTML = "<span>对不起，访问失败，请刷新后重试！。</span>";
			}

		});

	}
	window.onload = function() {
		getGroupDate(document.getElementById("product").value);
	};

	function getGroup(t, inx) {
		var oInx = inx;
		if (t == 0) {
			inx = parseInt(inx) - 1;
		} else {
			inx = parseInt(inx) + 1;
		}
		//alert(inx);
		//设置新选择的日历出现
		var canObj = document.getElementById("can-" + inx);
		if (canObj != null) {
			canObj.style.display = "";
		} else {
			//新日历显示失败，直接退回
			return;
		}
		//隐藏原有日历
		var canObj = document.getElementById("can-" + oInx);
		if (canObj == null) {
			return;
		}
		canObj.style.display = "none";
	}
	function sel_group(obj, date, adult, children, hotelSpan) {
		//debugger;
		selGroupDate.innerHTML = date;
		//console.log(day);
		adultPriceValue.value = adult;
		childrenPriceValue.value = children;
		hotelSpanPriceValue.value = hotelSpan;

		/*if (obj.style.backgroundColor != 'rgb(5, 205, 190)') {
			obj.style.backgroundColor = 'rgb(5, 205, 190)';
		 */
		adultPrice.innerHTML = '￥' + adult + '元';
		childrenPrice.innerHTML = '￥' + children + '元';
		hotelSpanPrice.innerHTML = '￥' + hotelSpan + '元';

		//重新计算总价
		priceChange();
	}
	function sel_product(obj) {
		getGroupDate(obj.value);
	}

	
</script>