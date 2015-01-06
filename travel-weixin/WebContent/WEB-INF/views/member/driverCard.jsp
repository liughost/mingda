<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>国籍驾照列表</legend>
	<table>
		<tr>
			<td><select id="sType">
					<option value="0">全部</option>
					<option value="1">手机号码</option>
					<option value="2">姓名</option>
					<option value="3">邀请码</option>
			</select></td>
			<td><input type="text" id="sText" value=""></td>
			<td><button onclick="javascript:getList()"
					class="btn btn-primary" style="margin-top: -8px;">搜索</button></td>
		</tr>
	</table>
	<table class="table table-striped table-bordered" id="memberList">

	</table>

</div>
<script>
	function sel_all(chk) {
		var chks = document.getElementsByName("memberSel");
		for ( var i = 0; i < chks.length; i++) {
			chks[i].checked = chk.checked;
		}
	}
	function fillMember(members) {
		var head = '<tr><th><input onchange="sel_all(this)" style="margin-top: -3px;" type="checkbox">全选</th><th>报名参团</th><th>邀请信息</th><th>姓名</th><th>手机号</th><th>登录名</th><th>微信号</th><th>会员级别</th><th>积分</th><th>国籍驾照</th></tr>';
		var body = '';
		var classLevel = [ '非会员', '普通会员', '友情会员', 'VIP会员' ];
		for ( var i = 0; i < members.length; i++) {
			body += '<tr><td><input name="memberSel" style="margin-top: -3px;" type="checkbox"></td>';
			body += '<th><a href="${context}/member/book?memId='
					+ members[i].travelerId + '">报名参团</a></th>';//报名参团
			body += '<th><a href="${context}/member/invite?memId='
					+ members[i].travelerId + '">邀请信息</a></th>';//邀请信息
			body += '<th>' + decodeURIComponent(members[i].userName) + '</th>';//姓名
			body += '<th>' + members[i].mobile + '</th>';//手机号
			body += '<th>' + decodeURIComponent(members[i].loginName) + '</th>';//登录名
			body += '<th>' + members[i].wxOpenId + '</th>';//微信号
			body += '<th>' + classLevel[members[i].classLevel] + '</th>';//会员级别
			body += '<th>' + members[i].totalScore + '</th>';//积分
			body += '<th><a href="${context}/member/driverCard?memId='
					+ members[i].travelerId + '">国籍驾照</a></th></tr>';//国籍驾照
		}
		memberList.innerHTML = head + body;
	}
	function getList() {

		var url = "${context}/member/getList";
		console.log("call getLlist function.url=" + url);
		$
				.post(
						url,
						{
							sType : sType.value,
							sText : sText.value,
							page : 0
						},
						function(result, status) {
							//debugger;
							if (status == 'success') {

								var ms = eval(result);

								if (ms != null && ms.length > 0) {

									fillMember(ms);
								} else {
									//count.innerHTML = 0;
									memberList.innerHTML = "<span>对不起，没有符合条件的会员，请放宽条件重新试一下。</span>";
								}
							} else {
								memberList.innerHTML = "<span>对不起，访问失败，请刷新后重试！。</span>";
							}

						});
	}
	window.onload = function() {
		console.log('window onload.');

		getList();
	};
</script>
