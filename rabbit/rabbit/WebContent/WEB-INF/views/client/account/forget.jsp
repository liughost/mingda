<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<s:url var="resource" value="/resources"></s:url>
<c:if test="${IsMobile }">
	<div style="height: 20px;"></div>
</c:if>
<div class="container">

	<div class="container form-horizontal">

		<div class="control-group">
			<div class="controls">
				<h3>重置密码</h3>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="mobile"><span
				class="text-warning">*</span>已绑定手机号:</label>
			<div class="controls">
				<input type="text" required="required" id="forget_mobile" /><a
					id="getvalidcode" class="btn btn-default">获取验证码</a> <span
					id="get_valid_info" class="text-info"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="get_valid_code"><span
				class="text-warning">*</span>验证码:</label>
			<div class="controls">
				<input type="text" class="form-control" required="required"
					id="get_valid_code" placeholder="输入验证码" /><br /> <span
					id="get_valid_info" class="text-info"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="new_pass"><span
				class="text-warning">*</span>新的密码:</label>
			<div class="controls">
				<input name="new_pass" type="password" class="form-control"
					required="required" id="new_pass" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="second_pass"><span
				class="text-warning">*</span>再次确认:</label>
			<div class="controls">
				<input name="second_pass" type="password" class="form-control"
					required="required" id="second_pass" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<button type="button" data-disable-with="正在确认..." id="rest_pwd"
					class="btn btn-warning btn-lg">重置</button>
				<span class="text-warning">${Message.info}</span>
			</div>
		</div>

	</div>

</div>


<script>
	$('#getvalidcode').click(function() {
		debugger;
		var mobile = $('#forget_mobile').val();
		$('#get_valid_info').text('');
		if (mobile.length == 0) {
			document.getElementById('forget_mobile').focus();
			$('#get_valid_info').text('请输入手机号码。');
			return;
		}
		var url = "${context}/signup/sendValidCode";
		$.post(url, {
			mobile : mobile,
		}, function(result, status) {
			debugger;
			if (status == 'success') {
				if (result == 1) {
					alert("验证码已经发送到您的手机，请注意查收。此验证码在2小时内有效。");
					getvalidcode.disabled = true;
				} else {
					alert("验证码发送失败，请稍候重试！");
				}
			} else {
				alert("网络忙，请稍候重试！");
			}

		});
	});

	$('#rest_pwd').click(function() {
		if (forget_mobile.value.length == 0) {
			alert("请输入绑定的手机号。");
			forget_mobile.focus();
			return;
		}
		if (get_valid_code.value.length == 0) {
			alert("请输入验证码。");
			get_valid_code.focus();
			return;
		}
		if (new_pass.value.length == 0) {
			alert("请输入新密码。");
			new_pass.focus();
			return;
		}
		if (second_pass.value.length == 0) {
			alert("请输入确认密码。");
			second_pass.focus();
			return;
		}
		if (new_pass.value != second_pass.value) {
			alert("密码和确认密码不一致，请重新输入。");
			second_pass.focus();
			return;
		}
		debugger;

		var url = "${context}/resetPwd";
		$.post(url, {
			mobile : forget_mobile.value,
			validateCode : get_valid_code.value,
			pass : new_pass.value,
			confirmPass : second_pass.value,
		}, function(result, status) {
			debugger;
			if (status == 'success') {
				if (result == 0) {
					alert("密码已经重新设置，请用新密码登录!");
					rest_pwd.disabled = true;
				} else if (result == -1) {
					alert("对不起，验证码错误，请确认后重试!");
				} else if (result == -2) {
					alert("对不起，密码和确认密码不一致，请重新输入!");
				}
			} else {
				alert("网络忙，请稍候重试！");
			}

		});
	});
</script>
