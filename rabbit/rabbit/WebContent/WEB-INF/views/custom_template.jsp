<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<s:url var="resource" value="/resources/s" />
<%
	String context = request.getContextPath();
	request.setAttribute("context", context);
	request.setAttribute("maxWidth", 1140);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description"
	content="兔子旅游(guantravel.com)，为你提供全球最精彩的自驾游，没有跟团游的匆忙和自助游的盲目，4人以上即可成团，带你体验更加自由的旅程；我们的口号是：我的旅行我做主！">
<meta name="baidu-site-verification" content="XROhNHMZGM" />
<meta name="author" content="程序员老刘">
<meta name="Keywords" content="${metaKeys }">
<!--[if lt IE 9]>
<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->
<title>首页 - 兔子旅行</title>
<!-- 公用样式 -->
<link href="${context }/resources/rabbit/css/common.css"
	rel="stylesheet" type="text/css">
<script src="${context }/resources/rabbit/js/jquery-1.8.0.min.js"
	type="text/javascript"></script>
<script src="${context }/resources/rabbit/js/top.js"
	type="text/javascript"></script>
<!-- 标签切换 -->
<script type="text/javascript">
	function selectTag(showContent, selfObj) {
		// 操作标签
		var tag = document.getElementById("tags").getElementsByTagName("li");
		var taglength = tag.length;
		for (i = 0; i < taglength; i++) {
			tag[i].className = "";
		}
		selfObj.parentNode.className = "selectTag";
		// 操作内容
		for (i = 0; j = document.getElementById("tagContent" + i); i++) {
			j.style.display = "none";
		}
		document.getElementById(showContent).style.display = "block";
	}
	//导游详细
	function guTag(showCont,guideObj){
		// 操作标签
		var guide = document.getElementById("guide").getElementsByTagName("li");
		var guidelength = guide.length;
		for(i=0; i<guidelength; i++){
			guide[i].className = "";
		}
		guideObj.parentNode.className = "guTag";
		// 操作内容
		for(i=0; j=document.getElementById("guideCont"+i); i++){
			j.style.display = "none";
		}
		document.getElementById(showCont).style.display = "block";
	}
</script>

<!-- index -->
<link href="${context }/resources/rabbit/css/index.css" rel="stylesheet"
	type="text/css">
<!-- style -->
<link href="${context }/resources/rabbit/css/style.css" rel="stylesheet" type="text/css">

<!-- banner -->
<script type="text/javascript"
	src="${context }/resources/rabbit/js/banner.js"></script>
<script type="text/javascript"
	src="${context }/resources/rabbit/js/bootstrap.js"></script>

<!-- 弹框 -->
<script type="text/javascript"
	src="${context }/resources/rabbit/js/jquery.modal.js"></script>
<script type="text/javascript"
	src="${context }/resources/rabbit/js/site.js"></script>
</head>

<body>
	<!-- 公用头部 STA -->
	<t:insertAttribute name="header" />
	<!-- 公用头部 End -->

	<!-- 主体内容区 STA -->
	<t:insertAttribute name="content" />
	<!-- 主体内容区 End -->

	<!-- 公用底部 STA -->
	<t:insertAttribute name="footer" />
</body>
</html>
