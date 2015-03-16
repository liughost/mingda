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
					<li><a href="${context }/member/info">我的信息</a></li>
					<!-- active为当前状态 -->
					<li><a href="${context }/member/invite/send">我的邀请</a></li>
					<li><a href="${context }/member/order/list">我的订单</a></li>
					<li class="active"><a href="${context }/member/tour/info">导游信息</a></li>
					<li><a class="modalLink" href="#modal4">修改密码</a></li>
				</ul>
			</div>
			<div class="use-r">
				<div class="use-rt">导游图片上传</div>

				<div class="use-rb">
					<form action="${context }/member/upload/head"
						enctype="multipart/form-data" method="POST">
						<input type="hidden" id="tourKey" name="tourKey"
							value="${tourKey }" />
						<ul class="use-info">
							<li><div class="use-info-l">头像</div>
								<div class="use-info-r">
									<c:choose>
										<c:when test="${not empty tourKey }">
											<img style="width: 204px; height: 204px;border-radius: 166px;"
												src="http://guantravel.com:5984/tours/${tourKey }/head.jpg" />
										</c:when>
										<c:otherwise>
											<img style="width: 204px; height: 204px;"
												src="${context }/resources/rabbit/images/head166-166.jpg" />
										</c:otherwise>
									</c:choose>

									<br /> <label>导游头像必须是jpg图片，尺寸：204×204</label> <input
										name="headImg" multiple="multiple" type="file" class="u-txt1" />
								</div></li>
							<li><div class="use-info-l">宣传照</div>
								<div class="use-info-r" style="margin-left: 214px;">

									<c:choose>
										<c:when test="${not empty tourKey }">
											<img style="width: 371px; height: 271px;"
												src="http://guantravel.com:5984/tours/${tourKey }/intro.jpg" />
										</c:when>
										<c:otherwise>
											<img style="width: 371px; height: 271px;"
												src="${context }/resources/rabbit/images/head166-166.jpg" />
										</c:otherwise>
									</c:choose>
									<br /> <label>宣传照必须是jpg图片，尺寸：371×271</label> <input
										name="introImg" multiple="multiple" type="file" class="u-txt1" />
								</div></li>
							<li><div class="use-info-l">微信二维码</div>
								<div class="use-info-r" style="margin-left: 214px;">

									<c:choose>
										<c:when test="${not empty tourKey }">
											<img style="width: 212px; height: 212px;"
												src="http://guantravel.com:5984/tours/${tourKey }/weixinQR.jpg" />
										</c:when>
										<c:otherwise>
											<img style="width: 371px; height: 271px;"
												src="${context }/resources/rabbit/images/head166-166.jpg" />
										</c:otherwise>
									</c:choose>
									<br /> <label>微信二维码为jpg图片，尺寸：212×212</label> <input
										name="weixinImg" multiple="multiple" type="file"
										class="u-txt1" />
								</div></li>
							<li>
								<div class="use-info-r" style="margin-left: 214px;"></div>
							</li>
							<li>
								<div class="use-info-r" style="margin-left: 214px;">
									<input type="submit" class="u-btn1" name="uploadImg"
										value="上传图片" />
									 
										<!-- <input type="submit"
										class="u-btn2" name="uploadImg" value="上传微信二维码" />
										 -->
								</div>
							</li>
						</ul>
					</form>
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

				} else {
					$("#update_error").text("对不起，会员信息更新失败，请稍候重试！");

				}
			} else {
				$("#update_error").text("对不起，网络故障请稍候重试！");
			}

		});
	}

	
</script>