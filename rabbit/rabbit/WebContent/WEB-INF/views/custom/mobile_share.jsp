<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="height: 00px;"></div>
<style type="text/css">
/* Featurettes
    ------------------------- */
.featurette-divider {
	margin: 10px 0; /* Space out the Bootstrap <hr> more */
}

.featurette { //
	padding-top: 120px;
	/* Vertically center images part 1: add padding above and below text. */
	overflow: hidden;
	/* Vertically center images part 2: clear their floats. */
}

.featurette-image {
	margin-top: -10px;
	/* Vertically center images part 3: negative margin up the image the same amount of the padding to center it. */
}

/* Give some space on the sides of the floated elements so text doesn't run right into it. */
.featurette-image.pull-left {
	margin-right: 40px;
}

.featurette-image.pull-right {
	margin-left: 40px;
}

/* Thin out the marketing headings */
.featurette-heading {
	font-size: 30px;
	font-weight: 300;
	line-height: 1;
	letter-spacing: -1px;
}

.lead {
	font-size: 16px;
}

.intro_text {
	
}

.tb_title {
	color: black;
	font-size: 20px;
}

.tb_date {
	color: gray;
	font-size: 12px;
}
</style>
<div class="container" style="max-widht:900px;">

	<!-- START THE FEATURETTES -->
	<h3 id="title">领队动态</h3>
	<!-- 
	<input id="touchMove" value="" /> <input id="touchStart" value="" />
 -->
	<c:choose>
		<c:when test="${IsMobile==true }">
			<s:url var="articlePath" value="/show/spec?id="></s:url>
		</c:when>
		<c:otherwise>
			<s:url var="articlePath" value="/custom/detail?id="></s:url>
		</c:otherwise>
	</c:choose>
	<table class="table">
		<c:forEach var="items" items="${msgList}" varStatus="status">

			<tr>
				<td style="text-align: left;">
					<div style="min-height: 20px;word-break:break-all;">
						<c:choose>
							<c:when test="${items.msgType== 'image'}">
								<img src="${items.content }" style="max-width: 290px;">
							</c:when>
							<c:otherwise>
								<span class="tb_title">${items.content }</span>
							</c:otherwise>
						</c:choose>
					</div>
					<div>
						<span class="tb_date"><a
					href="${context }/custom/share?custId=${items.customer.id}">
						${items.customer.nickName } </a>发表&nbsp;日期：${items.createTime}</span>
					</div></td>
			</tr>
			<c:set var="cust_id" value="${items.customer.id}" />
		</c:forEach>
	</table>
	<div class="pagination">
		<ul>
			<c:if test="${prevPage>=0}">
				<li><a
					href="${context}/custom/share?custId=${custId }&page=${prevPage}">上一页</a></li>
			</c:if>
			<c:forEach var="i" begin="0" end="${pageNo}" step="1">
				<li><a
					href="${context}/custom/share?custId=${custId }&page=${i}">第${i+1}页</a></li>
			</c:forEach>
			<c:if test="${nextPage>0}">
				<li><a
					href="${context}/custom/share?custId=${custId }&page=${nextPage}">下一页</a></li>
			</c:if>
		</ul>
	</div>
	<!-- /END THE FEATURETTES -->
</div>