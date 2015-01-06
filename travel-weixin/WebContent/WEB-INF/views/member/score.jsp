<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>会员列表</legend>
	<table>
		<tr>
			<td><select id="sType">
					<option value="1">手机号码</option>
					<option value="2">姓名</option>
					<option value="3">邀请码</option>
			</select></td>
			<td><input type="text" id="sText" value=""></td>
			<td><button class="btn btn-primary">搜索</button></td>
		</tr>
	</table>
	<table class="" id="memberList">
		<tr>
			<th><input type="checkbox">全选</th>
			<th>姓名</th>
			<th>手机号</th>
			<th>积分</th>
		</tr>
	</table>
</div>
<script>
	function fillMember(members) {
		var head = "";
		var body = '';

		memberList.innerHTML = head + body;
	}
	window.onload = defaultMembers;
</script>
