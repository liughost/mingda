<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="username" property="principal.username" />
</sec:authorize>
<style>
.maink {
	background-color: #f1f4f6;
}

.footerk {
	background-color: #f1f4f6;
}

.area {
	width: 422px;
	height: 155px;
}

.u-txt1 {
	width: 400px;
}
</style>
<!-- 主体内容区 STA -->
<div class="maink">
	<div class="main">
		<div class="use">
			<div class="use-l">
				<div class="use-lhead">
					<img src="${context }/resources/rabbit/images/head166-166.jpg"
						alt="" />
				</div>
				<div class="use-lname">${username }</div>
				<ul class="use-lul">
					<li><a href="#">我的信息</a></li>
					<!-- active为当前状态 -->
					<li><a href="${context }/member/invite/send">我的邀请</a></li>
					<li><a href="${context }/member/order/list">我的订单</a></li>
					<li class="active"><a href="#">导游信息</a></li>
					<li><a class="modalLink" href="#modal4">修改密码</a></li>
				</ul>
			</div>
			<div class="use-r">
				<div class="use-rt">导游信息</div>
				<input type="hidden" id="tourKey" value="${tourKey }" />
				<div class="use-rb">
					<ul class="use-info">


						<li>
							<div class="use-info-l">姓名</div>
							<div class="use-info-r">
								<input id="nickName" type="text" class="u-txt1"
									value="${tour.nickName }" /> <label style="color: red"
									id="nickName_error"></label>
							</div>
						</li>

						<li>
							<div class="use-info-l">性别</div>
							<div class="use-info-r">
								<select class="u-txt1" id="gender"><option value="1">男</option>
									<option value="0">女</option></select>
							</div>
						</li>
						<li>
							<div class="use-info-l">微信ID</div>
							<div class="use-info-r">
								<input id="weixinId" type="text" class="u-txt1"
									value="${tour.weixinId }" /> <label style="color: red"
									id="weixinId_error"></label>
							</div>
						</li>

						<li>
							<div class="use-info-l">电话号码</div>
							<div class="use-info-r">
								<input id="tourMobile" type="text" class="u-txt1"
									value="${tour.mobile }" /><label style="color: red"
									id="mobile_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">EMail</div>
							<div class="use-info-r">
								<input id="EMail" type="text" class="u-txt1"
									value="${tour.EMail }" /><label style="color: red"
									id="email_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">QQ号</div>
							<div class="use-info-r">
								<input id="QQ" type="text" class="u-txt1" value="${tour.QQ }" /><label
									style="color: red" id="qq_error"></label>
							</div>
						</li>

						<li>
							<div class="use-info-l">常驻地</div>
							<div class="use-info-r">
								<input id="tourLocation" type="text" class="u-txt1"
									value="${tour.location }" /><br /> <label style="color: red"
									id="location_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">母语</div>
							<div class="use-info-r">
								<select class="u-txt1" id="motherlang"><option
										value="汉语">汉语</option>
									<option value="英语">英语</option></select>
							</div>
						</li>
						<li>
							<div class="use-info-l">外语</div>
							<div class="use-info-r">
								<input type="checkbox" name="foreign" checked value="英语" />英语
								&nbsp;&nbsp;<input type="checkbox" name="foreign" value="法语" />法语&nbsp;&nbsp;<input
									name="foreign" type="checkbox" value="意大利语" />意大利语&nbsp;&nbsp;<input
									type="checkbox" name="foreign" value="德语" />德语
							</div>
						</li>
						<li>
							<div class="use-info-l">用车说明</div>
							<div class="use-info-r">
								<input id="carType" type="text" class="u-txt1"
									value="${tour.carType }" /><br /> <label style="color: gray"
									id="carType_error">不带车填空,例如：全尺寸SUV、别克7座商务等</label>
							</div>
						</li>
						<li>
							<div class="use-info-l">基本价格(每天)</div>
							<div class="use-info-r">
								<input id="unitPrice" type="text" class="u-txt1"
									value="${tour.unitPrice }" /><br /> <label
									style="color: gray" id="unitPrice_error">每天服务报价,单位元</label>
							</div>
						</li>
						<li>
							<div class="use-info-l">服务城市</div>
							<div class="use-info-r">
								<input id="serviceCities" type="text" class="u-txt1"
									value="${tour.serviceCities }" /><br /> <label
									style="color: gray" id="serviceCities_error">提供服务的城市，多个城市之间以顿号（、）分割</label>
							</div>
						</li>
						<li>
							<div class="use-info-l">服务项目</div>
							<div class="use-info-r">
								<textarea class="area" id="serviceItem"><c:forEach
										var="item" items="${tour.serviceItem }" varStatus="status">${item}&#13;</c:forEach></textarea>
								<br /> <label style="color: gray" id="serviceItem_error">提供的服务项目，接机、送机、导游解说、熟悉美食、熟悉艺术等，每行限一个项目</label>
							</div>
						</li>
						<li>
							<div class="use-info-l">服务景点</div>
							<div class="use-info-r">
								<textarea class="area" id="scenic">${tour.scenic }</textarea>
								<br /> <label style="color: gray" id="scenic_error">可以提供服务的景点，多个景点之间以顿号（、）分割</label>
							</div>
						</li>
						<li>
							<div class="use-info-l">是否规划师</div>
							<div class="use-info-r">

								<select class="u-txt1" id="planning"><option selected
										value="1">可以形成规划</option>
									<option value="0">不做形成规划</option></select>
							</div>
						</li>
						<li>
							<div class="use-info-l">导游类别</div>
							<div class="use-info-r">

								<select class="u-txt1" id="career"><option value="专业导游">专业导游</option>
									<option value="留学生">留学生</option>
									<option value="业余导游">业余导游</option></select>
							</div>
						</li>
						<li>
							<div class="use-info-l">服务说明</div>
							<div class="use-info-r">
								<textarea class="area" id="feeDesc">${tour.feeDesc }</textarea>
								<label style="color: red" id="feeDesc_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">费用不含</div>
							<div class="use-info-r">
								<textarea class="area" id="feeExcept">${tour.feeExcept }</textarea>
								<label style="color: red" id="except_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">其他说明</div>
							<div class="use-info-r">
								<textarea class="area" id="supplement">${tour.supplement }</textarea>
								<br /> <label style="color: red" id="supplement_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">自我介绍</div>
							<div class="use-info-r">
								<textarea class="area" id="introduce">${tour.introduce }</textarea>
								<br /> <label style="color: red" id="except_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">上线状态${tour.online }</div> <c:choose>
								<c:when test="${tour.online == true}">
									<s:url var="onlineStr" value="checked" />
									<s:url var="craftStr" value="" />
								</c:when>
								<c:otherwise>
									<s:url var="onlineStr" value="" />
									<s:url var="craftStr" value="checked" />
								</c:otherwise>
							</c:choose>
							<div class="use-info-r">
								<input type="radio" class="u-btn2" name="online" ${onlineStr }
									onchange="onlineClick()" value="online" />上线<br /> <input
									type="radio" class="u-btn2" name="online"
									onchange="onlineClick()" ${craftStr } value="craft" />草稿 <br />
								<label style="color: red" id="online_error"></label>
							</div>
						</li>



						<li>
							<div class="use-info-l">&nbsp;</div>
							<div class="use-info-r">
								<label style="color: red" id="update_error"></label> <br /> <input
									onclick="javascript:updateTourInfo()"
									style="margin-left: 0px; margin-top: 0px;" type="button"
									class="u-btn1" value="保存资料后" />
								<c:choose>
									<c:when test="${not empty tour.id }">
										<s:url var="showImg" value="block" />
									</c:when>
									<c:otherwise>
										<s:url var="showImg" value="none" />
									</c:otherwise>
								</c:choose>
								<input id="setImage"
									onclick="javascript:location.href='${context }/member/tour/images';"
									style="margin-top: 0px;display:${showImg}" type="button"
									class="u-btn1" value="设置头像" />
							</div>
						</li>


					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 主体内容区 End -->
