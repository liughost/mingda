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
					<li class="active"><a href="#">我的信息</a></li>
					<!-- active为当前状态 -->
					<li><a href="${context }/member/invite/send">我的邀请</a></li>
					<li><a href="${context }/member/order/list">我的订单</a></li>
					<li><a href="${context }/member/tour/info">导游信息</a></li>
					<li><a class="modalLink" href="#modal4">修改密码</a></li>
				</ul>
			</div>
			<div class="use-r">
				<div class="use-rt">个人信息</div>
				<div class="use-rb">
					<ul class="use-info">
						<li>
							<div class="use-info-l">登录名</div>
							<div class="use-info-r">
								<input id="loginName" type="text" class="u-txt1"
									value="${info.loginName }" /> <label style="color: red"
									id="loginName_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">微信ID</div>
							<div class="use-info-r">${info.wxOpenId }</div>
						</li>
						<li>
							<div class="use-info-l">电话号码</div>
							<div class="use-info-r">
								<input id="userMobile" type="text" class="u-txt1"
									value="${info.mobile }" /><label style="color: red"
									id="mobile_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">用户姓名</div>
							<div class="use-info-r">
								<input id="userName" type="text" class="u-txt1"
									value="${info.userName }" /><label style="color: red"
									id="username_error"></label>
							</div>
						</li>
						<li>
							<div class="use-info-l">会员等级</div>
							<div class="use-info-r">${info.classLevel }</div>
						</li>
						<li>
							<div class="use-info-l">总积分</div>
							<div class="use-info-r">${info.totalScore }</div>
						</li>
						<li>
							<div class="use-info-l">&nbsp;</div>
							<div class="use-info-r">
								<label style="color: red" id="update_error"></label> <br />
								<input onclick="javascript:updateUserInfo(${info.travelerId})"
									style="margin: 0;" type="button" class="u-btn1" value="更新" />
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
	function updateUserInfo(uid) {
		if (loginName.value.length == 0) {
			$("#loginName_error").text("请输入登录名。");
			loginName.focus();
			return;
		}
		if (userMobile.value.length == 0) {
			$("#mobile_error").text("对不起，请输入手机号码。");
			userMobile.focus();
			return;
		}
		if (userName.value.length == 0) {
			$("#userName_error").text("对不起，请输入用户名。");
			userName.focus();
			return;
		}
		var url = "${context}/member/info/update";
		$.post(url, {
			uid : uid,
			loginName : loginName.value,
			userMobile : userMobile.value,
			userName : userName.value
		}, function(result, status) {
			//debugger;
			if (status == 'success') {

				if (result > 0) {

					$("#update_error").text("恭喜，会员信息更新成功！");

				} else {
					$("#update_error").text("对不起，会员信息更新失败，请稍候重试！");

				}
			} else {
				$("#update_error").text("对不起，网络故障请稍候重试！");
			}

		});
	}

	
</script>