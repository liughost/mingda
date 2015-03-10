<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<s:url var="home" value="/home" />
<s:url var="forget" value="/forget" />
<s:url var="sign_in" value="/static/j_spring_security_check" />
<div class="bannerk">
	<div class="banner">
		<h3>很遗憾，登录失败，请确认用户名和密码后重新登录！<a class="modalLink"  style="color:blue;" href="#modal1">点击重新登录</a></h3>
		system error:${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
	</div>
</div>