<script>
	function validate() {
		if (loginName.value.length == 0) {
			$("#loginName_error").text("请输入登录名。");
			loginName.focus();
			return false;
		}
		if (userMobile.value.length == 0) {
			$("#mobile_error").text("对不起，请输入手机号码。");
			userMobile.focus();
			return false;
		}
		if (userName.value.length == 0) {
			$("#userName_error").text("对不起，请输入用户名。");
			userName.focus();
			return false;
		}
		return true;
	}
	function updateTourInfo() {
		//if (!validate())
		//	return;

		var url = "${context}/member/tour/info/update";
		var strForeign = "";
		var foreignObj = document.getElementsByName("foreign");
		for (var i = 0; i < foreignObj.length; i++) {
			if (foreignObj[i].checked)
				strForeign += foreignObj[i].value + ",";
		}
		debugger;
		$.post(url, {
			tourKey : tourKey.value,
			nickName : nickName.value,
			weixinId : weixinId.value,
			mobile : tourMobile.value,
			EMail : EMail.value,
			QQ : QQ.value,
			carType : carType.value,
			career : $("#career").val(),
			feeDesc : $("#feeDesc").val(),
			feeExcept : $("#feeExcept").val(),
			gender : $("#gender").val(),
			introduce : $("#introduce").val(),
			foreignLang : strForeign,
			motherLang : $("#motherlang").val(),
			location : tourLocation.value,
			planning : $("#planning").val(),
			scenic : scenic.value,
			serviceCities : $("#serviceCities").val(),
			serviceItem : $("#serviceItem").val(),
			supplement : $("#supplement").val(),
			unitPrice : unitPrice.value
		}, function(result, status) {
			debugger;
			if (status == 'success') {

				if (result.length > 0) {
					tourKey.value = result;
					$("#setImage").css('display', 'block');
					$("#update_error").text("数据已经保存。");
				} else {
					$("#update_error").text("对不起，会员信息更新失败，请稍候重试！");

				}
			} else {
				$("#update_error").text("对不起，网络故障请稍候重试！");
			}

		});
	}

	function onlineClick() {
		debugger;
		var url = "${context}/member/tour/online";
		$.post(url, {
			tourKey : tourKey.value,
			online : $("input[name='online']:checked").val()
		}, function(result, status) {
			debugger;
			if (status == 'success') {
				result = decodeURIComponent(result);
				$("#online_error").text(result);
			} else {
				$("#online_error").text("对不起，网络故障请稍候重试！");
			}

		});
	}

	
</script>