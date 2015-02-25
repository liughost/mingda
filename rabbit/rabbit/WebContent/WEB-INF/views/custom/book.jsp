<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- banner及搜索 STA -->
<div class="bannersy">
	<ul class="bannersy-bg">
		<li><a href="#"><img
				src="${context }/resources/rabbit/images/banner-sy1.jpg" alt="" /></a></li>
		<li><a href="#"><img
				src="${context }/resources/rabbit/images/banner-sy2.jpg" alt="" /></a></li>
	</ul>
	<div class="banner-btn">
		<input class="bnr-txt modalLink" name="city" href="#modal3"
			placeholder="城市/景点" type="text" /> <input type="button"
			class="bnr-btn" value="搜索" title="搜索" />
	</div>
</div>
<!-- banner及搜索 End -->
<!-- 主体内容区 STA -->
<div class="mainsyk-t">
	<div class="main">
		<div class="m-car-t">
			<div class="l">
				<a href="${context }/custom/tour/search">专车&导游</a>
			</div>
		</div>
		<div class="m-car-b">
			<ul>
				<li>
					<div class="m-car">
						<div class="m-car-img">
							<a href="#"><img
								src="${context }/resources/rabbit/images/photo-sy1.jpg" alt="" /></a>
							<div class="icon">
								<div class="icon-dy">导游</div>
								<div class="icon-zc">专车</div>
							</div>
						</div>
						<div class="m-car-cont">
							<div class="l">
								<img src="${context }/resources/rabbit/images/head56-56.gif"
									alt="" />
							</div>
							<div class="r">
								<div class="title">
									<a href="#">Damon Salvatore</a>
								</div>
								<div class="cont">你好，歡迎來到美国.我是美国私人导游damon，可为您提供美国地陪，包车旅游等服务。根据您的...</div>
							</div>
						</div>
						<div class="m-car-bot">
							<div class="l">
								服务<span class="star"></span><span class="star"></span><span
									class="star"></span><span class="star"></span><span
									class="star"></span>
							</div>
							<div class="r">
								<em>￥680</em>/小时
							</div>
						</div>
					</div>
				</li>
				<li>
					<div class="m-car">
						<div class="m-car-img">
							<a href="#"><img
								src="${context }/resources/rabbit/images/photo-sy2.jpg" alt="" /></a>
							<div class="icon">
								<div class="icon-dy">导游</div>
								<div class="icon-zc">专车</div>
							</div>
						</div>
						<div class="m-car-cont">
							<div class="l">
								<img src="${context }/resources/rabbit/images/head56-56.gif"
									alt="" />
							</div>
							<div class="r">
								<div class="title">
									<a href="#">Damon Salvatore</a>
								</div>
								<div class="cont">你好，歡迎來到美国.我是美国私人导游damon，可为您提供美国地陪，包车旅游等服务。根据您的...</div>
							</div>
						</div>
						<div class="m-car-bot">
							<div class="l">
								服务<span class="star"></span><span class="star"></span><span
									class="star"></span><span class="star"></span><span
									class="star"></span>
							</div>
							<div class="r">￥680/小时</div>
						</div>
					</div>
				</li>
				<li>
					<div class="m-car">
						<div class="m-car-img">
							<a href="#"><img
								src="${context }/resources/rabbit/images/photo-sy3.jpg" alt="" /></a>
							<div class="icon">
								<div class="icon-dy">导游</div>
							</div>
						</div>
						<div class="m-car-cont">
							<div class="l">
								<img src="${context }/resources/rabbit/images/head56-56.gif"
									alt="" />
							</div>
							<div class="r">
								<div class="title">
									<a href="#">Damon Salvatore</a>
								</div>
								<div class="cont">你好，歡迎來到美国.我是美国私人导游damon，可为您提供美国地陪，包车旅游等服务。根据您的...</div>
							</div>
						</div>
						<div class="m-car-bot">
							<div class="l">
								服务<span class="star"></span><span class="star"></span><span
									class="star"></span><span class="star"></span><span
									class="star-no"></span>
							</div>
							<div class="r">￥680/小时</div>
						</div>
					</div>
				</li>
				<li>
					<div class="m-car">
						<div class="m-car-img">
							<a href="#"><img
								src="${context }/resources/rabbit/images/photo-sy4.jpg" alt="" /></a>
							<div class="icon">
								<div class="icon-dy">导游</div>
								<div class="icon-zc">专车</div>
							</div>
						</div>
						<div class="m-car-cont">
							<div class="l">
								<img src="${context }/resources/rabbit/images/head56-56.gif"
									alt="" />
							</div>
							<div class="r">
								<div class="title">
									<a href="#">Damon Salvatore</a>
								</div>
								<div class="cont">你好，歡迎來到美国.我是美国私人导游damon，可为您提供美国地陪，包车旅游等服务。根据您的...</div>
							</div>
						</div>
						<div class="m-car-bot">
							<div class="l">
								服务<span class="star"></span><span class="star"></span><span
									class="star"></span><span class="star"></span><span
									class="star"></span>
							</div>
							<div class="r">￥680/小时</div>
						</div>
					</div>
				</li>
				<li>
					<div class="m-car">
						<div class="m-car-img">
							<a href="#"><img
								src="${context }/resources/rabbit/images/photo-sy5.jpg" alt="" /></a>
							<div class="icon">
								<div class="icon-dy">导游</div>
							</div>
						</div>
						<div class="m-car-cont">
							<div class="l">
								<img src="${context }/resources/rabbit/images/head56-56.gif"
									alt="" />
							</div>
							<div class="r">
								<div class="title">
									<a href="#">Damon Salvatore</a>
								</div>
								<div class="cont">你好，歡迎來到美国.我是美国私人导游damon，可为您提供美国地陪，包车旅游等服务。根据您的...</div>
							</div>
						</div>
						<div class="m-car-bot">
							<div class="l">
								服务<span class="star"></span><span class="star"></span><span
									class="star"></span><span class="star"></span><span
									class="star-no"></span>
							</div>
							<div class="r">￥680/小时</div>
						</div>
					</div>
				</li>
				<li>
					<div class="m-car">
						<div class="m-car-img">
							<a href="#"><img
								src="${context }/resources/rabbit/images/photo-sy6.jpg" alt="" /></a>
							<div class="icon">
								<div class="icon-dy">导游</div>
								<div class="icon-zc">专车</div>
							</div>
						</div>
						<div class="m-car-cont">
							<div class="l">
								<img src="${context }/resources/rabbit/images/head56-56.gif"
									alt="" />
							</div>
							<div class="r">
								<div class="title">
									<a href="#">Damon Salvatore</a>
								</div>
								<div class="cont">你好，歡迎來到美国.我是美国私人导游damon，可为您提供美国地陪，包车旅游等服务。根据您的...</div>
							</div>
						</div>
						<div class="m-car-bot">
							<div class="l">
								服务<span class="star"></span><span class="star"></span><span
									class="star"></span><span class="star"></span><span
									class="star"></span>
							</div>
							<div class="r">￥680/小时</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
