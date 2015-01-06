<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<style type="text/css">
body {
	padding-bottom: 40px;
	font-family: "Microsoft YaHei";
	background-color: #FCFDEF;
}
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
	font-size: 16px;
	line-height: 24px;
}

.article_img {
	max-width: 100%;
}

.table td {
	border: 0;
}
</style>

<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js"
	type="text/javascript" charset="utf-8"></script>
<div style="height: 10px;"></div>
<div class="container"
	style="max-width: 960px; border: 1px solid gray; padding-left: 10px;">



	<!-- START THE FEATURETTES -->
	<table>
		<tr>
			<td><h2>${mainTitle }</h2></td>
		</tr>
		<tr>
			<td>${makeTime}&nbsp;<i
				style="position: absolute; padding-left: 10px;"> <wb:share-button
						appkey="6EvTuZ" addition="simple" type="button"></wb:share-button>
			</i>
			</td>
		</tr>
	</table>

	<div class="row-fluid">
		<div class="span8"
			style="padding: 20px 20px 0px 10px; border-right: 1px solid lightgray;">
			<c:forEach var="items" items="${detailList}" varStatus="status">
				<!-- 左右结构注释 -->
				<!-- 
				<div class="featurette">
					<c:if test="${fn:length(items.smart)>0}">
						<img
							class="featurette-image <c:choose><c:when test="${status.count%2==0 || fn:length(items.smart_text)==0 }">pull-left</c:when><c:otherwise>pull-right</c:otherwise></c:choose>"
							style="padding-top: 30px; max-width: 350px;"
							src="${items.smart }">
					</c:if>
					<div class="intro_text">
						<h2 class="featurette-heading">
							${items.title } <span class="muted">${items.subhead }</span>
						</h2>
						<p class="lead">${items.smart_text}</p>
					</div>
				</div>
				<hr class="featurette-divider">
				-->
				<!-- 左右结构注释 -->

				<div>
					<h3>
						${items.title } <span class="muted">${items.subhead }</span>
					</h3>
					<c:if test="${fn:length(items.smart)>0}">
						<img class="article_img" src="${items.smart }">
					</c:if>
					<p class="intro_text">${items.smart_text}</p>
				</div>
			</c:forEach>
		</div>
		<div class="span4">
			<legend>更多精彩</legend>
			<table class="table" style="color: gray;">
				<tbody>
					<c:forEach var="items" items="${articleList}">
						<tr>
							<td><a href="${context }/custom/detail?id=${items.id }"><img
									style="width: 160px;" src="${items.img }"></a></td>
							<td><a href="${context }/custom/detail?id=${items.id }"><span
									class="tb_title">${items.title }</span></a><br />日期：${items.specialMaketime}</td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div style="text-align: center;">
		<c:if test="${not empty prevSpecial }">
			<p class="text-left">
				上一篇：<a href="${context }/custom/detail?id=${prevSpecial.id }">${prevSpecial.title
					}</a>
			</p>
		</c:if>
		<c:if test="${not empty nextSpecial }">
			<p class="text-left">
				下一篇：<a href="${context }/custom/detail?id=${nextSpecial.id }">${nextSpecial.title
					}</a>
			</p>
		</c:if>

	</div>

	<!-- /END THE FEATURETTES -->
</div>
