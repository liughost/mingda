<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="username" property="principal.username" />
</sec:authorize>
<!-- 公用头部 STA -->
<div class="headerk">
	<div class="header-t">
		<div class="header">
			<!--登录前STA-->
			<c:if test="${empty username}">
				<div class="header-go">
					<a class="modalLink" href="#modal1">登录</a><span>|</span><a
						class="modalLink" href="#modal2">注册</a>
				</div>
			</c:if>
			<!--登录前End-->

			<!--登录后STA-->
			<c:if test="${not empty username}">
				<div class="header-back">
					<img src="${context }/resources/rabbit/images/head22-22.gif" alt="" />
					<a href="javascript:;">${username }</a>
					<div class="back-menu">
						<div class="top-menuk">
							<div class="top-menu">
								<div class="back-arw"></div>
								<ul class="subnav">
									<li><a href="#">我的信息</a></li>
									<li><a href="#">我的邀请</a></li>
									<li><a href="#">我的订单</a></li>
									<li><a href="#">我的旅伴</a></li>
									<li><a href="#">修改密码</a></li>
									<li><a href="javascript:;">退出</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<!--登录后End-->
		</div>
	</div>
	<div class="header-b">
		<div class="nav">
			<div class="navlist" id="navlist">
				<div class="logo">
					<a href="${context }/custom/book"><img
						src="${context }/resources/rabbit/images/logo.png" alt="" /></a>
				</div>
				<ul id="navfouce">
					<li><a href="${context }/custom/tour/search" class="boxlist"><span
							class="nav1"></span>包车</a></li>
					<li><a href="${context }/custom/product/search"
						class="boxlist"><span class="nav2"></span>线路</a></li>

					<li style="display: none;"><a href="#" class="boxlist"><span
							class="nav3"></span>结伴同行</a></li>

					<li><a href="${context }/custom/articles" class="boxlist"><span
							class="nav4"></span>旅行分享</a></li>
				</ul>
			</div>
			<div class="box" id="navbox"
				style="height: 0; opacity: 0; overflow: hidden;">
				<div class="cont" style="display: none;">
					<div class="sublist clearfix">
						<div class="navsub">
							<div class="nav-car">
								<div class="navcar1"></div>
								<div class="nav-car-r">
									<div class="nav-cart">北美洲</div>
									<div class="nav-carb">
										<ul id="tags" class="tags">
											<li class="selectTag"><a
												onClick="selectTag('tagContent0',this)" href="javascript:;">美国</a></li>
											<!-- 
											<li><a onClick="selectTag('tagContent1',this)"
												href="javascript:;">墨西哥</a></li>
											<li><a onClick="selectTag('tagContent2',this)"
												href="javascript:;">加拿大</a></li>
												 -->
										</ul>
										<div id="tagContent">
											<div id="tagContent0" class="tagContent selectTag">
												<a href="${context}/custom/tour/search?city=拉斯维加斯">拉斯维加斯</a>
												<a href="${context}/custom/tour/search?city=洛杉矶">洛杉矶</a> <a
													href="${context}/custom/tour/search?city=旧金山">旧金山</a> <a
													href="${context}/custom/tour/search?city=弗雷斯诺">弗雷斯诺</a> <a
													href="${context}/custom/tour/search?city=佩吉">佩吉</a> <a
													href="${context}/custom/tour/search?city=夏威夷">夏威夷</a> <a
													href="${context}/custom/tour/search?city=奥兰多">奥兰多</a> <a
													href="${context}/custom/tour/search?city=盐湖城">盐湖城</a> <a
													href="${context}/custom/tour/search?city=蒙特利">蒙特利</a> <a
													href="${context}/custom/tour/search?city=迈阿密">迈阿密</a> <a
													href="${context}/custom/tour/search?city=芝加哥">芝加哥</a>
												<!-- 
												<a href="${context}/custom/tour/search?city=?city=丹麦小镇">丹麦小镇</a>
												<a href="${context}/custom/tour/search?city=佩吉">佩吉</a> <a
													href="${context}/custom/tour/search?city=华盛顿">华盛顿</a> <a
													href="${context}/custom/tour/search?city=卡梅尔小镇">卡梅尔小镇</a> <a
													href="${context}/custom/tour/search?city=圣路易斯">圣路易斯</a> <a
													href="${context}/custom/tour/search?city=埃尔克城">埃尔克城</a> <a
													href="${context}/custom/tour/search?city=基韦斯特">基韦斯特</a> <a
													href="${context}/custom/tour/search?city=塔尔萨">塔尔萨</a> <a
													href="${context}/custom/tour/search?city=夏威夷">夏威夷</a> <a
													href="${context}/custom/tour/search?city=奥兰多">奥兰多</a> <a
													href="${context}/custom/tour/search?city=威廉姆斯">威廉姆斯</a> <a
													href="${context}/custom/tour/search?city=布莱斯">布莱斯</a> <a
													href="${context}/custom/tour/search?city=弗雷斯诺">弗雷斯诺</a> <a
													href="${context}/custom/tour/search?city=拉斯维加斯">拉斯维加斯</a> <a
													href="${context}/custom/tour/search?city=摩押">摩押</a> <a
													href="${context}/custom/tour/search?city=文图拉">文图拉</a> <a
													href="${context}/custom/tour/search?city=旧金山">旧金山</a> <a
													href="${context}/custom/tour/search?city=檀香山">檀香山</a> <a
													href="${context}/custom/tour/search?city=洛杉矶">洛杉矶</a> <a
													href="${context}/custom/tour/search?city=盐湖城">盐湖城</a> <a
													href="${context}/custom/tour/search?city=科特斯">科特斯</a> <a
													href="${context}/custom/tour/search?city=纽约">纽约</a> <a
													href="${context}/custom/tour/search?city=罗德岱堡">罗德岱堡</a> <a
													href="${context}/custom/tour/search?city=芝加哥">芝加哥</a> <a
													href="${context}/custom/tour/search?city=蒙特利">蒙特利</a> <a
													href="${context}/custom/tour/search?city=迈阿密">迈阿密</a> <a
													href="${context}/custom/tour/search?city=都柏林">都柏林</a> <a
													href="${context}/custom/tour/search?city=隆波克">隆波克</a> <a
													href="${context}/custom/tour/search?city=雷顿">雷顿</a>
													 -->
											</div>
											<div id="tagContent1" class="tagContent">
												<a href="#">摩押</a><a href="#">科特斯</a><a href="#">佩吉</a>
											</div>
											<div id="tagContent2" class="tagContent">
												<a href="#">拉斯维加斯</a><a href="#">布莱斯</a><a href="#">洛杉矶</a><a
													href="#">摩押</a><a href="#">佩吉</a><a href="#">科特斯</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="nav-car" style="display: none;">
								<div class="navcar2"></div>
								<div class="nav-car-r">
									<div class="nav-cart">大洋洲</div>
									<div class="nav-carb">
										<ul class="tags">
											<li><a href="#">澳大利亚</a></li>
											<li><a href="#">新西兰</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="cont" style="display: none;">
					<div class="sublist clearfix">
						<div class="navsub">
							<div class="nav-car">
								<div class="navcar1"></div>
								<div class="nav-car-r">
									<div class="nav-cart">北美洲</div>
									<div class="nav-carb">
										<ul id="tags" class="tags">
											<li class="selectTag"><a
												onClick="selectTag('tagContent0',this)" href="javascript:;">美国</a></li>
											<!-- 
											<li><a onClick="selectTag('tagContent1',this)"
												href="javascript:;">墨西哥</a></li>
											<li><a onClick="selectTag('tagContent2',this)"
												href="javascript:;">加拿大</a></li>
												 -->
										</ul>
										<div id="tagContent">
											<div id="tagContent0" class="tagContent selectTag">
												<a href="${context}/custom/product/search?city=华盛顿">华盛顿</a>
												<a href="${context}/custom/product/search?city=圣路易斯">圣路易斯</a>
												<a href="${context}/custom/product/search?city=基韦斯特">基韦斯特</a>
												<a href="${context}/custom/product/search?city=奥兰多">奥兰多</a>
												<a href="${context}/custom/product/search?city=威廉姆斯">威廉姆斯</a>
												<a href="${context}/custom/product/search?city=拉斯维加斯">拉斯维加斯</a>
												<a href="${context}/custom/product/search?city=文图拉">文图拉</a>
												<a href="${context}/custom/product/search?city=旧金山">旧金山</a>
												<a href="${context}/custom/product/search?city=檀香山">檀香山</a>
												<a href="${context}/custom/product/search?city=洛杉矶">洛杉矶</a>
												<a href="${context}/custom/product/search?city=盐湖城">盐湖城</a>
												<a href="${context}/custom/product/search?city=科特斯">科特斯</a>
												<a href="${context}/custom/product/search?city=纽约">纽约</a> <a
													href="${context}/custom/product/search?city=罗德岱堡">罗德岱堡</a>
												<a href="${context}/custom/product/search?city=芝加哥">芝加哥</a>
												<a href="${context}/custom/product/search?city=蒙特利">蒙特利</a>
												<a href="${context}/custom/product/search?city=迈阿密">迈阿密</a>
												<a href="${context}/custom/product/search?city=都柏林">都柏林</a>
												
											</div>
											<div id="tagContent1" class="tagContent">
												<a href="#">摩押</a><a href="#">科特斯</a><a href="#">佩吉</a>
											</div>
											<div id="tagContent2" class="tagContent">
												<a href="#">拉斯维加斯</a><a href="#">布莱斯</a><a href="#">洛杉矶</a><a
													href="#">摩押</a><a href="#">佩吉</a><a href="#">科特斯</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="nav-car" style="display: none;">
								<div class="navcar2"></div>
								<div class="nav-car-r">
									<div class="nav-cart">大洋洲</div>
									<div class="nav-carb">
										<ul class="tags">
											<li><a href="#">澳大利亚</a></li>
											<li><a href="#">新西兰</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="cont" style="display: none;">
					<div class="sublist clearfix">
						<div class="navsub">
							<div class="nav-car">
								<div class="navcar3"></div>
								<div class="nav-car-r">
									<div class="nav-cart">找旅伴</div>
									<div class="nav-carb">
										<div class="nav-carbt">我已经有初步的旅行计划,需要找AA制同伴,我们一起旅行</div>
										<div class="nav-carbb">
											<a href="${context }/partner/publish">发布找旅伴</a>
										</div>
									</div>
								</div>
							</div>
							<div class="nav-car">
								<div class="navcar4"></div>
								<div class="nav-car-r">
									<div class="nav-cart">自由结伴</div>
									<div class="nav-carb">
										<div class="nav-carbt">自由的拼车、拼导游、找资深玩家、帅哥靓女和你一起玩</div>
										<div class="nav-carbb">
											<a href="${context }/partner/search">自由结伴</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="cont" style="display: none; height: 0;"></div>
				<div class="cont" style="display: none; height: 0;"></div>
			</div>
			<script src="${context }/resources/rabbit/js/nav.js"
				type="text/javascript"></script>
		</div>
	</div>
</div>
<div class="topbg"></div>
<!-- 公用头部 End -->