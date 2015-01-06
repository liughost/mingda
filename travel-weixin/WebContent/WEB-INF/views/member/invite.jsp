<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>邀请码列表</legend>
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
	<table class="table table-striped table-bordered" id="inviteCodeList">

	</table>

</div>
<script>
	function sel_all(chk) {
		var chks = document.getElementsByName("memberSel");
		for ( var i = 0; i < chks.length; i++) {
			chks[i].checked = chk.checked;
		}
	}
	
	function formatDate(d) {
		var dd = new Date(d);
		
		return dd.getFullYear() + '-' + (dd.getMonth() + 1) + "-" + dd.getDate()
				+ ' ' + dd.getHours() + ':' + dd.getMinutes() + ':'
				+ dd.getSeconds();
	}
	
	Date.prototype.format = function(format) //author: meizz
	{
	  var o = {
	    "M+" : this.getMonth()+1, //month
	    "d+" : this.getDate(), //day
	    "h+" : this.getHours(), //hour
	    "m+" : this.getMinutes(), //minute
	    "s+" : this.getSeconds(), //second
	    "q+" : Math.floor((this.getMonth()+3)/3), //quarter
	    "S" : this.getMilliseconds() //millisecond
	  }
	  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  for(var k in o)if(new RegExp("("+ k +")").test(format))
	    format = format.replace(RegExp.$1,
	      RegExp.$1.length==1 ? o[k] :
	        ("00"+ o[k]).substr((""+ o[k]).length));
	  return format;
	}
	function fillInvite(inviteCodes) {
		var head = '<tr><th><input onchange="sel_all(this)" style="margin-top: -3px;" type="checkbox">全选</th>';
		head += '<th>姓名</th><th>手机号</th><th>登录名</th><th>邀请码</th><th>码类型</th><th>生成时间</th><th>本次积分</th><th>被邀者手机</th><th>优惠额度</th><th>状态</th><th>使用时间</th></tr>';
		var body = '';
		var codeType = [ '注册码', '优惠码' ];
		var codeStatus = [ '未使用', '已使用', '已禁用', '已失效' ];
		debugger;
		for ( var i = 0; i < inviteCodes.length; i++) {
			body += '<tr><td><input name="memberSel" style="margin-top: -3px;" type="checkbox"></td>';

			body += '<td>' + decodeURIComponent(inviteCodes[i].member.userName)
					+ '</td>';
			body += '<td>' + inviteCodes[i].member.mobile + '</td>';
			body += '<td>'
					+ decodeURIComponent(inviteCodes[i].member.loginName)
					+ '</td>';

			body += '<td>' + inviteCodes[i].inviteCode + '</td>';
			body += '<td>' + codeType[inviteCodes[i].codeType] + '</td>';

			body += '<td>' + formatDate(inviteCodes[i].makeTime) + '</td>';
			body += '<td>' + inviteCodes[i].score + '</td>';
			body += '<td>' + inviteCodes[i].dest_mobile + '</td>';
			body += '<td>' + inviteCodes[i].offPrice + '</td>';
			body += '<td>' + codeStatus[inviteCodes[i].codeStatus] + '</td>';
			body += '<td>' + formatDate(inviteCodes[i].usedTime) + '</td>';

		}
		inviteCodeList.innerHTML = head + body;
	}
	function getList() {

		var url = "${context}/member/invite/list";
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

									fillInvite(ms);
								} else {
									//count.innerHTML = 0;
									inviteCodeList.innerHTML = "<span>对不起，没有符合条件的邀请码记录，请放宽条件重新试一下。</span>";
								}
							} else {
								inviteCodeList.innerHTML = "<span>对不起，访问失败，请刷新后重试！。</span>";
							}

						});
	}
	window.onload = function() {
		console.log('window onload.');

		getList();
	};
</script>
