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
                        	冠行是北京青年旅行社股份有限公司国际一部的海外自驾游品牌，我们出行的方式是私家小团或订制小团的自驾游。<br />
                            行程具有自由、个性、灵活与可定制等特点。<br />
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
                	<div class="footer-ml"><img src="${context }/resources/rabbit/images/bot-logo.gif" alt="" /></div>
                    <div class="footer-mm">
                    	<ul>
                        	<li class="title">前往美国</li>
                            <li><a href="#">去美国前的准备和注意事项</a></li>
                            <li><a href="#">在美国开车自驾须知</a></li>
                            <li><a href="#">常用车型介绍</a></li>
                            <li><a href="#">你的驾照出国还能用吗？</a></li>
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
                            <li><a href="#">旅游分享</a></li>
                        </ul>
                    </div>
                    <div class="footer-mr">
                    	<img src="${context }/resources/rabbit/images/qr.gif" alt="" />
                        <p>微信号：zwxzjz</p>
                    </div>
                </div>
            	<div class="footer-b">
                	<div class="footer-bt">友情链接：美国房产精英&nbsp;&nbsp;&nbsp;&nbsp;51766</div>
                    <div class="footer-bb">技术支持：北京智网明达信息技术有限公司&copy;2015 京ICP备11001537号-3</div>
                </div>
            </div>
        </div>
    </div>
    <!-- 公用底部 End -->
    
    <!-- 返回顶部 STA -->
    <div id="backToTop">
        <a href="javascript:;" onfocus="this.blur();" class="backToTop_a" title="返回顶部">返回顶部</a>
    </div>
    
    <script type="text/javascript">
    $(document).ready(function(e) {        
        $("#backToTop").live("click", function(){
            var _this = $(this);
            $('html,body').animate({ scrollTop: 0 }, 500 ,function(){
                _this.hide();
            });
        });
    
        $(window).scroll(function(){
            var htmlTop = $(document).scrollTop();
            if( htmlTop > 0){
                $("#backToTop").show();	
            }else{
                $("#backToTop").hide();
            }
        });
    });
    </script>
    <!-- 返回顶部 End -->
    
    <div class="overlay"></div><!-- 公用背景色 -->
	<!-- 搜索弹框 STA -->
    <div id="modal3" style="display:none;" class="modal hide fade">
        <div class="modal-header">
            <button type="button" class="close closeBtn">×</button>
           <h3>目的地城市选择</h3>
        </div>
        <div class="modal-body">
            <ul class="modal-nav nav-tabs" style="border-bottom:0px;">
                <li><label class="checkbox"><input name="sel_city" value="迈阿密" type="checkbox">迈阿密</label></li>
                <li><label class="checkbox"><input name="sel_city" value="奥兰多" type="checkbox">奥兰多</label></li>
                <li><label class="checkbox"><input name="sel_city" value="纽约" type="checkbox">纽约</label></li>
                <li><label class="checkbox"><input name="sel_city" value="华盛顿" type="checkbox">华盛顿</label></li>
                <li><label class="checkbox"><input name="sel_city" value="基维斯特" type="checkbox">基维斯特</label></li>
                <li><label class="checkbox"><input name="sel_city" value="芝加哥" type="checkbox">芝加哥</label></li>
                <li><label class="checkbox"><input name="sel_city" value="旧金山" type="checkbox">旧金山</label></li>
                <li><label class="checkbox"><input name="sel_city" value="蒙特利" type="checkbox">蒙特利</label></li>
                <li><label class="checkbox"><input name="sel_city" value="文图拉" type="checkbox">文图拉</label></li>
                <li><label class="checkbox"><input name="sel_city" value="拉斯维加斯" type="checkbox">拉斯维加斯</label></li>
                <li><label class="checkbox"><input name="sel_city" value="洛杉矶" type="checkbox">洛杉矶</label></li>
                <li><label class="checkbox"><input name="sel_city" value="费城" type="checkbox">费城</label></li>
                <li><label class="checkbox"><input name="sel_city" value="盐湖城" type="checkbox">盐湖城</label></li>
                <li><label class="checkbox"><input name="sel_city" value="檀香山" type="checkbox">檀香山</label></li>
                <li><label class="checkbox"><input name="sel_city" value="夏威夷" type="checkbox">夏威夷</label></li>
                <li><label class="checkbox"><input name="sel_city" value="摩押" type="checkbox">摩押</label></li>
                <li><label class="checkbox"><input name="sel_city" value="佩吉" type="checkbox">佩吉</label></li>
                <li><label class="checkbox"><input name="sel_city" value="66号公路" type="checkbox">66号公路</label></li>
                <li><label class="checkbox"><input name="sel_city" value="加州1号公路" type="checkbox">加州1号公路</label></li>
                <li><label class="checkbox"><input name="sel_city" value="17英里海岸" type="checkbox">17英里海岸</label></li>
            </ul>
        </div>
        <div class="modal-footer">
            <a href="#" onclick="javascript:;" class="btn closeBtn">关闭</a> <a href="#" class="btn btn-primary">确认</a>
        </div>
    </div>
    <!-- 搜索弹框 End -->
    
    <!-- 登录弹框 STA -->
    <div id="modal1" class="modal">
        <div class="login">
        	<ul class="login-t">
				<li class="loginTag"><a class="modalLink" href="#modal1">登录</a></li>
				<li><a class="modalLink" href="#modal2">注册</a></li>
			</ul>
			<div class="loginCont loginTag">
				<ul class="login-b">
                    <li>
                     	<input type="text" class="loginTxt" value="登录名" onfocus="if(this.value=='登录名'){this.value=''}" onblur="if(this.value==''){this.value='登录名'}" id="username" name="username" tabindex="1" />
                        <label id="username_succeed" class="blank"></label>
                        <div id="username_error"></div>
                    </li>
                  	<li>
                      	<input type="text" class="loginTxt" value="密码" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}" id="pwd" name="pwd" tabindex="2" />
                        <label id="pwd_succeed" class="blank"></label>
                        <label id="pwd_error"></label>
                    </li>
                    <li>
                      	<div class="login-bl"><input type="checkbox" checked /> 记住密码</div>
                        <div class="login-br"><a class="modalLink" href="#modal4">忘记密码？</a></div>
                    </li>
                    <li><input type="button" class="loginBtn1" value="登录" /></li>
                    <li><input type="button" class="loginBtn2" value="注册" /></li>
                </ul>
			</div>
        </div>
    </div>
    <!-- 登录弹框 End -->
    
    <div id="modal2" class="modal">
        <div class="login">
        	<ul class="login-t">
				<li><a class="modalLink" href="#modal1">登录</a></li>
				<li class="loginTag"><a class="modalLink" href="#modal2">注册</a></li>
			</ul>
			<div class="loginCont loginTag">
				<ul class="login-b">
                   	<li>
                       	<input type="text" class="loginTxt" value="登录账户" onfocus="if(this.value=='登录账户'){this.value=''}" onblur="if(this.value==''){this.value='登录名'}" id="realname" name="realname" tabindex="3" />
                        <label id="realname_succeed" class="blank"></label>
                        <label id="realname_error"></label>
                    </li>
                    <li>
                      	<input type="text" id="pwd2" name="pwd2" class="loginTxt" value="登录密码" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}" tabindex="4"/>
                        <label id="pwd2_succeed" class="blank"></label>
                        <label id="pwd2_error"></label>
                    </li>
                    <li>
                     	<input type="text" id="pwd3" name="pwd3" class="loginTxt" value="确认密码" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}" tabindex="5"/>
                        <label id="pwd3_succeed" class="blank"></label>
                        <label id="pwd3_error"></label>
                    </li>
                    <li>
                       	<input type="text" id="mobile" name="mobile" class="loginTxt2" value="有效手机号" onfocus="if(this.value=='有效手机号'){this.value=''}" onblur="if(this.value==''){this.value='有效手机号'}" tabindex="6" />
                       	<input type="button" class="loginBtn3" value="获取验证码" />
                        <label id="mobile_succeed" class="blank"></label>
                        <label id="mobile_error"></label>
                    </li>
                    <li>
                      	<input type="text" id="authcode" name="authcode" class="loginTxt" value="手机验证码" onfocus="if(this.value=='手机验证码'){this.value=''}" onblur="if(this.value==''){this.value='手机验证码'}" />
                        <label id="authcode_succeed" class="blank invisible"></label>
                        <label id="authcode_error"></label>
                    </li>
                    <li class="btny">
                      	<input type="button" class="loginBtn1" value="注册" />
                      	<input type="button" class="loginBtn2" value="登录" />
                    </li>
                </ul>
			</div>
        </div>
    </div>
    
    <!-- 忘记密码 STA -->
    <div id="modal4" class="modal">
        <div class="login">
        	<div class="forget-t">找回密码</div>
			<div class="loginCont loginTag">
				<ul class="login-b">
                   	<li>
                       	<input type="text" class="loginTxt" value="您注册时的电话号码" onfocus="if(this.value=='您注册时的电话号码'){this.value=''}" onblur="if(this.value==''){this.value='您注册时的电话号码'}" id="mobile1" name="mobile1" tabindex="7" />
                        <label id="mobile1_succeed" class="blank"></label>
                        <label id="mobile1_error"></label>
                    </li>
                    <li>
                      	<input type="text" id="authcode1" name="authcode1" class="loginTxt2" value="输入验证码" onfocus="if(this.value=='输入验证码'){this.value=''}" onblur="if(this.value==''){this.value='输入验证码'}" tabindex="8" />
                        <input type="button" class="loginBtn3" value="获取验证码" />
                        <label id="authcode1_succeed" class="blank invisible"></label>
                        <label id="authcode1_error"></label>
                    </li>
                    <li>
                     	<input type="text" id="pwd4" name="pwd4" class="loginTxt" value="输入新密码" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}" tabindex="9"/>
                        <label id="pwd4_succeed" class="blank"></label>
                        <label id="pwd4_error"></label>
                    </li>
                    <li>
                     	<input type="text" id="pwd5" name="pwd5" class="loginTxt" value="再次输入新密码" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}" tabindex="10"/>
                        <label id="pwd5_succeed" class="blank"></label>
                        <label id="pwd5_error"></label>
                    </li>
                    <li><input type="button" class="loginBtn1" value="确认" /></li>
                    <li><div class="login-br" style="float:left;"><a class="modalLink" href="#modal1">又想起密码了</a></div></li>
                </ul>
			</div>
        </div>
    </div>
    <!-- 忘记密码 End -->
    <script type="text/javascript" src="${context }/resources/rabbit/js/Validate.js"></script>
	<script type="text/javascript" src="${context }/resources/rabbit/js/Validate.form.js"></script>