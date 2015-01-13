<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<s:url var="home" value="/home" />
<s:url var="dosignup" value="/dosignup"></s:url>
<div class="container form-horizontal">
	<div class="control-group">
		<label></label>
		<div class="controls">
			<h3>用户注册</h3>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="username"><span
			class="text-warning">*</span>登录账户</label>
		<div class="controls">
			<input id="username" class="form-control" name="username" type="text"
				required="required" /><span
				style="color: red; font-size: 14px; font-style: italic;"
				id="username_msg"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="login_pass"><span
			class="text-warning">*</span>登录密码</label>
		<div class="controls">
			<input id="pass" name="pass" required="required" type="password"
				class="form-control" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="exampleInputPassword"><span
			class="text-warning">*</span>确认密码</label>
		<div class="controls">
			<input id="confirm_pass" type="password" required="required"
				class="form-control" id="exampleInputPassword" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="mobile"><span
			class="text-warning">*</span>有效手机号码</label>
		<div class="controls">
			<input id="mobile" name="mobile" type="text" maxlength="11"
				class="form-control" required="required" />
			<button id="signup_validcode" class="btn btn-default">获取验证码</button>
			<span id="get_valid_info" class="text-info"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="get_valid_code">手机验证码</label>
		<div class="controls">
			<input id="validCode" type="text" class="form-control"
				required="required" id="get_valid_code" placeholder="请输入手机验证码" />
		</div>
	</div>
	<div class="control-group">
		<div class="controls">
			<button type="button" data-disable-with="正在确认..." id="btnRegister"
				class="btn btn-primary btn-lg" onclick="registerUser()">注册</button>
			<a class="btn btn-default btn-xs" href='<s:url value="/login"/>'>登录</a>
			<div class="alert" id="alert" style="display: none;">
				<h5 class="text-primary text-danger" id="msg"></h5>
			</div>
		</div>
	</div>

</div>
<script>
	function registerUser() {
		if (username.value.length == 0) {
			alert("请输入登录帐号。");
			username.focus();
			return;
		}
		if (pass.value.length == 0) {
			alert("请输入登录密码。");
			pass.focus();
			return;
		}
		if (confirm_pass.value.length == 0) {
			alert("请输入确认密码。");
			confirm_pass.focus();
			return;
		}
		if (mobile.value.length == 0) {
			alert("请输入电话号码。");
			mobile.focus();
			return;
		}
		if (validCode.value.length == 0) {
			alert("请输入验证码。");
			validCode.focus();
			return;
		}
		var url = "${context}/dosignup";
		$.post(url, {
			username : username.value,
			pass : pass.value,
			confirmPass : confirm_pass.value,
			mobile : mobile.value,
			validCode : validCode.value
		}, function(result, status) {
			debugger;
			if (status == 'success') {
				if (result.length > 0) {
					result = decodeURIComponent(result);
					$('#msg').text(result);
					document.getElementById("alert").style.display = "block";
					if (result.indexOf('成功') >= 0) {
						btnRegister.disabled = true;
					}
				} else {
					alert("注册失败，请稍候重试！");
				}
			} else {
				alert("网络忙，请稍候重试！");
			}

		});
	}
	window.onload = function() {
		$('#signup_validcode').click(function() {
			debugger;
			var mobile = $('#mobile').val();
			$('#get_valid_info').text('');
			if (mobile.length == 0) {
				document.getElementById('mobile').focus();
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
						signup_validcode.disabled = true;
					} else {
						alert("验证码发送失败，请稍候重试！");
					}
				} else {
					alert("网络忙，请稍候重试！");
				}

			});
		});
		$('#username').blur(function() {
			var url = "${context}/check/username";
			$.post(url, {
				username : username.value
			}, function(result, status) {

				debugger;
				if (status == 'success') {
					result = decodeURIComponent(result);
					$('#username_msg').text(result);
				}

			});
		});
	};

	
</script>