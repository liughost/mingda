<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js"
	type="text/javascript" charset="utf-8"></script>
<style>
.muted {
	color: gray;
	font-size: 16px;
	font-weight: normal;
}
</style>
<div class="container" id="region">
	<c:choose>
		<c:when test="${not empty msg}">
			<span class="label label-important">提示：</span>${msg}
					</c:when>
		<c:otherwise>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td><h3>${title}</h3></td>
				</tr>
				<tr>
					<td style="height: 50px;">${makeTime
						}&nbsp;&nbsp;${writer}&nbsp;&nbsp; <!-- 
						<a
						href="weixin://contacts/profile/${weixinId}">${weixinName}</a>  
						--> <i style="position: absolute; padding-left: 10px;"> <wb:share-button
								appkey="6EvTuZ" addition="simple" type="button"></wb:share-button>
					</i>
					</td>
				</tr>

				<c:forEach var="item" items="${content_list}" varStatus="state">
					<tr id="special_${item.hashCode}">
						<td id="td_special_${item.hashCode}"
							style="font-size: 18px;text-align:${align};width:100%;line-height: 24px;">
							<h3>
								${item.title}&nbsp;<span class="muted">${item.subhead }</span>
							</h3> <c:choose>
								<c:when test="${not empty item.smart }">
									<img src="${item.smart}" style="max-width: 100%;" alt="网客来" />
									<br />
								</c:when>
							</c:choose>
							<h4>${item.subhead}</h4>${item.smart_text}
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td>
						<%
							String openid = request.getParameter("openid");
									request.setAttribute("openid", "");
						%> <c:choose>
							<c:when test="${empty openid}">
								<td style="height: 60px;"></td>
							</c:when>
							<c:otherwise>
								<td><img src="${context}/resources/image/client/finger.gif"
									style="" alt="想走就走" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>

			</table>
		</c:otherwise>
	</c:choose>
	<div style="text-align: center;">
		<c:if test="${not empty prevSpecial }">
			<p class="text-left">
				上一篇：<a href="${context }/show/spec?id=${prevSpecial.id }">${prevSpecial.title
					}</a>
			</p>
		</c:if>
		<c:if test="${not empty nextSpecial }">
			<p class="text-left">
				下一篇：<a href="${context }/show/spec?id=${nextSpecial.id }">${nextSpecial.title
					}</a>
			</p>
		</c:if>
		<!-- 
		<img src="${context }/resources/image/client/join_me.gif" /><br /> <img
			src="${context }/resources/image/client/xzjz_dingyue.jpg"
			style="width: 350px;" />
			 -->
	</div>
</div>
<input type="hidden" id="preSpec" value="${prevSpecial.id }" />
<input type="hidden" id="nextSpec" value="${nextSpecial.id }" />

<input type="hidden" id="touchStart" style="font-size: 48px;" />
<br />
<input type="hidden" id="touchMove" style="font-size: 48px;" />

<div class="container">
	<div class="mash_shape text-center" id="mash"
		style="text-align: center; color: white; display: none">
		<img src="${context }/resources/image/loading.jpg"
			style="padding-top: 100px;" /><br /> 加载文章中...
	</div>
	<div class="bottom_shape" style="display:none;">
		<table style="width: 100%; margin-top: 6px;">
			<tr>
				<td style="text-align: center"><img id="slide_img"
					src="${context }/resources/image/slide.jpg" style="width: 100%"></td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	/*
	 * 作者：程序员老刘
	 * 功能：计算手指在指定标签上的滑动的方向
	 */

	var pressX = 0, pressY = 0;
	function loadSlide(tagId) {
		var obj = document.getElementById(tagId);
		obj.addEventListener('touchmove', function(event) {
			// 如果这个元素的位置内只有一个手指的话
			if (event.targetTouches.length == 1) {
				var touch = event.targetTouches[0];
				var spanX = touch.pageX - pressX;
				var spanY = touch.pageY - pressY;
				var direct = "none";
				var sens = 120;
				if (Math.abs(spanX) > Math.abs(spanY)
						&& (Math.abs(spanX) > sens || Math.abs(spanY) > sens)) {
					//水平方向
					if (spanX > 0) {
						direct = "right";//向右
						//do right function
						if (nextSpec.value.length > 0) {
							mash.style.display = "";
							//location.href = "${context }/show/spec?id="
							//		+ nextSpec.value;
							//看上去好像有病，为什么不直接跳转？非要调个函数还要启动定时器，延时1500毫秒，
							//因为在微信的浏览器里location.href方法会让前面的style.display=""失效，
							slideUrl = "${context }/show/spec?id="
									+ nextSpec.value;
							gotoUrl();
						} else
							alert("对不起，已经是最后一篇了。");
					} else {
						direct = "left";//向左
						//do left function
						if (preSpec.value.length > 0) {
							mash.style.display = "";
							//location.href = "${context }/show/spec?id="
							//		+ preSpec.value;
							slideUrl = "${context }/show/spec?id="
									+ preSpec.value;
							gotoUrl();
						} else
							alert("对不起，已经没有文章了。");
					}
				} else {
					//垂直方向
					if (spanY > 0) {
						direct = "down";//向下
						//do down function
					} else {
						direct = "up";//向上
						//do up function
					}
				}
				// 把元素放在手指所在的位置
				touchMove.value = direct + "(" + spanX + ';' + spanY + ")";
			}
		}, false);

		obj.addEventListener('touchstart', function(event) {
			// 如果这个元素的位置内只有一个手指的话
			if (event.targetTouches.length == 1) {
				var touch = event.targetTouches[0];
				// 把元素放在手指所在的位置
				pressX = touch.pageX;
				pressY = touch.pageY;

				touchStart.value = pressX + ';' + pressY;
			}
		}, false);

		/*
		obj.addEventListener('touchend', function(event) {
		    // 如果这个元素的位置内只有一个手指的话
		    if (event.targetTouches.length == 1) {
		        var touch = event.targetTouches[0];
		        // 把元素放在手指所在的位置
		        touchEnd.value=touch.pageX + ';' + touch.pageY;
		    }
		}, false);
		 */
	}

	var slideUrl;
	function gotoUrl() {
		setInterval(function() {

			location.href = slideUrl;

		}, 1500);
	}

	//加载滑动事件
	window.onload = function() {

		readyTimer = setInterval(function() {
			if (document.readyState == "complete") {
				clearInterval(readyTimer);
				loadSlide('region');
				loadSlide('slide_img');
				loadSlide('footer');
			}
		}, 500);
	};
</script>

