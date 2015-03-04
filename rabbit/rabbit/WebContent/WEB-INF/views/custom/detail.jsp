<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js"
	type="text/javascript" charset="utf-8"></script>
<!-- 主体内容区 STA -->
<div class="maink">
	<div class="main">
		<div class="t-det">
			<div class="t-detl">
				<div class="t-detl-t">${mainTitle }</div>
				<div class="t-detl-s">
					${makeTime}&nbsp;&nbsp;<wb:share-button
						appkey="6EvTuZ" addition="simple" type="button"></wb:share-button>
				</div>
				<div class="t-detl-m">
					<c:forEach var="items" items="${detailList}" varStatus="status">
						<h3>
							${items.title } <span class="muted">${items.subhead }</span>
						</h3>
						<c:if test="${fn:length(items.smart)>0}">
							<p style="text-indent: 25px;">
								<img style="max-width:786px;" src="${items.smart}" alt="" />
							</p>
						</c:if>
						<p>${items.smart_text}</p>
					</c:forEach>
				</div>
				<div class="t-detl-b">
					<c:if test="${not empty prevSpecial }">
					上一篇：<a href="${context }/custom/detail?id=${prevSpecial.id }">${prevSpecial.title}</a>
						<br />
					</c:if>
					<c:if test="${not empty nextSpecial }">
					 下一篇：<a href="${context }/custom/detail?id=${nextSpecial.id }">${nextSpecial.title}</a>
					</c:if>
				</div>
			</div>
			<div class="t-detr">
				<div class="t-detr-t">最新动态</div>
				<div class="t-detr-b">
					<ul>
						<c:forEach var="items" items="${articleList}">
							<li>
								<div class="t-detr-bl">
									<a href="${context }/custom/detail?id=${items.id }"><img
										src="${items.img }" alt="" style="width: 98px; height: 98px;" /></a>
								</div>
								<div class="t-detr-br">
									<div class="t-detr-brt">
										<a href="${context }/custom/detail?id=${items.id }">${items.title }</a>
									</div>
									<div class="t-detr-brb">${items.specialMaketime}</div>
								</div>
							</li>
						</c:forEach>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 主体内容区 End -->