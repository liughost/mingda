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
					<li class="active"><a href="#">我的邀请</a></li>
					<!-- active为当前状态 -->
					<li><a href="${context }/member/order/list">我的订单</a></li>
					<li><a href="#">我的旅伴</a></li>
					<li><a class="modalLink" href="#modal4">修改密码</a></li>
				</ul>
			</div>
			<div class="use-r">
				<div class="use-rt">邀请码信息</div>
				<div class="use-vite-t" style="padding: 20px 0 10px;">
					<input type="text" class="u-txt1" id="invitedMobile" /> <span><input
						type="checkbox" value="vite" name="vite" checked id="isBind" />
						此手机专用</span> <input type="button" class="u-btn1"
						onclick="javascript:sendCode()" value="发码" />
				</div>
				<div class="use-rt">发送历史</div>
				<div class="use-vite-b">

					<div class="use-vite-bt">
						<form id="frmInvite" action="${context }/member/invite/send"
							method="get">
							<select id="sType" name="sType">
								<option value="0" selected="">全部</option>
								<option value="3">受邀手机</option>
								<option value="1">邀请码</option>
							</select> <input type="text" class="u-txt1" value="${sText }" id="sText"
								name="sText" /> <input type="button" class="inputbg1"
								value="搜索" title="搜索" onclick="javascript:codeSearch()" />
						</form>
					</div>
					<div class="use-vite-bb">
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<thead>
								<tr>
									<th width="50" align="left"><input type="checkbox"
										onclick="sel_all(this);" /> 全选</th>
									<th width="120">邀请码</th>
									<th width="120">受邀手机</th>
									<th width="70">状态</th>
									<th>发送时间</th>
									<th>使用时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="items" items="${inviteList}">
									<tr>
										<td><input type="checkbox" name="sel_invite"
											value="${items.id}"></td>
										<td>${items.inviteCode}</td>
										<td>${items.dest_mobile}</td>
										<td><c:choose>
												<c:when test="${items.codeStatus==0}">未使用</c:when>
												<c:otherwise>已使用</c:otherwise>
											</c:choose></td>
										<td>${items.makeTime}</td>
										<td><c:choose>
												<c:when test="${items.codeStatus==0}">--</c:when>
												<c:otherwise>
												${items.usedTime }</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						 <input
							type="button" class="u-btn1" value="重新发送" onclick="sendAgain()"
							style="margin: 10px 0 0 0px; height: 40px; line-height: 40px;">
							<br/>
							<label style="color: red" id="sendAgain_error"></label>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 主体内容区 End -->
<script>
	//window.onload=function(){showPage(0, ${fn:length(inviteList)});};
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
				var msg = $("#sendAgain_error").text();
				if (status == 'success') {
					result = eval(result);
					if (result[0] == 1) {

						msg += '邀请码：' + result[1] + '邀请码已经成功发送。';

						//location.href = "${context}/member/invite/send";
					} else if (result == -2) {
						msg += '邀请码' + result[1] + '不能发送给自己。';
					} else {
						//count.innerHTML = 0;
						msg += '邀请码' + result[1] + '已经过期或者被使用，发送失败。';
					}
				} else {
					msg += '网络异常，或者未登录，请重试。';
				}
				$("#sendAgain_error").text(msg);
			});
		}//end for

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
	function codeSearch() {
		debugger;
		frm = document.getElementById("frmInvite");
		//page.value = 0;
		frm.submit();
	}
	function getList(p) {
		debugger;
		frm = document.getElementById("frmInvite");
		//page.value = p;
		frm.submit();
	}
	function sel_all(chkObj) {
		debugger;
		var chks = document.getElementsByName("sel_invite");
		for (var i = 0; i < chks.length; i++) {
			chks[i].checked = chkObj.checked;
		}
	}
</script>
