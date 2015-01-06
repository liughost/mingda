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

input[type="checkbox"] {
	margin: 0;
}
</style>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>邀请码信息</legend>
	<form class="" action="${context }/member/invite/send" method="get">
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<input type="text" id="invitedMobile" maxlength= "11" name="invitedMobile" value=""
					placeholder="受邀者手机号码"> <input type="checkbox" id="isBind"
					name="isBind">此手机专用
				<button type="button" name="send" onclick="javascript:sendCode()"
					class="btn btn-primary" style="margin-top: -8px;" value="0">发码</button>
			</div>
		</div>

		<table>
			<tr>
				<td><select id="sType" name="sType">
						<option value="0">全部</option>
						<option value="1">受邀手机</option>
						<option value="3">邀请码</option>
				</select></td>
				<td><input type="text" id="sText" value="" name="sText"></td>
				<td><button type="submit" name="search"
						onclick="javascript:getList()" class="btn btn-primary" value="1"
						style="margin-top: -8px;">搜索</button></td>

			</tr>
		</table>
	</form>
	<legend>邀请码历史数据</legend>
	<table class="table table-striped table-bordered" id="inviteCodeList">
		<tr>
			<th style="width: 50px;"><input type="checkbox">全选</th>
			<th>邀请码</th>
			<th>受邀手机</th>
			<th>状态</th>
			<th>发送时间</th>
			<th>使用时间</th>
		</tr>
		<c:forEach var="items" items="${inviteList}">
			<tr>
				<td><input type="checkbox" name="sel_invite"></td>
				<td>${items.inviteCode}</td>
				<td>${items.dest_mobile}</td>
				<td>${items.codeStatus}</td>
				<td>${items.makeTime}</td>
				<td>${items.usedTime}</td>
			</tr>
		</c:forEach>
	</table>

</div>
<script>
	function IsNum(s) {
		if (s != null) {
			var r, re;
			re = /\d*/i; //\d表示数字,*表示匹配多个数字
			r = s.match(re);
			return (r == s) ? true : false;
		}
		return false;
	}
	function sendCode() {
		if (invitedMobile.value.length != 11 || !IsNum(invitedMobile.value)) {
			alert("请输入合法手机号码。");
			return;
		}
		var url = "${context}/member/invite/sendReq";
		debugger;
		$.post(url, {
			invitedMobile : invitedMobile.value,
			isBind : isBind.checked
		}, function(result, status) {
			//debugger;
			if (status == 'success') {

				if (result == '1') {

					alert('邀请码已经成功发送。');

				} else {
					//count.innerHTML = 0;
					alert('邀请码发送失败。');
				}
			} else {
				alert('网络异常，或者未登录，请重试。');
			}

		});
	}
</script>
