<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 公用底部 STA -->
<div class="footerk">
	<div class="footerk-t">
		<div class="footer">
			<div class="footer-t">
				<div class="footer-tl">
					<div class="title">我们的服务</div>
					<div class="cont">
						兔子旅行的方式是私家小团或订制小团的自驾游。<br /> 行程具有自由、个性、灵活与可定制等特点。<br />
						我们的客户是追求人格独立和心性自由的旅行者。
					</div>
				</div>
				<div class="footer-tr">
					<div class="title">联系我们</div>
					<p>400-6910-800</p>
					<div class="cont">地址：北京市朝阳区潘家园南里12号潘家园大厦北区319室</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footerk-b">
		<div class="footer">
			<div class="footer-m">
				<div class="footer-ml">
					<img src="${context }/resources/rabbit/images/bot-logo.gif" alt="" />
				</div>
				<div class="footer-mm">
					<ul>
						<li class="title">前往美国</li>
						<li><a href="${context }/custom/pdetail?id=125">去美国前的准备和注意事项</a></li>
						<li><a href="${context }/custom/pdetail?id=124">在美国开车自驾须知</a></li>
						<li><a href="${context }/custom/pdetail?id=123">常用车型介绍</a></li>
						<li><a href="${context }/custom/pdetail?id=699">你的驾照出国还能用吗？</a></li>
					</ul>
					<ul>
						<li class="title">服务条款</li>
						<li><a href="#">我想预约服务，如何进行？</a></li>
						<li><a href="#">有交易纠纷怎么办？</a></li>
						<li><a href="#">如何发布服务？</a></li>
					</ul>
					<ul>
						<li class="title">服务内容</li>
						<li><a href="#">发布服务</a></li>
						<li><a href="#">找旅伴</a></li>
						<li><a href="#">结伴出游</a></li>
						<li><a href="${context }/custom/articles">旅游分享</a></li>
					</ul>
				</div>
				<div class="footer-mr">
					<img src="${context }/resources/rabbit/images/qr.gif" alt="" />
					<p>微信号：zwxzjz</p>
				</div>
			</div>
			<div class="footer-b">
				<div class="footer-bt">
					友情链接：<a target="_blank" href="http://usa-home.cn/">美国房产精英</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
						target="_blank" href="http://51766.com/">51766</a>
				</div>
				<div class="footer-bb">
					技术支持：北京智网明达信息技术有限公司&copy;2015 京ICP备11001537号-3
					<script type="text/javascript">
						var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
								: " http://");
						document
								.write(unescape("%3Cspan id='cnzz_stat_icon_1254508119'%3E%3C/span%3E%3Cscript src='"
										+ cnzz_protocol
										+ "s11.cnzz.com/z_stat.php%3Fid%3D1254508119%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));
					</script>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 公用底部 End -->

<!-- 返回顶部 STA -->
<div id="backToTop">
	<a href="javascript:;" onfocus="this.blur();" class="backToTop_a"
		title="返回顶部">返回顶部</a>
</div>

<script type="text/javascript">
	$(document).ready(function(e) {
		$("#backToTop").live("click", function() {
			var _this = $(this);
			$('html,body').animate({
				scrollTop : 0
			}, 500, function() {
				_this.hide();
			});
		});

		$(window).scroll(function() {
			var htmlTop = $(document).scrollTop();
			if (htmlTop > 0) {
				$("#backToTop").show();
			} else {
				$("#backToTop").hide();
			}
		});
	});
	function searchCity() {
		var selCities = document.getElementsByName("sel_city");
		var cities = "";
		for (var i = 0; i < selCities.length; i++) {
			if (selCities[i].checked)
				cities += selCities[i].value + ",";

		}
		location.href = "${context}/custom/tour/search?city=" + cities;
	}

	function testLogin() {
		$("#username_error").text("");
		$("#pwd_error").text("");
		if (username.value == 0) {
			$("#username_error").text("请输入登录名。");
			return false;
		}
		if (pwd.value == 0) {
			$("#pwd_error").text("请输入登录密码。");
			return false;
		}
		return true;
	}
	function registerUser() {
		$("#realname_error").text("");
		$("#pwd2").text("");
		$("#pwd3").text("");
		$("#mobile").text("");
		$("#authcode").text("");
		if (realname.value.length == 0) {
			$("#realname_error").text("请输入登录帐号。");
			realname.focus();
			return;
		}
		if (pwd2.value.length == 0) {
			$("#pwd2").text("请输入登录密码。");
			pwd2.focus();
			return;
		}
		if (pwd3.value.length == 0) {
			$("#pwd3").text("请输入确认密码。");
			pwd3.focus();
			return;
		}
		if (mobile.value.length == 0) {
			$("#mobile").text("请输入电话号码。");
			mobile.focus();
			return;
		}
		if (authcode.value.length == 0) {
			$("#authcode").text("请输入验证码。");
			authcode.focus();
			return;
		}
		var url = "${context}/dosignup";
		$.post(url, {
			username : realname.value,
			pass : pwd2.value,
			confirmPass : pwd3.value,
			mobile : mobile.value,
			validCode : authcode.value
		}, function(result, status) {
			debugger;
			if (status == 'success') {
				if (result.length > 0) {
					result = decodeURIComponent(result);
					//$('#msg').text(result);
					//document.getElementById("alert").style.display = "block";
					if (result.indexOf('成功') >= 0) {
						//btnRegister.disabled = true;
						pwd.value = pwd2.value;
						username.value = realname.value;
						frmLogin.submit();
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
		/*注册时发送手机验证码*/
		$('#signup_validcode').click(function() {
			debugger;
			var mobile = $('#mobile').val();
			$('#mobile_error').text('');
			if (mobile.length != 11) {
				document.getElementById('mobile').focus();
				$('#mobile_error').text('请输入正确的手机号码。');
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
		});//end for validatecode.click
		/*注册时检验用户名是否重复*/
		$('#realname').blur(function() {
			if (realname.value.length == 0)
				return;
			var url = "${context}/check/username";
			$.post(url, {
				username : realname.value
			}, function(result, status) {

				debugger;
				if (status == 'success') {
					result = decodeURIComponent(result);
					if (result.length > 0) {
						$('#realname_error').text(result);
					} else {
						$('#realname_error').text("");
					}
				}

			});
		});//end for #realname.blur

		/**忘记密码相关函数 begin */
		$('#getvalidcode').click(function() {
			debugger;
			var mobile = $('#mobile1').val();
			$('#mobile1_error').text('');
			if (mobile.length != 11) {
				document.getElementById('mobile1').focus();
				$('#mobile1_error').text('请输入正确手机号码。');
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

		/*重置密码*/
		$('#rest_pwd').click(function() {
			if (mobile1.value.length != 11) {
				$("#mobile1_error").text("请输入绑定的手机号。");
				mobile1.focus();
				return;
			}
			if (authcode1.value.length == 0) {
				$("#authcode1_error").text("请输入手机验证码。");
				authcode1.focus();
				return;
			}
			if (pwd4.value.length == 0) {
				$("#pwd4_error").text("请输入新密码。");
				pwd4.focus();
				return;
			}
			if (pwd5.value.length == 0) {
				$("#pwd5_error").text("请输入确认密码。");
				pwd5.focus();
				return;
			}
			if (pwd4.value != pwd5.value) {
				$("#pwd4_error").text("密码和确认密码不一致，请重新输入。");
				pwd4.focus();
				return;
			}
			debugger;

			var url = "${context}/resetPwd";
			$.post(url, {
				mobile : mobile1.value,
				validateCode : authcode1.value,
				pass : pwd4.value,
				confirmPass : pwd5.value,
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
		/**忘记密码相关函数 end */

	};//end for window.onload
</script>
<!-- 返回顶部 End -->

<div class="overlay"></div>
<!-- 公用背景色 -->
<!-- 搜索弹框 STA -->
<div id="modal3" style="display: none;" class="modal hide fade">
	<div class="modal-header">
		<button type="button" class="close closeBtn">×</button>
		<h3>目的地城市选择</h3>
	</div>
	<div class="modal-body">
		<ul class="modal-nav nav-tabs" style="border-bottom: 0px;">
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="拉斯维加斯">拉斯维加斯</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="洛杉矶">洛杉矶</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="旧金山">旧金山</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="弗雷斯诺">弗雷斯诺</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="夏威夷">夏威夷</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="奥兰多">奥兰多</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="盐湖城">盐湖城</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="蒙特利">蒙特利</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="迈阿密">迈阿密</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="芝加哥">芝加哥</label></li>
			<li><label class="checkbox"><input name="sel_city"
					type="checkbox" value="摩押">摩押</label></li>
		</ul>
	</div>
	<div class="modal-footer">
		<a href="#" onclick="javascript:;" class="btn closeBtn">关闭</a> <a
			href="javascript:searchCity()" class="btn btn-primary">确认</a>
	</div>
</div>
<!-- 搜索弹框 End -->

<!-- 登录弹框 STA -->
<div id="modal1" class="modal">
	<form action="${context }/static/j_spring_security_check" method="POST"
		id="frmLogin" onsubmit="return testLogin()">
		<div class="login">
			<ul class="login-t">
				<li class="loginTag"><a class="modalLink" href="#modal1">登录</a></li>
				<li><a class="modalLink" href="#modal2">注册</a></li>
			</ul>
			<div class="loginCont loginTag">
				<ul class="login-b">
					<li><input type="text" class="loginTxt" value=""
						placeholder="登录名" id="username" name="j_username" tabindex="1" />
						<label id="username_succeed" class="blank"></label>
						<div id="username_error" style="color: red;"></div></li>
					<li><input type="password" class="loginTxt" value=""
						placeholder="密码" id="pwd" name="j_password" tabindex="2" /> <label
						id="pwd_succeed" class="blank"></label> <label id="pwd_error"
						style="color: red;"></label></li>
					<li>
						<div class="login-bl">
							<input type="checkbox" checked /> 记住密码
						</div>
						<div class="login-br">
							<a class="modalLink" href="#modal4">忘记密码？</a>
						</div>
					</li>
					<li><input type="submit" class="loginBtn1" value="登录" /></li>

					<li style="display: none;"><input type="button"
						onclick='javascript:$("#modal2").modal();$("#modal2").show();'
						class="loginBtn2" value="注册" /></li>

				</ul>
			</div>
		</div>
	</form>
</div>
<!-- 登录弹框 End -->
<!-- 注册弹框 Begin -->
<div id="modal2" class="modal">
	<div class="login">
		<ul class="login-t">
			<li><a class="modalLink" href="#modal1">登录</a></li>
			<li class="loginTag"><a class="modalLink" href="#modal2">注册</a></li>
		</ul>
		<div class="loginCont loginTag">
			<ul class="login-b">
				<li><input type="text" class="loginTxt" value=""
					placeholder="登录名" id="realname" name="realname" tabindex="3" /> <label
					id="realname_succeed" class="blank"></label> <label
					id="realname_error" style="color: red;"></label></li>
				<li><input type="password" id="pwd2" name="pwd2" class="loginTxt"
					value="" placeholder="登录密码" tabindex="4" /> <label
					id="pwd2_succeed" class="blank"></label> <label id="pwd2_error"></label></li>
				<li><input type="password" id="pwd3" name="pwd3" class="loginTxt"
					value="" placeholder="确认密码" tabindex="5" /> <label
					id="pwd3_succeed" class="blank"></label> <label id="pwd3_error"></label></li>
				<li><input type="text" id="mobile" name="mobile"
					class="loginTxt2" value="" placeholder="有效手机号" tabindex="6" /> <input
					type="button" id="signup_validcode" class="loginBtn3" value="获取验证码" />
					<br /> <br /> <br /> <label id="mobile_succeed" class="blank"></label>
					<label id="mobile_error" style="color: red;"></label></li>

				<li><input type="text" id="authcode" name="authcode"
					class="loginTxt" value="" placeholder="手机验证码" /> <label
					id="authcode_succeed" class="blank invisible"></label> <label
					id="authcode_error"></label></li>
				<li class="btny"><input type="button" class="loginBtn1"
					onclick="registerUser();" value="注册" style="width: 100%" /> <!--  <input type="button"
					class="loginBtn2" value="登录" onclick=""/> --></li>
			</ul>
		</div>
	</div>
</div>
<!-- 注册弹框 End -->
<!-- 忘记密码 STA -->
<div id="modal4" class="modal">
	<div class="login">
		<div class="forget-t">找回密码</div>
		<div class="loginCont loginTag">
			<ul class="login-b">
				<li><input type="text" class="loginTxt" value=""
					placeholder="您注册时的电话号码" id="mobile1" name="mobile1" tabindex="7" />
					<label id="mobile1_succeed" class="blank"></label> <label
					id="mobile1_error" style="color: red;"></label></li>
				<li><input type="text" id="authcode1" name="authcode1"
					class="loginTxt2" value="" placeholder="输入验证码"
					 tabindex="8" />
					<input type="button" class="loginBtn3" value="获取验证码"
					id="getvalidcode" /> <label id="authcode1_succeed"
					class="blank invisible"></label> <label id="authcode1_error"
					style="color: red;"></label></li>
				<li><input type="password" id="pwd4" name="pwd4"
					class="loginTxt" value="" placeholder="输入新密码" tabindex="9" /> <label
					id="pwd4_succeed" class="blank"></label> <label id="pwd4_error"
					style="color: red;"></label></li>
				<li><input type="password" id="pwd5" name="pwd5"
					class="loginTxt" value="" placeholder="再次输入新密码" tabindex="10" /> <label
					id="pwd5_succeed" class="blank"></label> <label id="pwd5_error"
					style="color: red;"></label></li>
				<li><input type="button" class="loginBtn1" id="rest_pwd"
					value="重置密码" /></li>
				<li><div class="login-br" style="float: left;">
						<a class="modalLink" href="#modal1">又想起密码了</a>
					</div></li>
			</ul>
		</div>
	</div>
</div>
<!-- 忘记密码 End -->
<!-- 
<script type="text/javascript"
	src="${context }/resources/rabbit/js/Validate.js"></script>
<script type="text/javascript"
	src="${context }/resources/rabbit/js/Validate.form.js"></script>
 -->