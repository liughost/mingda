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
					<option value="0">全部</option>
					<option value="1">手机号码</option>
					<option value="2">姓名</option>
					<option value="3">邀请码</option>
			</select></td>
			<td><input type="text" id="sText" value=""></td>
			<td><button onclick="javascript:getList(0)"
					class="btn btn-primary" style="margin-top: -8px;">搜索</button></td>
		</tr>
	</table>
	<table class="table table-striped table-bordered" id="memberList">

	</table>
	<div class="pagination">
		<ul id="pageZone">

		</ul>
	</div>
</div>
<script>
	function sel_all(chk) {
		var chks = document.getElementsByName("memberSel");
		for ( var i = 0; i < chks.length; i++) {
			chks[i].checked = chk.checked;
		}
	}
	function fillMember(members, page) {
		var head = '<tr><th><input onchange="sel_all(this)" style="margin-top: -3px;" type="checkbox">全选</th><th>报名参团</th><th>邀请信息</th><th>姓名</th><th>手机号</th><th>登录名</th><th>微信号</th><th>会员级别</th><th>积分</th><th>国籍驾照</th></tr>';
		var body = '';
		var classLevel = [ '非会员', '普通会员', '友情会员', 'VIP会员' ];
		for ( var i = 0; i < members.length; i++) {
			body += '<tr><td><input name="memberSel" style="margin-top: -3px;" type="checkbox"></td>';
			body += '<td><a href="${context}/member/book?memId='
					+ members[i].travelerId + '">报名参团</a></td>';//报名参团
			body += '<td><a href="${context}/member/invite?memId='
					+ members[i].travelerId + '">邀请信息</a></td>';//邀请信息
			body += '<td>' + decodeURIComponent(members[i].userName) + '</td>';//姓名
			body += '<td>' + members[i].mobile + '</td>';//手机号
			body += '<td>' + decodeURIComponent(members[i].loginName) + '</td>';//登录名
			body += '<td>' + members[i].wxOpenId + '</td>';//微信号
			body += '<td>' + classLevel[members[i].classLevel] + '</td>';//会员级别
			body += '<td>' + members[i].totalScore + '</td>';//积分
			body += '<td><a href="${context}/member/driverCard?memId='
					+ members[i].travelerId + '">国籍驾照</a></td></tr>';//国籍驾照
		}
		memberList.innerHTML = head + body;

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
		if ( len >= pageCount) {
			page_str += '<li><a href="javascript:getList(' + (page + 1)
					+ ')">下一页</a></li>';
		}
		pageZone.innerHTML = page_str;
	}
	function getList(page) {

		var url = "${context}/member/getList";
		console.log("call getLlist function.url=" + url);
		$
				.post(
						url,
						{
							sType : sType.value,
							sText : sText.value,
							page : page
						},
						function(result, status) {
							//debugger;
							if (status == 'success') {

								var ms = eval(result);

								if (ms != null && ms.length > 0) {

									fillMember(ms, page);
									showPage(page, ms.length);
								} else {
									//count.innerHTML = 0;
									memberList.innerHTML = "<span>对不起，没有符合条件的会员，请放宽条件重新试一下。</span>";
									showPage(page, 0);
								}
							} else {
								memberList.innerHTML = "<span>对不起，访问失败，请刷新后重试！。</span>";
							}

						});
	}
	window.onload = function() {
		console.log('window onload.');

		getList(0);
	};
</script>
