<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.info_left {
	text-align: right;
	width: 120px;
}

.info_right {
	width: 220px;
}

.table td {
	border-top: 0px;
	border-bottom: 1px solid #dddddd;
}
</style>
<c:if test="${IsMobile }">
	<div style="height: 20px;"></div>
</c:if>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>会员信息</legend>
	<table class="table">
		<tr>
			<td class="info_left">登录名</td>
			<td class="info_right"><input type="text" id="loginName"
				value="${info.loginName }"></td>
			<td class="info_left">电话号码</td>
			<td class="info_right"><input type="text" maxlength="11" id="userMobile"
				value="${info.mobile }"></td>
		</tr>
		<tr>
			<td class="info_left">微信ID</td>
			<td class="info_right">${info.wxOpenId }</td>
			<td class="info_left">用户姓名</td>
			<td class="info_right"><input type="text" id="userName"
				value="${info.userName }"></td>
		</tr>
		<tr>
			<td class="info_left">会员等级</td>
			<td class="info_right">${info.classLevel }</td>
			<td class="info_left">总积分</td>
			<td class="info_right">${info.totalScore }</td>
		</tr>
	</table>
	<button class="btn btn-primary"
		onclick="javascript:updateUserInfo(${info.travelerId})">更新</button>
</div>
<script>
function updateUserInfo(uid)
{
	if(loginName.value.length==0)
	{
		alert("对不起，请输入登录名。");
		loginName.focus();
		return;
	}
	if(userMobile.value.length==0)
	{
		alert("对不起，请输入手机号码。");
		userMobile.focus();
		return;
	}
	if(userName.value.length==0)
	{
		alert("对不起，请输入用户名。");
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

				alert("恭喜，会员信息更新成功！");

			} else {
				alert("对不起，会员信息更新失败，请稍候重试！");

			}
		} else {
			alert("对不起，网络故障请稍候重试！");
		}

	});
}
</script>
