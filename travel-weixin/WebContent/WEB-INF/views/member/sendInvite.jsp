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
	<form id="frmInvite" action="${context }/member/invite/send"
		method="get">
		<input type="hidden" name="page" id="page" value="${page }">
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<input type="text" id="invitedMobile" maxlength="11"
					required="required" name="invitedMobile" value=""
					placeholder="受邀者手机号码"> <input type="checkbox" id="isBind"
					name="isBind">此手机专用
				<button type="button" name="send" onclick="javascript:sendCode()"
					class="btn btn-primary" style="margin-top: -8px;" value="0">发码</button>
			</div>
		</div>


	</form>
	<legend>发送历史</legend>
	<table>
		<tr>
			<td><select id="sType" name="sType">
					<option value="0" <c:if test="${sType==0}">selected</c:if>>全部</option>
					<option value="3" <c:if test="${sType==1}">selected</c:if>>受邀手机</option>
					<option value="1" <c:if test="${sType==3}">selected</c:if>>邀请码</option>
			</select></td>
			<td><input type="text" id="sText" value="${sText }" name="sText"></td>
			<td><button type="button" name="search"
					onclick="javascript:codeSearch()" class="btn btn-primary" value="1"
					style="margin-top: -8px;">搜索</button></td>

		</tr>
	</table>
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
				<td><input type="checkbox" name="sel_invite"
					value="${items.id}"></td>
				<td>${items.inviteCode}</td>
				<td>${items.dest_mobile}</td>
				<td><c:if test="${items.codeStatus==0}">未使用</c:if>
					<c:if test="${items.codeStatus!=0}">已使用</c:if></td>
				<td>${items.makeTime}</td>
				<td><c:if test="${items.codeStatus==0}">--</c:if>
					<c:if test="${items.codeStatus!=0}">${items.usedTime }</c:if></td>
			</tr>
		</c:forEach>
	</table>
	<button class="btn btn-primary" onclick="sendAgain()">重新发送</button>

	<div class="pagination">
		<ul id="pageZone">

		</ul>
	</div>
</div>
<script>

showPage(0, ${fn:length(inviteList)});
	function sendAgain() {
		var url = "${context}/member/invite/resend";
		debugger;
		var objs = document.getElementsByName("sel_invite");
		for (var i = 0; i < objs.length; i++) {
			if (!objs[i].checked)
				continue;
			$.post(url, {
				id : objs[i].value
			}, function(result, status) {
				debugger;
				if (status == 'success') {

					if (result == 1) {
						alert('邀请码已经成功发送。');
						location.href = "${context}/member/invite/send";
					}
					else if (result == -2)
						{
						alert('对不起，邀请码不能发送给自己。');
						}
					else {
						//count.innerHTML = 0;
						alert('邀请码发送失败。');
					}
				} else {
					alert('网络异常，或者未登录，请重试。');
				}

			});
		}
	}
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
	function showPage(page, len) {
		var pageCount = 20;
		//debugger;
		console.log('page:' + page);
		var page_str = '';
		//上一页
		if (page > 0) {
			page_str += '<li><a href="javascript:getList(' + (page - 1)
					+ ');">上一页</a></li>';
		}

		//下一页
		if (len >= pageCount) {
			page_str += '<li><a href="javascript:getList(' + (page + 1)
					+ ')">下一页</a></li>';
		}
		pageZone.innerHTML = page_str;
	}
	function codeSearch()
	{
		debugger;
		frm = document.getElementById("frmInvite");
		page.value = 0;
		frm.submit();
	}
	function getList(p)
	{
		debugger;
		frm = document.getElementById("frmInvite");
		page.value = p;
		frm.submit();
	}
</script>
