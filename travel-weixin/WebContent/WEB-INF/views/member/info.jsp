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
.table td{
border-top: 0px;
border-bottom: 1px solid #dddddd;
}
</style>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>会员信息</legend>
	<table class="table" >
		<tr>
			<td class="info_left">登录名</td>
			<td class="info_right">${info.loginName }</td>
			<td class="info_left">电话号码</td>
			<td class="info_right">${info.mobile }</td>
		</tr>
		<tr>
			<td class="info_left">微信ID</td>
			<td class="info_right">${info.wxOpenId }</td>
			<td class="info_left">用户姓名</td>
			<td class="info_right">${info.userName }</td>
		</tr>
		<tr>
			<td class="info_left">会员等级</td>
			<td class="info_right">${info.classLevel }</td>
			<td class="info_left">总积分</td>
			<td class="info_right">${info.totalScore }</td>
		</tr>
	</table>
	
</div>
<script>

</script>