<div class="mainsyk-b">
	<div class="main">
		<div class="m-car-t">
			<div class="l">
				<a href="${context }/custom/product/search">精彩线路</a>
			</div>
		</div>
		<div class="m-fine">
			<ul>
				<li>
					<div class="m-fine-img"
						onclick="javascript:location.href='${context }/custom/product/search';">
						<img src="${context }/resources/rabbit/images/photo-sy7.jpg"
							alt="" />
						<p>美西大环线</p>
					</div>
				</li>
				<li>
					<div class="m-fine-img"
						onclick="javascript:location.href='${context }/custom/product/search';">
						<img src="${context }/resources/rabbit/images/photo-sy8.jpg"
							alt="" />
						<p>黄石公园</p>
					</div>
				</li>
				<li>
					<div class="m-fine-img"
						onclick="javascript:location.href='${context }/custom/product/search';">
						<img src="${context }/resources/rabbit/images/photo-sy9.jpg"
							alt="" />
						<p>大美加州</p>
					</div>
				</li>
				<li>
					<div class="m-fine-img"
						onclick="javascript:location.href='${context }/custom/product/search';">
						<img src="${context }/resources/rabbit/images/photo-sy10.jpg"
							alt="" />
						<p>66号公路</p>
					</div>
				</li>
				<li>
					<div class="m-fine-img"
						onclick="javascript:location.href='${context }/custom/product/search';">
						<img src="${context }/resources/rabbit/images/photo-sy11.jpg"
							alt="" />
						<p>美国最南端</p>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
<!-- 主体内容区 End -->