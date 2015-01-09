<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<s:url var="home" value="/home" />
<s:url var="dosignup" value="/dosignup"></s:url>
<div class="container">
	<div class="page-header">

		<h1>
			<small>用户注册</small>
		</h1>
	</div>



	<div class="form-group">
		<label for="username"><span class="text-warning">*</span>登录账户</label>
		<span style="color: red; font-size: 24px; font-style: italic;"
			id="username_msg"></span> <input id="username" class="form-control"
			name="username" type="text" required="required" />
	</div>
	<div class="form-group">
		<label for="login_pass"><span class="text-warning">*</span>登录密码</label>
		<input id="pass" name="pass" required="required" type="password"
			class="form-control" />
	</div>
	<div class="form-group">
		<label for="exampleInputPassword"><span class="text-warning">*</span>确认密码</label>
		<input id="confirm_pass" type="password" required="required"
			class="form-control" id="exampleInputPassword" />
	</div>
	<div class="form-group">
		<label for="mobile"><span class="text-warning">*</span>有效手机号码</label>
		<input id="mobile" name="mobile" type="text" maxlength="11"
			class="form-control" required="required" />
	</div>
	<div class="form-group">
		<label for="get_valid_code"><button id="signup_validcode"
				class="btn btn-default">获取验证码</button> <span id="get_valid_info"
			class="text-info"></span></label> <input id="validCode" type="text"
			class="form-control" required="required" id="get_valid_code" />
	</div>
	<button type="button" data-disable-with="正在确认..." id="btnRegister"
		class="btn btn-primary btn-lg" onclick="registerUser()">注册</button>
	<div style="height: 15px;">
		<h5 class="text-primary text-danger" id="msg"></h5>
	</div>
	<p class='muted'>
		<span class="glyphicon glyphicon-user"></span> <span
			class="text-warning">已注册</span> &nbsp;<span
			class="glyphicon glyphicon-hand-right"></span> <a
			class="btn btn-default btn-xs" href='<s:url value="/login"/>'>登录</a>
	</p>
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

		});
	};

	
</script>