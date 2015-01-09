<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:url var="home" value="/home" />
<s:url var="forget" value="/forget" />



<s:url var="sign_in" value="/static/j_spring_security_check" />
<div class="container" style="max-width: 960px;">
	
	<form accept-charset="UTF-8" action="${sign_in}"
		class="new_user form-horizontal" method="post" role="form"
		class="form-horizontal">
		<legend>登录</legend>
		<div class="control-group">
			<label class="control-label" for="user_login">登录名</label>
			<div class="controls">
				<input id="user_login" name="j_username" placeholder="账户"
					type="text" class="form-control" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="user_password"> <span
				class="control-label" style="margin-left: 0px;">密码 </span>
			</label>
			<div class="controls">
				<input id="user_password" name="j_password" type="password"
					id="inputPassword" class="form-control" placeholder="密码" />
					<br/><a href="${forget}">忘记密码</a>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<label class="checkbox"> <input
					name="_spring_security_remember_me" type="hidden" value="0" /> <input
					id="user_remember_me" name="_spring_security_remember_me"
					type="checkbox" value="1" /> 记住密码
				</label>
			</div>
		</div>


		<div class="control-group">
			<div class="controls">
				<button type="submit" class="btn">登录</button>
			</div>
		</div>

	</form>
</div>

<script type="text/javascript">
	$('#user_login').focus();
</script>
<div class='row-fluid muted' style="display: none">
	<div class='span6'>
		<span class="text-warning">未注册</span> <span
			class="glyphicon glyphicon-user"></span>&nbsp; <span
			class="glyphicon glyphicon-hand-right"></span> <a
			class="btn btn-default btn-xs" href='<s:url value="/signup"/>'>注册</a>
	</div>
</div>


